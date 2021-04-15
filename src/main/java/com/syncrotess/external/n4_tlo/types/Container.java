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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.syncrotess.external.n4_tlo.common.types.Length;
import com.syncrotess.external.n4_tlo.common.types.Weight;


/**
 * 
 * 				Container characteristics relevant for planning in TLO.
 * 			
 * 
 * <p>Java class for container complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="container">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.syncrotess.com/external/n4-tlo/types}containerId"/>
 *         &lt;element name="lengthType" type="{http://www.syncrotess.com/external/n4-tlo/types}containerLengthType"/>
 *         &lt;element name="tareWeight" type="{http://www.syncrotess.com/external/n4-tlo/common/types}weight"/>
 *         &lt;element name="height" type="{http://www.syncrotess.com/external/n4-tlo/common/types}length"/>
 *         &lt;element name="isoCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reefer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tank" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="flatrack" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="overheightOpenTop" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="wellSuitable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="empty" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pairable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pairedWith" type="{http://www.syncrotess.com/external/n4-tlo/types}containerId" minOccurs="0"/>
 *         &lt;element name="grossWeight" type="{http://www.syncrotess.com/external/n4-tlo/common/types}weight"/>
 *         &lt;element name="hazardous" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="oog" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="onlyGroundSlot" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="stackable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="holds" type="{http://www.syncrotess.com/external/n4-tlo/types}holds" minOccurs="0"/>
 *         &lt;element name="currentLocation" type="{http://www.syncrotess.com/external/n4-tlo/types}locationId"/>
 *         &lt;element name="toLocation" type="{http://www.syncrotess.com/external/n4-tlo/types}locationId" minOccurs="0"/>
 *         &lt;element name="arrivalTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="destination" type="{http://www.syncrotess.com/external/n4-tlo/types}destinationId" minOccurs="0"/>
 *         &lt;element name="railway" type="{http://www.syncrotess.com/external/n4-tlo/types}railwayId" minOccurs="0"/>
 *         &lt;element name="lockedOnSlot" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="specialStow1" type="{http://www.syncrotess.com/external/n4-tlo/types}specialStowType" minOccurs="0"/>
 *         &lt;element name="dangerousGoodsClass" type="{http://www.syncrotess.com/external/n4-tlo/types}dangerousGoodsClass" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "container", propOrder = {
    "id",
    "lengthType",
    "tareWeight",
    "height",
    "isoCode",
    "reefer",
    "tank",
    "flatrack",
    "overheightOpenTop",
    "wellSuitable",
    "empty",
    "pairable",
    "pairedWith",
    "grossWeight",
    "hazardous",
    "oog",
    "onlyGroundSlot",
    "stackable",
    "holds",
    "currentLocation",
    "toLocation",
    "arrivalTime",
    "priority",
    "destination",
    "railway",
    "lockedOnSlot",
    "specialStow1",
    "dangerousGoodsClass"
})
public class Container {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String lengthType;
    @XmlElement(required = true)
    protected Weight tareWeight;
    @XmlElement(required = true)
    protected Length height;
    @XmlElement(required = true)
    protected String isoCode;
    protected boolean reefer;
    protected boolean tank;
    protected boolean flatrack;
    protected boolean overheightOpenTop;
    protected boolean wellSuitable;
    protected boolean empty;
    protected boolean pairable;
    protected String pairedWith;
    @XmlElement(required = true)
    protected Weight grossWeight;
    protected boolean hazardous;
    protected boolean oog;
    protected boolean onlyGroundSlot;
    protected boolean stackable;
    protected Holds holds;
    @XmlElement(required = true)
    protected String currentLocation;
    protected String toLocation;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrivalTime;
    @XmlSchemaType(name = "unsignedByte")
    protected short priority;
    protected String destination;
    protected String railway;
    protected boolean lockedOnSlot;
    @XmlSchemaType(name = "string")
    protected SpecialStowType specialStow1;
    protected String dangerousGoodsClass;

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
     * Gets the value of the lengthType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLengthType() {
        return lengthType;
    }

    /**
     * Sets the value of the lengthType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLengthType(String value) {
        this.lengthType = value;
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
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Length }
     *     
     */
    public Length getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Length }
     *     
     */
    public void setHeight(Length value) {
        this.height = value;
    }

    /**
     * Gets the value of the isoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoCode() {
        return isoCode;
    }

    /**
     * Sets the value of the isoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoCode(String value) {
        this.isoCode = value;
    }

    /**
     * Gets the value of the reefer property.
     * 
     */
    public boolean isReefer() {
        return reefer;
    }

    /**
     * Sets the value of the reefer property.
     * 
     */
    public void setReefer(boolean value) {
        this.reefer = value;
    }

    /**
     * Gets the value of the tank property.
     * 
     */
    public boolean isTank() {
        return tank;
    }

    /**
     * Sets the value of the tank property.
     * 
     */
    public void setTank(boolean value) {
        this.tank = value;
    }

    /**
     * Gets the value of the flatrack property.
     * 
     */
    public boolean isFlatrack() {
        return flatrack;
    }

    /**
     * Sets the value of the flatrack property.
     * 
     */
    public void setFlatrack(boolean value) {
        this.flatrack = value;
    }

    /**
     * Gets the value of the overheightOpenTop property.
     * 
     */
    public boolean isOverheightOpenTop() {
        return overheightOpenTop;
    }

    /**
     * Sets the value of the overheightOpenTop property.
     * 
     */
    public void setOverheightOpenTop(boolean value) {
        this.overheightOpenTop = value;
    }

    /**
     * Gets the value of the wellSuitable property.
     * 
     */
    public boolean isWellSuitable() {
        return wellSuitable;
    }

    /**
     * Sets the value of the wellSuitable property.
     * 
     */
    public void setWellSuitable(boolean value) {
        this.wellSuitable = value;
    }

    /**
     * Gets the value of the empty property.
     * 
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets the value of the empty property.
     * 
     */
    public void setEmpty(boolean value) {
        this.empty = value;
    }

    /**
     * Gets the value of the pairable property.
     * 
     */
    public boolean isPairable() {
        return pairable;
    }

    /**
     * Sets the value of the pairable property.
     * 
     */
    public void setPairable(boolean value) {
        this.pairable = value;
    }

    /**
     * Gets the value of the pairedWith property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPairedWith() {
        return pairedWith;
    }

    /**
     * Sets the value of the pairedWith property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPairedWith(String value) {
        this.pairedWith = value;
    }

    /**
     * Gets the value of the grossWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getGrossWeight() {
        return grossWeight;
    }

    /**
     * Sets the value of the grossWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setGrossWeight(Weight value) {
        this.grossWeight = value;
    }

    /**
     * Gets the value of the hazardous property.
     * 
     */
    public boolean isHazardous() {
        return hazardous;
    }

    /**
     * Sets the value of the hazardous property.
     * 
     */
    public void setHazardous(boolean value) {
        this.hazardous = value;
    }

    /**
     * Gets the value of the oog property.
     * 
     */
    public boolean isOog() {
        return oog;
    }

    /**
     * Sets the value of the oog property.
     * 
     */
    public void setOog(boolean value) {
        this.oog = value;
    }

    /**
     * Gets the value of the onlyGroundSlot property.
     * 
     */
    public boolean isOnlyGroundSlot() {
        return onlyGroundSlot;
    }

    /**
     * Sets the value of the onlyGroundSlot property.
     * 
     */
    public void setOnlyGroundSlot(boolean value) {
        this.onlyGroundSlot = value;
    }

    /**
     * Gets the value of the stackable property.
     * 
     */
    public boolean isStackable() {
        return stackable;
    }

    /**
     * Sets the value of the stackable property.
     * 
     */
    public void setStackable(boolean value) {
        this.stackable = value;
    }

    /**
     * Gets the value of the holds property.
     * 
     * @return
     *     possible object is
     *     {@link Holds }
     *     
     */
    public Holds getHolds() {
        return holds;
    }

    /**
     * Sets the value of the holds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Holds }
     *     
     */
    public void setHolds(Holds value) {
        this.holds = value;
    }

    /**
     * Gets the value of the currentLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the value of the currentLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentLocation(String value) {
        this.currentLocation = value;
    }

    /**
     * Gets the value of the toLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToLocation() {
        return toLocation;
    }

    /**
     * Sets the value of the toLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToLocation(String value) {
        this.toLocation = value;
    }

    /**
     * Gets the value of the arrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the value of the arrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalTime(XMLGregorianCalendar value) {
        this.arrivalTime = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     */
    public short getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     */
    public void setPriority(short value) {
        this.priority = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the railway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRailway() {
        return railway;
    }

    /**
     * Sets the value of the railway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRailway(String value) {
        this.railway = value;
    }

    /**
     * Gets the value of the lockedOnSlot property.
     * 
     */
    public boolean isLockedOnSlot() {
        return lockedOnSlot;
    }

    /**
     * Sets the value of the lockedOnSlot property.
     * 
     */
    public void setLockedOnSlot(boolean value) {
        this.lockedOnSlot = value;
    }

    /**
     * Gets the value of the specialStow1 property.
     * 
     * @return
     *     possible object is
     *     {@link SpecialStowType }
     *     
     */
    public SpecialStowType getSpecialStow1() {
        return specialStow1;
    }

    /**
     * Sets the value of the specialStow1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecialStowType }
     *     
     */
    public void setSpecialStow1(SpecialStowType value) {
        this.specialStow1 = value;
    }

    /**
     * Gets the value of the dangerousGoodsClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDangerousGoodsClass() {
        return dangerousGoodsClass;
    }

    /**
     * Sets the value of the dangerousGoodsClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDangerousGoodsClass(String value) {
        this.dangerousGoodsClass = value;
    }

}