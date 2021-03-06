//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.syncrotess.external.n4_tlo.common.types.ResponseMessage;
import com.syncrotess.external.n4_tlo.types.RailcarTypes;


/**
 * 
 *           FROM N4 TO TLO
 *           
 *           The `RailcarTypeResponse` is a response to a [RailcarTypeRequest](#external-n4-tlo:RailcarTypeRequest). It contains 
 *           a list of railcar class information (i.e. static information associated to the railcar class, e.g. the relevant 
 *           information that originates in UMLER), see also 
 *           [railcarType](#http___www_syncrotess_com_external_n4-tlo_types_railcarType)
 *         
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.syncrotess.com/external/n4-tlo/common/types}responseMessage">
 *       &lt;sequence>
 *         &lt;element name="railcarTypes" type="{http://www.syncrotess.com/external/n4-tlo/types}railcarTypes"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "railcarTypes"
})
@XmlRootElement(name = "RailcarTypeResponse")
public class RailcarTypeResponse
    extends ResponseMessage
{

    @XmlElement(required = true)
    protected RailcarTypes railcarTypes;

    /**
     * Gets the value of the railcarTypes property.
     * 
     * @return
     *     possible object is
     *     {@link RailcarTypes }
     *     
     */
    public RailcarTypes getRailcarTypes() {
        return railcarTypes;
    }

    /**
     * Sets the value of the railcarTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link RailcarTypes }
     *     
     */
    public void setRailcarTypes(RailcarTypes value) {
        this.railcarTypes = value;
    }

}
