/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.common.align.model.transformation.tree;

import java.util.List;
import java.util.Set;

import eu.esdihumboldt.hale.common.align.model.Cell;

/**
 * Represents a cell in the transformation tree
 * @author Simon Templer
 */
public interface CellNode extends TransformationNode {
	
	/**
	 * Get the associated cell.
	 * @return the cell
	 */
	public Cell getCell();
	
	/**
	 * Get the source entities associated to the cell.
	 * @return the source entities represented by source nodes
	 */
	public List<SourceNode> getSources();
	
	/**
	 * Get the entity names for the given source node. These are the names of 
	 * the cell entities the node is associated to.
	 * @param source the source node
	 * @return the source names
	 */
	public Set<String> getSourceNames(SourceNode source);

}