package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.key.primary.PetalId;
import com.peergreen.store.db.client.enumeration.Origin;

@NamedQueries({
    @NamedQuery(
            name = "Petal.findAll", 
            query = "select p from Petal p"),
            @NamedQuery (
                    name = "Petal.find",
                    query = "select p from Petal p where " +
                            "p.vendor = :vendor and " +
                            "p.artifactId = :artifactId " +
                            "and p.version = :version"
                    ),
                    @NamedQuery (
                            name = "Petal.findById",
                            query = "select p from Petal p where p.pid = :pid"
                            )
})
@Entity
@IdClass(PetalId.class)
public class Petal {

    /**
     * Vendor who provide the petal. A petal is provided by 
     * one and only one vendor.
     */
    @ManyToOne
    @Id
    private Vendor vendor;

    /**
     * ArtifactId of the petal.
     */
    @Id
    private String artifactId;

    /**
     * Version of the petal.
     */
    @Id
    private String version;

    /**
     * Generated id of the petal.
     */
    @Id
    @SequenceGenerator(name="idPetalSeq", initialValue=1, allocationSize=50)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idPetalSeq")
    private int pid;

    /**
     * Category of the petal. A petal belongs to only one category.
     */
    @ManyToOne
    private Category category;

    /**
     * Description of the petal.
     */
    private String description;

    /**
     * Set of the groups which have access to the petal.
     */
    @ManyToMany(mappedBy="petals")
    private Set<Group> groupSet;

    /**
     * Set of the requirements of the petal.
     */
    @ManyToMany
    private Set<Requirement> requirements;

    /**
     * Set of the capabilities provided by the petal.
     */
    @ManyToMany
    private Set<Capability> capabilities;

    /**
     * Origin of the petal.
     */
    private Origin origin;

    /**
     * Default Constructor.
     */
    public Petal() {

    }

    /**
     * Construct a petal with the specified parameters.
     * @param vendor vendor who provide the petal to create
     * @param artifactId artifactId of the petal to create
     * @param version version of the petal to create
     * @param category category of the petal to create
     * @param description descrption of the petal to create
     * @param requirements requirements of the petal to create
     * @param capabilities capabilities provided by the petal to create
     * @param origin origin of the petal to create
     */
    public Petal(Vendor vendor, String artifactId, String version, Category category, String description,
            Set<Requirement> requirements, Set<Capability> capabilities, Origin origin) {
        super();
        this.vendor = vendor;
        this.artifactId = artifactId;
        this.version = version;
        this.category = category;
        this.description = description;
        this.groupSet = new HashSet<>();
        this.requirements = requirements;
        this.capabilities = capabilities;
        this.origin = origin;
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
     * Method to retrieve the petal's version
     * 
     * @return the version of the petal
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * @return the pid
     */
    public int getPid() {
        return this.pid;
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
    public Set<Group> getGroups() {
        return groupSet;
    }

    /**
     * Method to set the petal's groups
     * 
     * @param groupSet A Set of group to add for the petal 
     */
    public void setGroups(Set<Group> groupSet) {
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
     * Method to get the petal's origin
     * 
     * @return the origin of the petal 
     */
    public Origin getOrigin() {
        return origin;
    }

    /**
     * Method to set the petal origin
     * 
     * @param origin the origin to set
     */
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return artifactId + ":" + description + ":" + version;
    }

}
