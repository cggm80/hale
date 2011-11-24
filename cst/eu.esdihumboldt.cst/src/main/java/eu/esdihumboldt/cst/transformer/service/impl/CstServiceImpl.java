/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                  01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to this website:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to : http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 *
 * Componet     : cst
 * 	 
 * Classname    : eu.esdihumboldt.cst.transformer/TransformerService.java 
 * 
 * Author       : schneidersb
 * 
 * Created on   : Aug 13, 2009 -- 3:19:53 PM
 *
 */
package eu.esdihumboldt.cst.transformer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.Feature;
import org.opengis.feature.type.FeatureType;

import eu.esdihumboldt.cst.transformer.CstService;
import eu.esdihumboldt.cst.transformer.capabilities.CstServiceCapabilities;
import eu.esdihumboldt.cst.transformer.capabilities.impl.CstServiceCapabilitiesImpl;
import eu.esdihumboldt.cst.transformer.service.CstFunctionFactory;
import eu.esdihumboldt.cst.transformer.service.CstServiceFactory.ToleranceLevel;
import eu.esdihumboldt.specification.cst.CstFunction;
import eu.esdihumboldt.specification.cst.align.IAlignment;
import eu.esdihumboldt.specification.cst.align.ICell;


/**
 * Simple CstService implementation which applies all necessary Transformers on
 * a FeatureCollection or Feature.
 */
public class CstServiceImpl 
	implements CstService {
	
	private static final Logger _log = Logger.getLogger(CstServiceImpl.class);
	private final ToleranceLevel tl;
	private boolean createLineage = true;

	/**
	 * Default {@link CstService} constructor.
	 * @param tl 
	 */
	public CstServiceImpl(ToleranceLevel tl) {
		this.tl = tl;
	}


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public FeatureCollection<? extends FeatureType, ? extends Feature> transform(
			FeatureCollection<? extends FeatureType, ? extends Feature> fc,
			IAlignment alignment, Set<FeatureType> targetSchema) {
		TargetSchemaProvider.getInstance().addTypes(targetSchema);
		SchemaTranslationController stc = new SchemaTranslationController(tl,
				createLineage, alignment);
		FeatureCollection result = stc.translate((FeatureCollection) fc);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Feature transform(Feature f, ICell c) {
		CstFunctionFactory transformerFactory = CstFunctionFactory
				.getInstance();

		// The transformed feature
		Feature result = null;

		try {
			// Get the transformer which could execute the given
			// operation name.
			CstFunction function = transformerFactory.getCstFunction(c);

			// Execute the transformer
			result = function.transform(f, f);
		} catch (Exception e) {
			throw new RuntimeException("Transformation failed: " + e); //$NON-NLS-1$
		}

		return result;
	}

	public CstServiceCapabilities getCapabilities() {
		return new CstServiceCapabilitiesImpl();
	}

	/**
	 * @see eu.esdihumboldt.cst.transformer.CstService#registerCstFunctions(java.lang.String)
	 */
	public List<String> registerCstFunctions(String packageName) {
//		_log.setLevel(Level.INFO);
		if (packageName != null) {
			CstFunctionFactory.getInstance().registerCstPackage(packageName);
		}
		List<String> result = new ArrayList<String>();
		for (Class<? extends CstFunction> type : CstFunctionFactory.getInstance().getRegisteredFunctions().values()) {
			result.add(type.getName());
			_log.info("Registered CstFunction: " + type.getName()); //$NON-NLS-1$
		}
		return result;
	}


	public void enableLineageCreation(boolean activateLineage) {
		createLineage = activateLineage;
	}
}