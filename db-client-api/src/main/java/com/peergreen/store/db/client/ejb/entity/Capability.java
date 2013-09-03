package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.key.primary.CapabilityId;


/**
 * Entity Bean representing in the database the capability of a petal
 */
@NamedQueries({
    @NamedQuery (
            name = "Capability.findAll",
            query = "select cap from Capability cap"
            ),
            @NamedQuery (
                    name = "CapabilityByName",
                    query = "select cap from Capability cap where cap.capabilityName = :name and cap.version = :version"
                    ),
                    @NamedQuery (
                            name = "Requirement.findCapabilities",
                            query = "SELECT cap FROM Capability cap WHERE cap.namespace = :namespace"
                            )
})
@Entity
@IdClass(CapabilityId.class)
public class Capability{

    // TODO: add namespace to primary key

    private Integer hashCode = null;

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

    @Id
    @Column(name="namespace", nullable=false)
    private String namespace;

    //    @ElementCollection
    //    @CollectionTable( name="Properties")
    //    @MapKeyColumn (name="propertiesName")
    //    @Column(name="properties",nullable=false)
    //    private Map<String, String> properties;

    @OneToMany(mappedBy="capability", cascade = {CascadeType.ALL})
    @Column(name="properties", nullable=false)
    private Set<Property> properties = new HashSet<>();

    @ManyToMany(mappedBy="capabilities")
    @Column(name="petals", nullable=false)
    private Set<Petal> petals = new HashSet<>();

    public Capability() {

    }

    public Capability(String capabilityName, String version, String namespace,
            Set<Property> properties) {
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
     * @return Set containing all the properties of the capability
     */
    public Set<Property> getProperties() {
        return properties;
    }

    /**
     * Method for set the properties of the capability instance
     * 
     * @param properties the properties to set
     */
    public void setProperties(Set<Property> properties) {
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Capability)) {
            return false;
        }

        Capability cap = (Capability) obj;

        return ((cap.getCapabilityId() == this.capabilityId) &&
                (cap.capabilityName.equals(this.capabilityName)) &&
                (cap.version.equals(this.version)));
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = ("" + capabilityId + capabilityName + version + namespace).hashCode();
        }

        return hashCode;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        String s = capabilityId + "-" + capabilityName + ":" +
                version + ":" + namespace;
        return s;
    }

}
