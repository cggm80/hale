//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.09 at 01:46:43 PM MESZ 
//


package eu.esdihumboldt.goml.generated;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for PropertyOperatorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PropertyOperatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INTERSECTION"/>
 *     &lt;enumeration value="UNION"/>
 *     &lt;enumeration value="UNION_DUPLICATES"/>
 *     &lt;enumeration value="COMPLEMENT"/>
 *     &lt;enumeration value="FIRST"/>
 *     &lt;enumeration value="NEXT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum PropertyOperatorType {

    COMPLEMENT,
    FIRST,
    INTERSECTION,
    NEXT,
    UNION,
    UNION_DUPLICATES;

    public String value() {
        return name();
    }

    public static PropertyOperatorType fromValue(String v) {
        return valueOf(v);
    }

}
