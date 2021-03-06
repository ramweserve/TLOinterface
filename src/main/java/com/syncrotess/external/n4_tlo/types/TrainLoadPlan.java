//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				A train load plan is represented by a subtrain and the planned railcars related to it.
 * 			
 * 
 * <p>Java class for trainLoadPlan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trainLoadPlan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subTrainId" type="{http://www.syncrotess.com/external/n4-tlo/types}subTrainId"/>
 *         &lt;element name="railcars" type="{http://www.syncrotess.com/external/n4-tlo/types}trainLoadPlanRailcars"/>
 *         &lt;element name="canSkip" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trainLoadPlan", propOrder = {
    "subTrainId",
    "railcars",
    "canSkip"
})
public class TrainLoadPlan {

    @XmlElement(required = true)
    protected String subTrainId;
    @XmlElement(required = true)
    protected TrainLoadPlanRailcars railcars;
    @XmlElement(defaultValue = "false")
    protected Boolean canSkip;

    /**
     * Gets the value of the subTrainId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubTrainId() {
        return subTrainId;
    }

    /**
     * Sets the value of the subTrainId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubTrainId(String value) {
        this.subTrainId = value;
    }

    /**
     * Gets the value of the railcars property.
     * 
     * @return
     *     possible object is
     *     {@link TrainLoadPlanRailcars }
     *     
     */
    public TrainLoadPlanRailcars getRailcars() {
        return railcars;
    }

    /**
     * Sets the value of the railcars property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrainLoadPlanRailcars }
     *     
     */
    public void setRailcars(TrainLoadPlanRailcars value) {
        this.railcars = value;
    }

    /**
     * Gets the value of the canSkip property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCanSkip() {
        return canSkip;
    }

    /**
     * Sets the value of the canSkip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCanSkip(Boolean value) {
        this.canSkip = value;
    }

}
