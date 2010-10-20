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

package eu.esdihumboldt.hale.schemaprovider.model;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.opengis.feature.type.Name;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;

/**
 * Manages default geometry preferences
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public abstract class DefaultGeometries {
	
	private static final ALogger log = ALoggerFactory.getLogger(DefaultGeometries.class);
	
	private static final Preferences prefs = Preferences.userNodeForPackage(DefaultGeometries.class).node("defaultGeometries");

	/**
	 * Get the default geometry name for a given type name
	 * 
	 * @param typeName the type name
	 * 
	 * @return the default geometry property name or <code>null</code>
	 */
	public static String getDefaultGeometryName(Name typeName) {
		try {
			prefs.sync();
			if (prefs.nodeExists(typeName.getNamespaceURI())) {
				return prefs.node(typeName.getNamespaceURI()).get(typeName.getLocalPart(), null);
			}
			else {
				return null;
			}
		} catch (BackingStoreException e) {
			log.warn("Error accessing the default geometry preferences", e);
			return null;
		}
	}
	
	/**
	 * Set the default geometry property name for a given type
	 * 
	 * @param typeName the type name
	 * @param propertyName the geometry property name
	 */
	public static void setDefaultGeometryName(Name typeName, String propertyName) {
		prefs.node(typeName.getNamespaceURI()).put(typeName.getLocalPart(), propertyName);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			log.warn("Error writing the default geometry preferences", e);
		}
	}

}
