package com.peergreen.store.controller;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;
import com.peergreen.store.db.client.enumeration.Origin;

/**
 * Interface defining all petal related operations:
 * <ul>
 *      <li>Retrieve petal metadata or binary</li>
 *      <li>Create, remove or modify petals on database</li>
 *      <li>Create, retireve capabilities on database</li>
 *      <li>Add or remove capabilities to petals</li>
 *      <li>Create, retrieve requirements</li>
 *      <li>Add or remove requirements to petals</li>
 *      <li>Create, retrieve categories on database</li>
 *      <li>Create vendors on database</li>
 * </ul>
 * 
 */
public interface IPetalController {

    /**
     * Method to retrieve metadata related to a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal related metadata
     */
    Map<String, Object> getPetalMetadata(Vendor vendor, String artifactId, String version);

    /**
     * Method to retrieve all needed petals to install the provided one.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all needed petals for installation of the provided petal
     */
    Collection<PetalId> getTransitiveRequirements(Vendor vendor, String artifactId, String version);
    
    /**
     * Method to retrieve a petal from the local store.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or <em>null</em> if not available
     */
    File getPetal(Vendor vendor, String artifactId, String version);

    /**
     * Method to directly add a petal to the store.<br />
     * This method make the petal persistent in the store.
     * 
     * @param vendorName petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param petalBinary petal's binary
     * @param origin the petal's origin 
     */
    Petal addPetal(String vendorName, String artifactId,
            String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities,
            Origin origin, File petalBinary);

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void removePetal(Vendor vendor, String artifactId, String version);

    /**
     * Method to update a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param petalBinary petal's petalBinary
     * @return updated petal
     */
    Petal updatePetal(Vendor vendor, String artifactId,
            String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities,
            File petalBinary);

    /**
     * Method to add a new Capability to the database.
     * 
     * @param capabilityName capability's name
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     * @param petal the petal which provides this capability
     */
    Capability createCapability(String capabilityName, String namespace, Map<String,Object> properties, Petal petal);

    /**
     * Method to collect all the capabilities provided by a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all the capabilities provided by a petal
     */
    Collection<Capability> collectCapabilities(Vendor vendor, String artifactId, String version);

    /**
     * Method to add a capability to a petal's provided capabilities list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to add to the provided capabilites list of the petal
     * @return updated petal
     */
    Petal addCapability(Vendor vendor, String artifactId, String version, Capability capability);

    /**
     * Method to remove a capability from a petal's provided capabilities list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to remove from the provided capabilites  list of the petal
     * @return updated petal
     */
    Petal removeCapability(Vendor vendor, String artifactId, String version, Capability capability);

    /**
     * Method to add a new Requirement to the database.
     * 
     * @param requirementName requirement's name
     * @param namespace requirement's namespace
     * @param filter requirement's filter
     */
    Requirement createRequirement(String requirementName, String namespace, String filter);

    /**
     * Method to collect all the petal's requirements.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    Collection<Requirement> collectRequirements(Vendor vendor, String artifactId, String version);

    /**
     * Method to add a requirement to a petal's requirements list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to add to the petal's requirements list
     * @return updated petal
     */
    Petal addRequirement(Vendor vendor, String artifactId, String version, Requirement requirement);

    /**
     * Method to remove a requirement from a petal's requirements list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to remove from the petal's requirements
     * @return updated petal
     */
    Petal removeRequirement(Vendor vendor, String artifactId, String version, Requirement requirement);

    /**
     * Method to add a new category to the database.
     * 
     * @param categoryName cetegory's name
     */
    Category createCategory(String categoryName);

    /**
     * Method to retrieve a petal's category.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's category
     */
    Category getCategory(Vendor vendor, String artifactId, String version);

    /**
     * Method to set the petal's category.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param category petal's category
     * @return updated category
     */
    Petal setCategory(Vendor vendor, String artifactId, String version, Category category);

    /**
     * Method to create a new vendor on database.
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     * @return created vendor instance
     */
    Vendor createVendor(String vendorName, String vendorDescription);

}
