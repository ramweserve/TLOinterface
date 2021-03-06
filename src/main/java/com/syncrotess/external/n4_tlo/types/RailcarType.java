//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.18 at 12:39:04 PM IST 
//


package com.syncrotess.external.n4_tlo.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.syncrotess.external.n4_tlo.common.types.Length;
import com.syncrotess.external.n4_tlo.common.types.Weight;


/**
 * railcar type definition, i.e. based on Umler. Catalog member of known railcar types.
 * 
 * <p>Java class for railcarType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="railcarType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.syncrotess.com/external/n4-tlo/types}railcarTypeId"/>
 *         &lt;element name="platformTypes" type="{http://www.syncrotess.com/external/n4-tlo/types}platformTypes"/>
 *         &lt;element name="maxNetWeight" type="{http://www.syncrotess.com/external/n4-tlo/common/types}weight"/>
 *         &lt;element name="tareWeight" type="{http://www.syncrotess.com/external/n4-tlo/common/types}weight"/>
 *         &lt;element name="length" type="{http://www.syncrotess.com/external/n4-tlo/common/types}length"/>
 *         &lt;element name="reeferSuitableTopTier" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reeferSuitableBottomTier" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "railcarType", propOrder = {
    "id",
    "platformTypes",
    "maxNetWeight",
    "tareWeight",
    "length",
    "reeferSuitableTopTier",
    "reeferSuitableBottomTier"
})
public class RailcarType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected PlatformTypes platformTypes;
    @XmlElement(required = true)
    protected Weight maxNetWeight;
    @XmlElement(required = true)
    protected Weight tareWeight;
    @XmlElement(required = true)
    protected Length length;
    protected boolean reeferSuitableTopTier;
    protected boolean reeferSuitableBottomTier;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the platformTypes property.
     * 
     * @return
     *     possible object is
     *     {@link PlatformTypes }
     *     
     */
    public PlatformTypes getPlatformTypes() {
        return platformTypes;
    }

    /**
     * Sets the value of the platformTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlatformTypes }
     *     
     */
    public void setPlatformTypes(PlatformTypes value) {
        this.platformTypes = value;
    }

    /**
     * Gets the value of the maxNetWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getMaxNetWeight() {
        return maxNetWeight;
    }

    /**
     * Sets the value of the maxNetWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setMaxNetWeight(Weight value) {
        this.maxNetWeight = value;
    }

    /**
     * Gets the value of the tareWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getTareWeight() {
        return tareWeight;
    }

    /**
     * Sets the value of the tareWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setTareWeight(Weight value) {
        this.tareWeight = value;
    }

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link Length }
     *     
     */
    public Length getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link Length }
     *     
     */
    public void setLength(Length value) {
        this.length = value;
    }

    /**
     * Gets the value of the reeferSuitableTopTier property.
     * 
     */
    public boolean isReeferSuitableTopTier() {
        return reeferSuitableTopTier;
    }

    /**
     * Sets the value of the reeferSuitableTopTier property.
     * 
     */
    public void setReeferSuitableTopTier(boolean value) {
        this.reeferSuitableTopTier = value;
    }

    /**
     * Gets the value of the reeferSuitableBottomTier property.
     * 
     */
    public boolean isReeferSuitableBottomTier() {
        return reeferSuitableBottomTier;
    }

    /**
     * Sets the value of the reeferSuitableBottomTier property.
     * 
     */
    public void setReeferSuitableBottomTier(boolean value) {
        this.reeferSuitableBottomTier = value;
    }

}
