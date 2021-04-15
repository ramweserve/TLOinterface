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
import com.syncrotess.external.n4_tlo.common.types.NotificationMessage;
import com.syncrotess.external.n4_tlo.types.Railcars;


/**
 * 
 *           FROM N4 TO TLO
 *           
 *           A `RailcarNotification` is sent in case attributes of the railcar are changed 
 *           in a way that it doesn't affect the consist of the railcars on the terminal's serviceable tracks.
 *           TLO will only update railcars included in the notification and it will not change 
 *           any railcar sequences on tracks.
 *           See also [railcar](#http___www_syncrotess_com_external_n4-tlo_types_railcar)
 *         
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.syncrotess.com/external/n4-tlo/common/types}notificationMessage">
 *       &lt;sequence>
 *         &lt;element name="railcars" type="{http://www.syncrotess.com/external/n4-tlo/types}railcars"/>
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
    "railcars"
})
@XmlRootElement(name = "RailcarNotification")
public class RailcarNotification
    extends NotificationMessage
{

    @XmlElement(required = true)
    protected Railcars railcars;

    /**
     * Gets the value of the railcars property.
     * 
     * @return
     *     possible object is
     *     {@link Railcars }
     *     
     */
    public Railcars getRailcars() {
        return railcars;
    }

    /**
     * Sets the value of the railcars property.
     * 
     * @param value
     *     allowed object is
     *     {@link Railcars }
     *     
     */
    public void setRailcars(Railcars value) {
        this.railcars = value;
    }

}
