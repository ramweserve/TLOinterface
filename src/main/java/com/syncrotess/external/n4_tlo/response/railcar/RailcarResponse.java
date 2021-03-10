//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.04 at 05:04:01 PM IST 
//


package com.syncrotess.external.n4_tlo.response.railcar;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.syncrotess.external.n4_tlo.common.types.Header;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/common/types}header"/>
 *         &lt;element name="railcars">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}railcar" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "railcars"
})
@XmlRootElement(name = "RailcarResponse")
public class RailcarResponse {

    @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/common/types", required = true)
    protected Header header;
    @XmlElement(required = true)
    protected Railcars railcars;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}railcar" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "railcar"
    })
    public static class Railcars {

        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected List<Railcar> railcar;

        /**
         * Gets the value of the railcar property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the railcar property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRailcar().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Railcar }
         * 
         * 
         */
        public List<Railcar> getRailcar() {
            if (railcar == null) {
                railcar = new ArrayList<Railcar>();
            }
            return this.railcar;
        }

    }

}
