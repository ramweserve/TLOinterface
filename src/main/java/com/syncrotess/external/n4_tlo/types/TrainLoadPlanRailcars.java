//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				List of "trainLoadPlanRailcar" elements.
 * 			
 * 
 * <p>Java class for trainLoadPlanRailcars complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trainLoadPlanRailcars">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="railcar" type="{http://www.syncrotess.com/external/n4-tlo/types}trainLoadPlanRailcar" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trainLoadPlanRailcars", propOrder = {
    "railcar"
})
public class TrainLoadPlanRailcars {

    protected List<TrainLoadPlanRailcar> railcar;

    /**
     * Gets the value of the railcar property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the railcar property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRailcar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrainLoadPlanRailcar }
     * 
     * 
     */
    public List<TrainLoadPlanRailcar> getRailcar() {
        if (railcar == null) {
            railcar = new ArrayList<TrainLoadPlanRailcar>();
        }
        return this.railcar;
    }

}