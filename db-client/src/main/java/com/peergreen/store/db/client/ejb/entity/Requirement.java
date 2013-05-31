package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;

/**
 * Entity Bean representing in the database the requirement of a petal  
 */
@Entity
@SequenceGenerator(name="idRequirementSeq", initialValue=1, allocationSize=50)
public class Requirement {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idRequirementSeq")
	private int requirementId;
	private String filter;
	@JoinTable(name = "REQUIREMENT_PETAL_MAP",
			joinColumns = {@JoinColumn(name = "requirementId", referencedColumnName = "requirementId")},
			inverseJoinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")})
	private List<Petal> petals;

	/**
	 * @return the requirementId
	 */
	public int getRequirementId() {
		return requirementId;
	}

	/**
	 * @param requirementId the requirementId to set
	 */
	public void setRequirementId(int requirementId) {
		this.requirementId = requirementId;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
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
