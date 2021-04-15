//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo.common.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.syncrotess.external.n4_tlo.ContainerDeleteNotification;
import com.syncrotess.external.n4_tlo.ContainerTransitDeleteNotification;
import com.syncrotess.external.n4_tlo.ContainerTransitUpdateNotification;
import com.syncrotess.external.n4_tlo.ContainerUpdateNotification;
import com.syncrotess.external.n4_tlo.DangerousGoodsMatrixUpdateNotification;
import com.syncrotess.external.n4_tlo.DepartedSubTrainNotification;
import com.syncrotess.external.n4_tlo.RailcarConsistNotification;
import com.syncrotess.external.n4_tlo.RailcarNotification;
import com.syncrotess.external.n4_tlo.RailcarTypeNotification;
import com.syncrotess.external.n4_tlo.SubTrainDoneNotification;
import com.syncrotess.external.n4_tlo.SubTrainNotification;
import com.syncrotess.external.n4_tlo.TLOJobSteppingNotification;


/**
 * <p>Java class for notificationMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificationMessage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.syncrotess.com/external/n4-tlo/common/types}message">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificationMessage")
@XmlSeeAlso({
    RailcarNotification.class,
    RailcarTypeNotification.class,
    TLOJobSteppingNotification.class,
    ContainerTransitUpdateNotification.class,
    ContainerDeleteNotification.class,
    SubTrainDoneNotification.class,
    SubTrainNotification.class,
    RailcarConsistNotification.class,
    ResultNotificationMessage.class,
    DangerousGoodsMatrixUpdateNotification.class,
    DepartedSubTrainNotification.class,
    DoneNotificationMessage.class,
    ContainerTransitDeleteNotification.class,
    ContainerUpdateNotification.class
})
public abstract class NotificationMessage
    extends Message
{


}