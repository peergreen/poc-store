package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.key.primary.CapabilityId;


/**
 * Entity Bean representing in the database the capability of a petal
 */
@NamedQueries({
    @NamedQuery(
            name = "Capability.findAll",
            query = "select cap from Capability cap"
            ),
            @NamedQuery(
                    name = "CapabilityByName",
                    query="select cap from Capability cap where cap.capabilityName = :name and cap.version = :version"
                    )
})
@Entity
@IdClass(CapabilityId.class)
public class Capability{


    @Id
    @Column(name = "name")
    private String capabilityName;

    @Id
    @SequenceGenerator(name="idCapabilitySeq", initialValue=1, allocationSize=50)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCapabilitySeq")
    @Column(name = "id")
    private int capabilityId;

    @Id 
    @Column(name="version")
    private String version;

    @Column(name="namespace", nullable=false)
    private String namespace;

    @ElementCollection
    @CollectionTable( name="Properties")
    @MapKeyColumn (name="propertiesName")
    @Column(name="properties",nullable=false)
    private Map<String, String> properties; 

    @ManyToMany(mappedBy="capabilities", fetch=FetchType.LAZY)
    @Column(name="petals", nullable=false)
    private Set<Petal> petals = new HashSet<>();

    public Capability(){

    }

    public Capability(String capabilityName,String version, String namespace,
            Map<String, String> properties) {
        super();
        this.capabilityName = capabilityName;
        this.version = version;
        this.namespace = namespace;
        this.properties = properties;
        Set<Petal> petals = new HashSet<Petal>();
        setPetals(petals);
    }


    /**
     * Method to get the name of the capability instance
     * 
     * @return the name of the capability
     */
    public String getCapabilityName() {
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
     * Method to get Id of a capability
     * @return the capability's id
     */
    public int getCapabilityId() {
        return capabilityId;
    }

    /**
     * Method to retrieve a capability version 
     * @return The version of this instance of capability
     */
    public String getVersion() {
        return version;
    }

    /**
     * Method to set the version of a capability
     * 
     * @param version A version to set for an instance of capability
     */
    public void setVersion(String version) {
        this.version = version;
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
