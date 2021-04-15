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
 * 				Catalog for different railcar types. Provides technical railcar details relevant
 * 				for TLO like weight restrictions or number of platforms and their utlization options.
 * 			
 * 
 * <p>Java class for railcarTypes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="railcarTypes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="railcarType" type="{http://www.syncrotess.com/external/n4-tlo/types}railcarType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "railcarTypes", propOrder = {
    "railcarType"
})
public class RailcarTypes {

    protected List<RailcarType> railcarType;

    /**
     * Gets the value of the railcarType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the railcarType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRailcarType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RailcarType }
     * 
     * 
     */
    public List<RailcarType> getRailcarType() {
        if (railcarType == null) {
            railcarType = new ArrayList<RailcarType>();
        }
        return this.railcarType;
    }

}