//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.09 at 01:46:43 PM MESZ 
//


package eu.esdihumboldt.goml.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelationCompositionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelationCompositionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operator" type="{http://www.omwg.org/TR/d7/ontology/alignment}RelationOperatorType"/>
 *         &lt;choice>
 *           &lt;element name="collection" type="{http://www.omwg.org/TR/d7/ontology/alignment}ClassCollectionType"/>
 *           &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}Relation"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationCompositionType", propOrder = {
    "operator",
    "collection",
    "relation"
})
public class RelationCompositionType {

    @XmlElement(namespace = "http://www.omwg.org/TR/d7/ontology/alignment", required = true)
    protected RelationOperatorType operator;
    @XmlElement(namespace = "http://www.omwg.org/TR/d7/ontology/alignment")
    protected ClassCollectionType collection;
    @XmlElement(name = "Relation", namespace = "http://www.omwg.org/TR/d7/ontology/alignment")
    protected RelationType relation;

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link RelationOperatorType }
     *     
     */
    public RelationOperatorType getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationOperatorType }
     *     
     */
    public void setOperator(RelationOperatorType value) {
        this.operator = value;
    }

    /**
     * Gets the value of the collection property.
     * 
     * @return
     *     possible object is
     *     {@link ClassCollectionType }
     *     
     */
    public ClassCollectionType getCollection() {
        return collection;
    }

    /**
     * Sets the value of the collection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassCollectionType }
     *     
     */
    public void setCollection(ClassCollectionType value) {
        this.collection = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link RelationType }
     *     
     */
    public RelationType getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationType }
     *     
     */
    public void setRelation(RelationType value) {
        this.relation = value;
    }

}
