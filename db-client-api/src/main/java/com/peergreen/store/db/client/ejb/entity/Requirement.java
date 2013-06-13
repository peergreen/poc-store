package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Bean representing in the database the requirement of a petal  
 */
@Entity
@Table(name="Requirements")
@SequenceGenerator(name="idRequirementSeq", initialValue=1, allocationSize=50)
public class Requirement {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idRequirementSeq")
	private int requirementId;
	
	private String requirementName;

	private String filter;
	
	private String namespace;

	@ManyToMany(mappedBy="requirements")
	private Set<Petal> petals;

	/**
	 * Method to retrieve the requirement's Id
	 * 
	 * @return the id of the requirement instance
	 */
	public int getRequirementId() {
		return requirementId;
	}

	/**
     * @return the requirementName
     */
    public String getRequirementName() {
        return requirementName;
    }

    /**
     * @param requirementName the requirementName to set
     */
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
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
	 * Method to set the requiremeent's filter
	 * 
	 * @param filter The filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Method to retrieve the petals which had this requirement
	 * 
	 * @return A Set containing all the petals which had this requirement 
	 */
	public Set<Petal> getPetals() {
		return petals;
	}

	/**
	 * Method to add new petals that have this requirement
	 * 
	 * @param petals A Set of new petals that have this requirement
	 */
	public void setPetals(Set<Petal> petals) {
		this.petals = petals;
	}

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

}
