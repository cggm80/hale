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
package com.onespatial.jrc.tns.oml_to_rif.model.rif.filter.nonterminal;

/**
 * An enumeration of the different kinds of predicate tree nodes used in
 * filters.
 * 
 * @author Simon Payne (Simon.Payne@1spatial.com) / 1Spatial Group Ltd.
 * @author Richard Sunderland (Richard.Sunderland@1spatial.com) / 1Spatial Group Ltd.
 */
public enum NodeType
{
    /**
     * Node with an AND in it.
     */
    AND_NODE,
    /**
     * Node with an OR in it.
     */
    OR_NODE,
    /**
     * Node with a NOT in it.
     */
    NOT_NODE,
    /**
     * Node with an EQUAL_TO expression in it.
     */
    EQUAL_TO_NODE,
    /**
     * Node with a GREATER_THAN expression in it.
     */
    GREATER_THAN_NODE,
    /**
     * Node with a LESS_THAN expression in it.
     */
    LESS_THAN_NODE,
    /**
     * Node with a CONTAINS in it.
     */
    CONTAINS_NODE,
    /**
     * Node with an INTERSECTS in it.
     */
    INTERSECTS_NODE,
    /**
     * Node with a WITHIN in it.
     */
    WITHIN_NODE,
    /**
     * Node with a LIKE in it.
     */
    LIKE_NODE;
}