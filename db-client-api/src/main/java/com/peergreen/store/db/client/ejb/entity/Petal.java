package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.peergreen.store.db.client.ejb.key.primary.PetalId;

@Entity
@IdClass(PetalId.class)
@Table(name = "Petals")
public class Petal {

	@Id
    @Column(name = "vendor_Name")
	private String vendorName;
    @Id
	private String artifactId;
    @Id
    private String version;

/*	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="categoryId", referencedColumnName="categoryId")*/
	@ManyToOne
	private Category category;

	private String description;

	/*@JoinTable(name = "PETALS_GROUPS_MAP",
			joinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")},
			inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})*/
	@ManyToMany(mappedBy="petals")
	private Set<Group> groupSet;

	//@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@ManyToMany
	private Set<Requirement> requirements;
	
	//@ManyToMany(mappedBy="petals", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@ManyToMany
	private Set<Capability> capabilities;

	@ManyToOne
    @JoinColumn(name = "vendor_Name", referencedColumnName = "vendor_Name")
	private Vendor vendor;
	
	/**
	 * Method to retrieve the petal's vendor name
	 * 
	 * @return the vendor's name which provides the petal
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/**
	 * Method to set the petal's vendor name
	 * 
	 * @param vendor The vendor's name of the petal to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * Method to retrieve the petal's artifactId
	 * 
	 * @return the artifactId of the petal
	 */
	public String getArtifactId() {
		return this.artifactId;
	}

	/**
	 * Method to set the petal's artifactId
	 * 
	 * @param artifactId The artifactId of the petal to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Method to retrieve the petal's version
	 * 
	 * @return the version of the petal
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Method to set the petal's version
	 * 
	 * @param version The version of the petal to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
	public Category getCategory() {
		return category;
	}

	/**
	 * Method to set the petal's category
	 * 
	 * @param category The category of the petal to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Method to retrieve the petal's requirement
	 * 
	 * @return A Set of requirements of the petal instance
	 */
	public Set<Requirement> getRequirements() {
		return requirements;
	}

	/**
	 * Method to add requirements to the petal
	 * 
	 * @param requirement A Set of requirements to add for the petal 
	 */
	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * Method to retrieve the petal's capabilities
	 * 
	 * @return A Set of capabilities of the petal instance
	 */
	public Set<Capability> getCapabilities() {
		return capabilities;
	}

	/**
	 * Method to set the petal's capabilities
	 * 
	 * @param capabilities A Set of capabilities to add for the petal 
	 */
	public void setCapabilities(Set<Capability> capabilities) {
		this.capabilities = capabilities;
	}

	/**
	 * Method to retrieve the petal's group
	 * 
	 * @return A Set of group which belongs the petal instance
	 */
	public Set<Group> getGroupSet() {
		return groupSet;
	}

	/**
	 * Method to set the petal's groups
	 * 
	 * @param groupSet A Set of group to add for the petal 
	 */
	public void setGroupSet(Set<Group> groupSet) {
		this.groupSet = groupSet;
	}
	
	/**
     * Method to retrieve the petal's vendor 
     * 
     * @return the vendor which provides the petal
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * Method to set the petal's vendor
     * 
     * @param vendor The vendor of the petal to set
     */
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

}
