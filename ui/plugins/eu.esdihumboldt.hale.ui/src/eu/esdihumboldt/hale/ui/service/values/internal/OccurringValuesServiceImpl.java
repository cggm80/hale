/*
 * Copyright (c) 2013 Data Harmonisation Panel
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.ui.service.values.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.esdihumboldt.hale.common.align.model.AlignmentUtil;
import eu.esdihumboldt.hale.common.align.model.ChildContext;
import eu.esdihumboldt.hale.common.align.model.impl.PropertyEntityDefinition;
import eu.esdihumboldt.hale.common.filter.TypeFilter;
import eu.esdihumboldt.hale.common.instance.model.DataSet;
import eu.esdihumboldt.hale.common.instance.model.Group;
import eu.esdihumboldt.hale.common.instance.model.Instance;
import eu.esdihumboldt.hale.common.instance.model.InstanceCollection;
import eu.esdihumboldt.hale.common.instance.model.ResourceIterator;
import eu.esdihumboldt.hale.common.schema.SchemaSpaceID;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.Binding;
import eu.esdihumboldt.hale.ui.service.instance.InstanceService;
import eu.esdihumboldt.hale.ui.service.instance.InstanceServiceAdapter;
import eu.esdihumboldt.hale.ui.service.values.OccurringValues;
import eu.esdihumboldt.hale.ui.service.values.OccurringValuesUtil;

/**
 * Service that determines what different values occur for specific
 * {@link PropertyEntityDefinition}s.
 * 
 * @author Simon Templer
 */
public class OccurringValuesServiceImpl extends AbstractOccurringValuesService {

	/**
	 * Job determining the occurring values for a specific property entity.
	 */
	public class OccuringValuesJob extends Job {

		private final PropertyEntityDefinition property;
		private final Map<PropertyEntityDefinition, OccurringValuesImpl> values;
		private final InstanceCollection instances;

		/**
		 * Create a Job to update the occurring values for the given property
		 * entity.
		 * 
		 * @param property the property entity definition
		 * @param values the map to store the updated information in
		 * @param instances the instances to test
		 */
		public OccuringValuesJob(PropertyEntityDefinition property,
				Map<PropertyEntityDefinition, OccurringValuesImpl> values,
				InstanceCollection instances) {
			super("Determine occurring values");
			this.property = property;
			this.values = values;
			this.instances = instances;

			setUser(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			// only select instances of the correct type
			InstanceCollection instances = this.instances
					.select(new TypeFilter(property.getType()));
			// and apply an eventual filter
			if (property.getFilter() != null) {
				instances = instances.select(property.getFilter());
			}

			boolean instanceProgress = instances.hasSize();
			String taskName = "Check instances for occuring values";
			if (instanceProgress) {
				monitor.beginTask(taskName, instances.size());
			}
			else {
				monitor.beginTask(taskName, IProgressMonitor.UNKNOWN);
			}

			// create set to store values
			Set<Object> collectedValues;
			Class<?> binding = property.getDefinition().getPropertyType()
					.getConstraint(Binding.class).getBinding();
			if (Comparable.class.isAssignableFrom(binding)) {
				// tree set for sorted values
				collectedValues = new TreeSet<Object>();
			}
			else {
				// unsorted values
				collectedValues = new HashSet<Object>();
			}

			ResourceIterator<Instance> it = instances.iterator();
			try {
				while (it.hasNext()) {
					Instance instance = it.next();
					addValues(instance, property.getPropertyPath(), collectedValues);
					if (instanceProgress) {
						// TODO improved monitor update?!
						monitor.worked(1);
					}
				}
			} finally {
				it.close();
			}

			synchronized (values) {
				OccurringValuesImpl ov = new OccurringValuesImpl(collectedValues, property);
				values.put(property, ov);
			}

			notifyOccurringValuesUpdated(property);

			monitor.done();

			return Status.OK_STATUS;
		}

		/**
		 * Add the values found on the given path to the given set.
		 * 
		 * @param group the parent group
		 * @param path the path on the group
		 * @param collectedValues the set to add the values to
		 */
		private void addValues(Group group, List<ChildContext> path, Set<Object> collectedValues) {
			if (path == null || path.isEmpty()) {
				// empty path - retrieve value from instance
				if (group instanceof Instance) {
					Object value = ((Instance) group).getValue();
					if (value != null) {
						collectedValues.add(value);
					}
				}
			}
			else {
				// go down the path
				ChildContext context = path.get(0);
				List<ChildContext> subPath = path.subList(1, path.size());

				Object[] values = group.getProperty(context.getChild().getName());

				if (values != null) {
					// apply the possible source contexts
					if (context.getIndex() != null) {
						// select only the item at the index
						int index = context.getIndex();
						if (index < values.length) {
							values = new Object[] { values[index] };
						}
						else {
							values = new Object[] {};
						}
					}
					if (context.getCondition() != null) {
						// select only values that match the condition
						List<Object> matchedValues = new ArrayList<Object>();
						for (Object value : values) {
							if (AlignmentUtil.matchCondition(context.getCondition(), value, group)) {
								matchedValues.add(value);
							}
						}
						values = matchedValues.toArray();
					}

					// check all values
					for (Object value : values) {
						if (value instanceof Group) {
							addValues((Group) value, subPath, collectedValues);
						}
						else if (subPath.isEmpty()) {
							// normal value and at the end of the path
							if (value != null) {
								collectedValues.add(value);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Values that occur in the source data.
	 */
	private final Map<PropertyEntityDefinition, OccurringValuesImpl> sourceValues = new HashMap<PropertyEntityDefinition, OccurringValuesImpl>();

	/**
	 * Values that occur in the transformed data.
	 */
	private final Map<PropertyEntityDefinition, OccurringValuesImpl> transformedValues = new HashMap<PropertyEntityDefinition, OccurringValuesImpl>();

	/**
	 * The service for accessing instances.
	 */
	private final InstanceService instances;

	/**
	 * Create a service instance.
	 * 
	 * @param instances the instance service
	 */
	public OccurringValuesServiceImpl(InstanceService instances) {
		super();

		// add instance service listener
		instances.addListener(new InstanceServiceAdapter() {

			@Override
			public void datasetChanged(DataSet type) {
				SchemaSpaceID schemaSpace;
				switch (type) {
				case TRANSFORMED:
					schemaSpace = SchemaSpaceID.TARGET;
					break;
				default:
					schemaSpace = SchemaSpaceID.SOURCE;
				}

				invalidateValues(schemaSpace);
			}

		});

		this.instances = instances;
	}

	/**
	 * Invalidate occurring values in the given schema space.
	 * 
	 * @param schemaSpace the schema space
	 */
	protected void invalidateValues(SchemaSpaceID schemaSpace) {
		Map<PropertyEntityDefinition, OccurringValuesImpl> values = selectValues(schemaSpace);

		synchronized (values) {
			for (OccurringValuesImpl ov : values.values()) {
				ov.invalidate();
			}
		}

		notifyOccurringValuesInvalidated(schemaSpace);
	}

	@Override
	public OccurringValues getOccurringValues(PropertyEntityDefinition property) {
		return getOccurringValues(property, selectValues(property.getSchemaSpace()));
	}

	private Map<PropertyEntityDefinition, OccurringValuesImpl> selectValues(
			SchemaSpaceID schemaSpace) {
		switch (schemaSpace) {
		case SOURCE:
			return sourceValues;
		case TARGET:
			return transformedValues;
		default:
			throw new IllegalArgumentException("Illegal schema space specified");
		}
	}

	/**
	 * Get the values occurring in the data for the given property entity.
	 * 
	 * @param property the property entity definition
	 * @param values the map containing the current occurring values
	 * @return the occurring values for the property or <code>null</code>
	 */
	private OccurringValues getOccurringValues(PropertyEntityDefinition property,
			Map<PropertyEntityDefinition, ? extends OccurringValues> values) {
		synchronized (values) {
			OccurringValues ov = values.get(property);
			return ov;
		}
	}

	@Override
	public boolean updateOccuringValues(PropertyEntityDefinition property) {
		// sanity check on property
		if (!OccurringValuesUtil.supportsOccurringValues(property)) {
			throw new IllegalArgumentException(
					"Determinining occurring values not supported for given property");
		}

		return updateOccuringValues(property, selectValues(property.getSchemaSpace()));
	}

	/**
	 * Update the occurring values for the given property entity.
	 * 
	 * @param property the property entity definition
	 * @param values the map containing the current occurring values
	 * @return <code>true</code> if the task to update the information has been
	 *         started, <code>false</code> if the information was up-to-date
	 */
	private boolean updateOccuringValues(PropertyEntityDefinition property,
			Map<PropertyEntityDefinition, OccurringValuesImpl> values) {
		synchronized (values) {
			OccurringValues ov = values.get(property);
			if (ov != null && ov.isUpToDate()) {
				return false;
			}
		}

		// determine occurring values

		// determine data set
		DataSet dataSet;
		switch (property.getSchemaSpace()) {
		case TARGET:
			dataSet = DataSet.TRANSFORMED;
			break;
		default:
			dataSet = DataSet.SOURCE;
		}

		// go through instances to determine occurring values
		Job job = new OccuringValuesJob(property, values, instances.getInstances(dataSet));
		job.schedule();

		return true;
	}

}