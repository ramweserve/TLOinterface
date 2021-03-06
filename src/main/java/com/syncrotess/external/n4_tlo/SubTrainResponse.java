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
import com.syncrotess.external.n4_tlo.types.SubTrains;


/**
 * 
 *           FROM N4 TO TLO
 * 
 *           The `SubTrainResponse` is a response to a [SubTrainRequest](#external-n4-tlo:SubTrainRequest) and contains all 
 *           sub-train information. See also  
 *           [subTrain](#http___www_syncrotess_com_external_n4-tlo_types_subTrain).
 *           
 *           Used in initilization phase.
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
 *         &lt;element name="subTrains" type="{http://www.syncrotess.com/external/n4-tlo/types}subTrains"/>
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
    "subTrains"
})
@XmlRootElement(name = "SubTrainResponse")
public class SubTrainResponse
    extends ResponseMessage
{

    @XmlElement(required = true)
    protected SubTrains subTrains;

    /**
     * Gets the value of the subTrains property.
     * 
     * @return
     *     possible object is
     *     {@link SubTrains }
     *     
     */
    public SubTrains getSubTrains() {
        return subTrains;
    }

    /**
     * Sets the value of the subTrains property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubTrains }
     *     
     */
    public void setSubTrains(SubTrains value) {
        this.subTrains = value;
    }

}
