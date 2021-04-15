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
 * <p>Java class for railcarDamaged.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="railcarDamaged">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GOOD"/>
 *     &lt;enumeration value="BAD"/>
 *     &lt;enumeration value="UGLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "railcarDamaged")
@XmlEnum
public enum RailcarDamaged {


    /**
     * 
     *       			The railcar is in a condition, that TLO is allowed to plan with that
     *       			railcar. TLO can plan load on this railcar.
     *       		
     * 
     */
    GOOD,

    /**
     * 
     *       			TLO will not plan load on a railcar with condition "BAD" and will not consider
     *       			it in any form. From a TLO perspective, it is assumed that bad railcars will be 
     *       			removed from the outgoing train.
     *       		
     * 
     */
    BAD,

    /**
     * 
     *       			A railcar having this status will get a special handling. Based on a given configuration
     *       			these railcars will be routed to specific destination (coming from configuration). They
     *       			can carry load on the way to this location. It may be the case, that the destination is
     *       			not on the direct train route and needs to be decoupled somewhere on that trip.
     *       		
     * 
     */
    UGLY;

    public String value() {
        return name();
    }

    public static RailcarDamaged fromValue(String v) {
        return valueOf(v);
    }

}
