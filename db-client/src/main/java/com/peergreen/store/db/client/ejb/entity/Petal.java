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

	private String description;

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
	/**
	 * Method to retrieve the petal's vendor
	 * 
	 * @return the vendor which provides the petal
	 */
	public IVendor getVendor() {
		return this.petalId.getVendor();
	}

	/**
	 * Method to set the petal's vendor
	 * 
	 * @param vendor The vendor of the petal to set
	 */
	public void setVendor(IVendor vendor) {
		petalId.setVendor(vendor);
	}

	/**
	 * Method to retrieve the petal's artifactId
	 * 
	 * @return the artifactId of the petal
	 */
	public String getArtifactId() {
		return petalId.getArtifactId();
	}

	/**
	 * Method to set the petal's artifactId
	 * 
	 * @param artifactId The artifactId of the petal to set
	 */
	public void setArtifactId(String artifactId) {
		petalId.setArtifactId(artifactId);
	}

	/**
	 * Method to retrieve the petal's version
	 * 
	 * @return the version of the petal
	 */
	public String getVersion() {
		return petalId.getVersion();
	}

	/**
	 * Method to set the petal's version
	 * 
	 * @param version The version of the petal to set
	 */
	public void setVersion(String version) {
		petalId.setVersion(version);
	} 

	/**
	 * Method to retrieve the petal's description
	 * 
	 * @return the description of the petal
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method to set a description to the petal 
	 * 
	 * @param description The description of the petal to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method to retrieve the petal's category
	 * 
	 * @return the category which belongs the petal
	 */
	public ICategory getCategory() {
		return category;
	}

	/**
	 * Method to set the petal's category
	 * 
	 * @param category The category of the petal to set
	 */
	public void setCategory(ICategory category) {
		this.category = category;
	}

	/**
	 * Method to retrieve the petal's requirement
	 * 
	 * @return A Set of requirements of the petal instance
	 */
	public Set<IRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * Method to add requirements to the petal
	 * 
	 * @param requirement A Set of requirements to add for the petal 
	 */
	public void setRequirements(Set<IRequirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * Method to retrieve the petal's capabilities
	 * 
	 * @return A Set of capabilities of the petal instance
	 */
	public Set<ICapability> getCapabilities() {
		return capabilities;
	}

	/**
	 * Method to set the petal's capabilities
	 * 
	 * @param capabilities A Set of capabilities to add for the petal 
	 */
	public void setCapabilities(Set<ICapability> capabilities) {
		this.capabilities = capabilities;
	}

	/**
	 * Method to retrieve the petal's group
	 * 
	 * @return A Set of group which belongs the petal instance
	 */
	public Set<IGroup> getGroupSet() {
		return groupSet;
	}

	/**
	 * Method to set the petal's groups
	 * 
	 * @param groupSet A Set of group to add for the petal 
	 */
	public void setGroupSet(Set<IGroup> groupSet) {
		this.groupSet = groupSet;
	}

}
