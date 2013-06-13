package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Entity Bean representing in the database the capability of a petal
 */
@Entity
@Table(name="Capabilities")
@SequenceGenerator(name="idCapabilitySeq", initialValue=1, allocationSize=50)
public class Capability{

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCapabilitySeq")
    private int capabilityId;

    @Id
    private String capabilityName; 

    private String namespace;

    private Map<String, Object> properties; 

    @ManyToMany(mappedBy="capabilities")
    private Set<Petal> petals = new HashSet<>();

    /**
     * Method to get Id of a capability
     * @return the capability's id
     */
    public int getCapabilityId() {
        return capabilityId;
    }

    /**
     * Method to get the name of the capability instance
     * 
     * @return the name of the capability
     */
    public String getcapabilityName() {
        return this.capabilityName;
    }

    /**
     * Method to set the name of the capability instance
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.capabilityName = name;
    }

    /**
     * Method to get the namespace of the capability instance
     * 
     * @return the namespace of the capability
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Method to set the id of the capability instance
     * 
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Method for retrieve the properties of the capability instance
     * 
     * @return Map containing all the properties of the capability
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Method for set the properties of the capability instance
     * 
     * @param properties the properties to set
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Method to retrieve the petals which provides this capability instance
     * 
     * @return Set containing petals 
     */
    public Set<Petal> getPetals() {
        return petals;
    }

    /**
     * Method for add others petals to the Set of petals which provides
     * this capability instance
     * 
     * @param petals Set containing petals to set
     */
    public void setPetals(Set<Petal> petals) {
        this.petals = petals;
    }


}
