//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for containerTransitStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="containerTransitStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CARRY_UNDERWAY"/>
 *     &lt;enumeration value="PLANNED"/>
 *     &lt;enumeration value="COMPLETED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "containerTransitStatus")
@XmlEnum
public enum ContainerTransitStatus {


    /**
     * 
     * 						Horizontal transport of the container is in execution.
     * 					
     * 
     */
    CARRY_UNDERWAY,

    /**
     * 
     * 						Horizontal transport of the container is planned.
     * 					
     * 
     */
    PLANNED,

    /**
     * 
     * 						Horizontal transport of the container is completed.
     * 					
     * 
     */
    COMPLETED;

    public String value() {
        return name();
    }

    public static ContainerTransitStatus fromValue(String v) {
        return valueOf(v);
    }

}
