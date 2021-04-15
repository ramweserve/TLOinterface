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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.syncrotess.external.n4_tlo.common.types.NotificationMessage;
import com.syncrotess.external.n4_tlo.types.JobStepping;


/**
 * 
 *           FROM GCT TO TLO
 *           
 *           A  `TLOJobSteppingNotification` is sent to TLO in order to steer the activity of TLO triggers: the TLO triggers can either be paused (i.e. 
 *           no further optimization runs will be started) or be resumed (TLO is sensitive to again to any occurring triggers).
 *           
 *           Single-stepping a TLO optimization run is currently not supported by TLO.
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
 *         &lt;element name="jobStepping" type="{http://www.syncrotess.com/external/n4-tlo/types}jobStepping"/>
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
    "jobStepping"
})
@XmlRootElement(name = "TLOJobSteppingNotification")
public class TLOJobSteppingNotification
    extends NotificationMessage
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected JobStepping jobStepping;

    /**
     * Gets the value of the jobStepping property.
     * 
     * @return
     *     possible object is
     *     {@link JobStepping }
     *     
     */
    public JobStepping getJobStepping() {
        return jobStepping;
    }

    /**
     * Sets the value of the jobStepping property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobStepping }
     *     
     */
    public void setJobStepping(JobStepping value) {
        this.jobStepping = value;
    }

}
