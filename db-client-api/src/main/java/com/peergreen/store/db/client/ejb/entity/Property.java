package com.peergreen.store.db.client.ejb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 * Entity class providing POJO to represent capability property.
 */
@Entity
public class Property {

    /** Property unique id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    /** Property name */
    private String name;
    /** Property value */
    private String value;
    /** Related capability */
    @OneToOne
    Capability capability;

    /**
     * Default constructor
     */
    public Property() {

    }

    /**
     * Parameterized constructor.
     * 
     * @param name property name
     * @param value property value
     */
    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Method to retrieve unique property id.
     * 
     * @return unique property id
     */
    public long getId() {
        return id;
    }

    /**
     * Method to retrieve property name.
     * 
     * @return property name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set property name.
     * 
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to retrieve property value.
     * 
     * @return property name
     */
    public String getValue() {
        return value;
    }

    /**
     * Method to set property value.
     * 
     * @param name value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to retrieve associated capability.
     * 
     * @return associated capability
     */
    public Capability getCapability() {
        return capability;
    }

    /**
     * Method to set associated capability.
     * 
     * @param name capability to set
     */
    public void setCapability(Capability capability) {
        this.capability = capability;
    }

}
