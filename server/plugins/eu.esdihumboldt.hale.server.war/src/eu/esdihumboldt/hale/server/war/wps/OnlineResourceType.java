//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.01 at 08:57:42 AM MEZ 
//


package eu.esdihumboldt.hale.server.war.wps;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * For OWS use in the service metadata document, the CI_OnlineResource class was XML encoded as the attributeGroup "xlink:simpleLink", as used in GML. 
 * 
 * <p>Java class for OnlineResourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OnlineResourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}simpleLink"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OnlineResourceType")
@XmlSeeAlso({
    RequestMethodType.class
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class OnlineResourceType {

    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String type;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String href;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String role;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String arcrole;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String title;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String show;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String actuate;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getType() {
        if (type == null) {
            return "simple";
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the href property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the arcrole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getArcrole() {
        return arcrole;
    }

    /**
     * Sets the value of the arcrole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setArcrole(String value) {
        this.arcrole = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the show property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setShow(String value) {
        this.show = value;
    }

    /**
     * Gets the value of the actuate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getActuate() {
        return actuate;
    }

    /**
     * Sets the value of the actuate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setActuate(String value) {
        this.actuate = value;
    }

}