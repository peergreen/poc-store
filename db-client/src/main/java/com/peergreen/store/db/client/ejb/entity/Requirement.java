package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;

/**
 * Entity Bean representing in the database the requirement of a petal  
 */
@Entity
@SequenceGenerator(name="idRequirementSeq", initialValue=1, allocationSize=50)
public class Requirement implements IRequirement {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idRequirementSeq")
	private int requirementId;
	private String filter;
	@JoinTable(name = "REQUIREMENT_PETAL_MAP",
			joinColumns = {@JoinColumn(name = "requirementId", referencedColumnName = "requirementId")},
			inverseJoinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")})
	private Set<IPetal> petals;

	/**
	 * @return the requirementId
	 */
	public int getRequirementId() {
		return requirementId;
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
	public Set<IPetal> getPetals() {
		return petals;
	}

	/**
	 * @param petals the petals to set
	 */
	public void setPetals(Set<IPetal> petals) {
		this.petals = petals;
	}

}
