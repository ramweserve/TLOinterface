//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo.common.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.syncrotess.external.n4_tlo.T1DoneNotification;
import com.syncrotess.external.n4_tlo.T3DoneNotification;


/**
 * 
 *           Base message of T1DoneNotification and T3DoneNotification with an optional planId.
 *           N4 should reply the planId of TrainPlanNotification and TrainLoadPlanNotification messages.
 *         
 * 
 * <p>Java class for doneNotificationMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="doneNotificationMessage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.syncrotess.com/external/n4-tlo/common/types}notificationMessage">
 *       &lt;sequence>
 *         &lt;element name="processingResult" type="{http://www.syncrotess.com/external/n4-tlo/common/types}processingResult"/>
 *         &lt;element name="planId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doneNotificationMessage", propOrder = {
    "processingResult",
    "planId"
})
@XmlSeeAlso({
    T3DoneNotification.class,
    T1DoneNotification.class
})
public abstract class DoneNotificationMessage
    extends NotificationMessage
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ProcessingResult processingResult;
    protected String planId;

    /**
     * Gets the value of the processingResult property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessingResult }
     *     
     */
    public ProcessingResult getProcessingResult() {
        return processingResult;
    }

    /**
     * Sets the value of the processingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessingResult }
     *     
     */
    public void setProcessingResult(ProcessingResult value) {
        this.processingResult = value;
    }

    /**
     * Gets the value of the planId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * Sets the value of the planId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanId(String value) {
        this.planId = value;
    }

}
