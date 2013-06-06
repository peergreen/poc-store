package com.peergreen.store.db.client.ejb.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * Entity Bean representing in the database the capability of a petal
 */
@Entity
@Table(name="Capabilities")
public class Capability{

    @Id
    private String capabilityName; 

    private String namespace;

    private Map<String, String> properties;

    /*@JoinTable(name = "CAPABILITY_PETAL_MAP",
			joinColumns = {@JoinColumn(name = "capabilityId", referencedColumnName = "capabilityId")},
			inverseJoinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")})*/
    @ManyToMany(mappedBy="capabilities")
    private Set<Petal> petals;

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
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Method for set the properties of the capability instance
     * 
     * @param properties the properties to set
     */
    public void setProperties(Map<String, String> properties) {
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
