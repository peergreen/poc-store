package com.peergreen.store.db.client.ejb.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


/**
 * Entity Bean representing in the database the capability of a petal
 */
@Entity
@SequenceGenerator(name="idCapabilitySeq", initialValue=1, allocationSize=50)
public class Capability {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCapabilitySeq")
    private int capabilityId;
    private String namespace;
    private Map<String, String> properties;
    private List<Petal> petals;

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * @return the petals
     */
    public List<Petal> getPetals() {
        return petals;
    }

    /**
     * @param petals the petals to set
     */
    public void setPetals(List<Petal> petals) {
        this.petals = petals;
    }

}
