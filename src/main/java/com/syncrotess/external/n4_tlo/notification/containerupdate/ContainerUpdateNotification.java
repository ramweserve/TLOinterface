//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.08 at 01:43:40 PM IST 
//


package com.syncrotess.external.n4_tlo.notification.containerupdate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
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
 *         &lt;element name="container">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}id"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}lengthType"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}tareWeight"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}height"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}isoCode"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}reefer"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}tank"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}flatrack"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}overheightOpenTop"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}wellSuitable"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}empty"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}pairable"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}pairedWith"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}grossWeight"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}hazardous"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}oog"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}onlyGroundSlot"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}stackable"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}holds"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}currentLocation"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}toLocation"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}arrivalTime"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}priority"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}destination"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}railway"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}lockedOnSlot"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}specialStow1"/>
 *                   &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}dangerousGoodsClass"/>
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
    "container"
})
@XmlRootElement(name = "ContainerUpdateNotification")
public class ContainerUpdateNotification {

    @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/common/types", required = true)
    protected Header header;
    @XmlElement(required = true)
    protected Container container;

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
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}id"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}lengthType"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}tareWeight"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}height"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}isoCode"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}reefer"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}tank"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}flatrack"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}overheightOpenTop"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}wellSuitable"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}empty"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}pairable"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}pairedWith"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}grossWeight"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}hazardous"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}oog"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}onlyGroundSlot"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}stackable"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}holds"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}currentLocation"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}toLocation"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}arrivalTime"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}priority"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}destination"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}railway"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}lockedOnSlot"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}specialStow1"/>
     *         &lt;element ref="{http://www.syncrotess.com/external/n4-tlo/types}dangerousGoodsClass"/>
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
    public static class Container {

        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String id;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        @XmlSchemaType(name = "unsignedByte")
        protected short lengthType;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected TareWeight tareWeight;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected Height height;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        @XmlSchemaType(name = "unsignedShort")
        protected int isoCode;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean reefer;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean tank;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean flatrack;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean overheightOpenTop;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean wellSuitable;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean empty;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean pairable;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String pairedWith;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected GrossWeight grossWeight;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean hazardous;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean oog;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean onlyGroundSlot;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean stackable;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected Holds holds;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String currentLocation;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String toLocation;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar arrivalTime;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        @XmlSchemaType(name = "unsignedByte")
        protected short priority;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String destination;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String railway;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        protected boolean lockedOnSlot;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types", required = true)
        protected String specialStow1;
        @XmlElement(namespace = "http://www.syncrotess.com/external/n4-tlo/types")
        @XmlSchemaType(name = "unsignedByte")
        protected short dangerousGoodsClass;

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
         */
        public short getLengthType() {
            return lengthType;
        }

        /**
         * Sets the value of the lengthType property.
         * 
         */
        public void setLengthType(short value) {
            this.lengthType = value;
        }

        /**
         * Gets the value of the tareWeight property.
         * 
         * @return
         *     possible object is
         *     {@link TareWeight }
         *     
         */
        public TareWeight getTareWeight() {
            return tareWeight;
        }

        /**
         * Sets the value of the tareWeight property.
         * 
         * @param value
         *     allowed object is
         *     {@link TareWeight }
         *     
         */
        public void setTareWeight(TareWeight value) {
            this.tareWeight = value;
        }

        /**
         * Gets the value of the height property.
         * 
         * @return
         *     possible object is
         *     {@link Height }
         *     
         */
        public Height getHeight() {
            return height;
        }

        /**
         * Sets the value of the height property.
         * 
         * @param value
         *     allowed object is
         *     {@link Height }
         *     
         */
        public void setHeight(Height value) {
            this.height = value;
        }

        /**
         * Gets the value of the isoCode property.
         * 
         */
        public int getIsoCode() {
            return isoCode;
        }

        /**
         * Sets the value of the isoCode property.
         * 
         */
        public void setIsoCode(int value) {
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
         *     {@link GrossWeight }
         *     
         */
        public GrossWeight getGrossWeight() {
            return grossWeight;
        }

        /**
         * Sets the value of the grossWeight property.
         * 
         * @param value
         *     allowed object is
         *     {@link GrossWeight }
         *     
         */
        public void setGrossWeight(GrossWeight value) {
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
         *     {@link String }
         *     
         */
        public String getSpecialStow1() {
            return specialStow1;
        }

        /**
         * Sets the value of the specialStow1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpecialStow1(String value) {
            this.specialStow1 = value;
        }

        /**
         * Gets the value of the dangerousGoodsClass property.
         * 
         */
        public short getDangerousGoodsClass() {
            return dangerousGoodsClass;
        }

        /**
         * Sets the value of the dangerousGoodsClass property.
         * 
         */
        public void setDangerousGoodsClass(short value) {
            this.dangerousGoodsClass = value;
        }

    }

}
