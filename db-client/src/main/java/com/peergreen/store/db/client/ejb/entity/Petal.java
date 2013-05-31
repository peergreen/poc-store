package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="idPetalSeq", initialValue=1, allocationSize=50)
public class Petal {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idPetalSeq")
	private int petalId;
	private String groupId;
	private String artifactid;
	private String version;
	private String description; 
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="categoryId", referencedColumnName="categoryId")
	private Category category;

	@JoinTable(name = "PETALS_GROUPS_MAP",
			joinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")},
			inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})
	private List<Group> groupSet;

	@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Requirement> requirements;
	@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Capability> capabilities;

	
	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * @return the capabilities
	 */
	public List<Capability> getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities the capabilities to set
	 */
	public void setCapabilities(List<Capability> capabilities) {
		this.capabilities = capabilities;
	}

	public List<Group> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(List<Group> groupSet) {
		this.groupSet = groupSet;
	} 

}
