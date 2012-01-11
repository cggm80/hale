/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */

package eu.esdihumboldt.hale.ui.service.instance;

import java.util.Set;

import eu.esdihumboldt.hale.common.instance.model.DataSet;
import eu.esdihumboldt.hale.common.instance.model.Instance;
import eu.esdihumboldt.hale.common.instance.model.InstanceCollection;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;

/**
 * The {@link InstanceService} provides {@link Instance}s from different data
 * sets, e.g. the {@link DataSet#SOURCE} and {@link DataSet#TRANSFORMED} data
 * sets.
 * XXX It also triggers the transformation of the source to the target data set???
 * 
 * @author Thorsten Reitz
 * @author Simon Templer
 */
public interface InstanceService {
	
	/**
	 * Get the instances from the given data set
	 * @param dataset the data set
	 * @return the instance collection
	 */
	public InstanceCollection getInstances(DataSet dataset);
	
	/**
	 * Get the types for which instances are present in the given data set
	 * @param dataset the data set
	 * @return the set of types for which instances are present
	 */
	public Set<TypeDefinition> getInstanceTypes(DataSet dataset);
	
	/**
	 * Add instances to the {@link DataSet#SOURCE} data set
	 * @param sourceInstances the instances to add
	 */
	public void addSourceInstances(InstanceCollection sourceInstances);
	
	/**
	 * This will remove all instances from the service.
	 */
	public void clearInstances();
	
	/**
	 * Get a reference to an instance that can be used to retrieve the
	 * given instance using {@link #getInstance(InstanceReference)}.
	 * @param instance the instance, must have originated from this service
	 * @return the reference
	 */
	public InstanceReference getReference(Instance instance);
	
	/**
	 * Get the instance referenced by the given reference.
	 * @param reference the instance reference
	 * @return the referenced instance or <code>null</code> if it does not exist
	 *   or the reference is invalid
	 */
	public Instance getInstance(InstanceReference reference);
	
	/**
	 * Adds an instance service listener
	 * 
	 * @param listener the listener to add
	 */
	public void addListener(InstanceServiceListener listener);
	
	/**
	 * Removes an instance service listener
	 * 
	 * @param listener the listener to remove
	 */
	public void removeListener(InstanceServiceListener listener);
	
}
