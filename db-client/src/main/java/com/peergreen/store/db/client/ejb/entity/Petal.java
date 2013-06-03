package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;

@Entity
public class Petal implements IPetal {

	@EmbeddedId
	private PetalId petalId;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="categoryId", referencedColumnName="categoryId")
	private ICategory category;

	@JoinTable(name = "PETALS_GROUPS_MAP",
			joinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")},
			inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})
	private Set<IGroup> groupSet;

	@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<IRequirement> requirements;
	@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<ICapability> capabilities;


	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="vendor", referencedColumnName="vendorName")
	public IVendor getVendor() {
		return this.petalId.getVendor();
	}


	public void setVendor(IVendor vendor) {

		petalId.setVendor(vendor);

	}


	public String getArtifactId() {

		return petalId.getArtifactId();
	}


	public void setArtifactId(String artifactId) {
		petalId.setArtifactId(artifactId);
	}


	public String getVersion() {
		return petalId.getVersion();
	}


	public void setVersion(String version) {
		petalId.setVersion(version);
	} 

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
	public Set<IRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(Set<IRequirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * @return the capabilities
	 */
	public Set<ICapability> getCapabilities() {
		return capabilities;
	}

	/**
	 * @param capabilities the capabilities to set
	 */
	public void setCapabilities(Set<ICapability> capabilities) {
		this.capabilities = capabilities;
	}

	public Set<IGroup> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Set<IGroup> groupSet) {
		this.groupSet = groupSet;
	}

}
