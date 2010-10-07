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
package eu.esdihumboldt.hale.rcp.wizards.functions.core.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

import eu.esdihumboldt.cst.align.ICell;
import eu.esdihumboldt.cst.align.IEntity;
import eu.esdihumboldt.goml.align.Cell;
import eu.esdihumboldt.goml.omwg.FeatureClass;
import eu.esdihumboldt.goml.omwg.Property;
import eu.esdihumboldt.goml.omwg.Restriction;
import eu.esdihumboldt.hale.models.utils.EntityUtils;
import eu.esdihumboldt.hale.rcp.views.model.SchemaItem;
import eu.esdihumboldt.hale.rcp.wizards.functions.AbstractSingleCellWizard;
import eu.esdihumboldt.hale.rcp.wizards.functions.AlignmentInfo;

/**
 * This {@link Wizard} is used to invoke a Renaming CstFunction for the Source
 * Feature Type
 * 
 * @author Anna Pitaev, Logica; Simon Templer, Fraunhofer IGD
 * @version $Id$
 */
public class SimpleFilterWizard extends AbstractSingleCellWizard {

	//private static Logger _log = Logger.getLogger(SimpleFilterWizard.class);

	private SimpleFilterWizardMainPage mainPage;
	
	private List<Restriction> restrictions;
	
	private Restriction resUsed;

	/**
	 * @see AbstractSingleCellWizard#AbstractSingleCellWizard(AlignmentInfo)
	 */
	public SimpleFilterWizard(AlignmentInfo selection) {
		super(selection);
	}
	
	/**
	 * @see AbstractSingleCellWizard#init()
	 */
	@Override
	protected void init() {
		Cell cell = getResultCell();
		
		String initialCQL = null;
		
		restrictions = FilterUtils2.getRestrictions(cell.getEntity1());
		
		if (restrictions != null) {
			resUsed = null;
			
			// check for a filter restriction
			Iterator<Restriction> it = restrictions.iterator();
			while (initialCQL == null && it.hasNext()) {
				Restriction restriction = it.next();
				if (restriction.getCqlStr() != null && !restriction.getCqlStr().isEmpty()) {
					initialCQL = restriction.getCqlStr();
					resUsed = restriction;
				}
			}
		}
		
		this.mainPage = new SimpleFilterWizardMainPage("Configure Filter Expression",
			"Configure Filter Expression", initialCQL);
		super.setWindowTitle("Configure Function");
		super.setNeedsProgressMonitor(true);
	}

	/**
	 * @see Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		ICell cell = getResultCell();
		
		// remove old restriction
		if (resUsed != null && restrictions != null) {
			restrictions.remove(resUsed);
		}
		
		String cql = mainPage.getCQL();
		if (cql != null && !cql.isEmpty()) {
			// add new one
			Restriction r = new Restriction(null);
			r.setCqlStr(mainPage.getCQL());
			
			FilterUtils2.addRestriction(r, cell.getEntity1(), getSourceItem());
		}
		
		return true;
	}

	/**
	 * @see IWizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		addPage(mainPage);
	}

}
