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

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;

@Entity
@SequenceGenerator(name="idPetalSeq", initialValue=1, allocationSize=50)
public class Petal implements IPetal {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idPetalSeq")
	private int petalId;
	private String groupId;
	private String artifactid;
	private String version;
	private String description; 
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="categoryId", referencedColumnName="categoryId")
	private ICategory category;

	@JoinTable(name = "PETALS_GROUPS_MAP",
			joinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")},
			inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})
	private List<IGroup> groupSet;

	@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<IRequirement> requirements;
	@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<ICapability> capabilities;

	
	/**
	 * @return the category
	 */
	public ICategory getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(ICategory category) {
		this.category = category;
	}

	/**
	 * @return the requirements
	 */
	public List<IRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(List<IRequirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * @return the capabilities
	 */
	public List<ICapability> getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities the capabilities to set
	 */
	public void setCapabilities(List<ICapability> capabilities) {
		this.capabilities = capabilities;
	}

	public List<IGroup> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(List<IGroup> groupSet) {
		this.groupSet = groupSet;
	} 

}
