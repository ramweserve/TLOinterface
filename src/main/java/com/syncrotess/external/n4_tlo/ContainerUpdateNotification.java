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
import com.syncrotess.external.n4_tlo.types.Container;


/**
 * 
 *           FROM N4 TO TLO
 *           
 *           A `ContainerUpdateNotification` is sent in case new rail-containers are added or existing rail-containers are updated. It contains exactly one
 *           container, see also [container](#http___www_syncrotess_com_external_n4-tlo_types_container). A `ContainerUpdateNotification` is sent 
 *           when the following triggers occur:
 *           
 *           <ul><li>Position update</li>
 *             <li>Routing updates</li>
 *             <li>OOG/Damage/Hazard updates</li>
 *             <li>Weight updates</li>
 *             <li>Destination updates</li>
 *             <li>Special Stow updates</li>
 *             <li>Flex Field (such as locked status) updates</li>
 *             <li>Group Code updates</li>
 *             <li>Train Load Plan updates</li></ul>
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
 *         &lt;element name="container" type="{http://www.syncrotess.com/external/n4-tlo/types}container"/>
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
    "container"
})
@XmlRootElement(name = "ContainerUpdateNotification")
public class ContainerUpdateNotification
    extends NotificationMessage
{

    @XmlElement(required = true)
    protected Container container;

    /**
     * Gets the value of the container property.
     * 
     * @return
     *     possible object is
     *     {@link Container }
     *     
     */
    public Container getContainer() {
        return container;
    }

    /**
     * Sets the value of the container property.
     * 
     * @param value
     *     allowed object is
     *     {@link Container }
     *     
     */
    public void setContainer(Container value) {
        this.container = value;
    }

}
