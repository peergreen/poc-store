package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.key.primary.RequirementId;


/**
 * Entity Bean representing in the database the requirement of a petal.
 */
@NamedQueries({
    @NamedQuery(
            name = "Requirement.findAll",
            query = "select r from Requirement r"),
    @NamedQuery(
            name = "RequirementByName",
            query = "select r from Requirement r where r.requirementName = :name"),        
})
@Entity
@IdClass(RequirementId.class)
public class Requirement {

    @Id
    @Column(name = "name", unique=true)
    private String requirementName;

    @Id
    @SequenceGenerator(name="idRequirementSeq", initialValue=1, allocationSize=50)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idRequirementSeq")
    @Column(name="id")
    private int requirementId;
    
    private String namespace;

    private String filter;

    @ManyToMany(mappedBy="requirements")
    private Set<Petal> petals;
    
    /**
     * Constructor with initializations.
     * 
     * @param requirementName requirement name
     * @param namespace requirement namespace
     * @param filter requirement filter
     */
    public Requirement(String requirementName,String namespace, String filter) {
        super();
        this.setRequirementName(requirementName);
        this.setNamespace(namespace);
        this.setFilter(filter);
        Set<Petal> petals = new HashSet<Petal>();
        this.setPetals(petals);
    }

    /**
     * Method to retrieve requirement's Id.
     * 
     * @return id of the requirement instance
     */
    public int getRequirementId() {
        return requirementId;
    }

    /**
     * Method to retrieve requirement's name.
     * 
     * @return name of the requirement instance
     */
    public String getRequirementName() {
        return requirementName;
    }

    /**
     * Method to set requirement name.
     * 
     * @param requirementName requirement name to set
     */
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    /**
     * Method to retrieve requirement's namespace.
     * 
     * @return namespace of the requirement instance
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Method to set requirement name.
     * 
     * @param namespace requirement namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Method to retrieve the requirement's filter 
     * 
     * @return the filter of the requirement instance
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Method to set requirement filter
     * 
     * @param filter filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Method to retrieve the petals which had this requirement.
     * 
     * @return set containing all the petals which had this requirement 
     */
    public Set<Petal> getPetals() {
        return petals;
    }

    /**
     * Method to set a collection of petals associated with this requirement.
     * 
     * @param petals set of petals that have this requirement
     */
    public void setPetals(Set<Petal> petals) {
        this.petals = petals;
    }
}
