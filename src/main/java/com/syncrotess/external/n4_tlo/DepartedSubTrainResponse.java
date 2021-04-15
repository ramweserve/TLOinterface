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
import com.syncrotess.external.n4_tlo.types.DepartedSubTrains;


/**
 * 
 *           FROM N4 TO TLO
 * 
 *           The `DepartedSubTrainResponse` is a response to a
 *           [DepartedSubTrainRequest](#external-n4-tlo:DepartedSubTrainRequest) and contains all
 *           departed sub-train information (sub-trains that are not on serviceable tracks
 *           and which have not left the realm of the terminal. A sub-train leaves the realm of
 *           the terminal when the sub-train is closed) See also
 *           [departedSubTrains](#http___www_syncrotess_com_external_n4-tlo_types_departedSubTrains).
 * 
 *           Used in the initialization phase and in the re-sync process.
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
 *         &lt;element name="departedSubTrains" type="{http://www.syncrotess.com/external/n4-tlo/types}departedSubTrains"/>
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
    "departedSubTrains"
})
@XmlRootElement(name = "DepartedSubTrainResponse")
public class DepartedSubTrainResponse
    extends ResponseMessage
{

    @XmlElement(required = true)
    protected DepartedSubTrains departedSubTrains;

    /**
     * Gets the value of the departedSubTrains property.
     * 
     * @return
     *     possible object is
     *     {@link DepartedSubTrains }
     *     
     */
    public DepartedSubTrains getDepartedSubTrains() {
        return departedSubTrains;
    }

    /**
     * Sets the value of the departedSubTrains property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartedSubTrains }
     *     
     */
    public void setDepartedSubTrains(DepartedSubTrains value) {
        this.departedSubTrains = value;
    }

}
