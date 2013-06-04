package com.peergreen.store.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;

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
    Map<String, String> getPetalMetadata(IVendor vendor, String artifactId, String version);

    /**
     * Method to retrieve a petal from the local store.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or <em>null</em> if not available
     */
    IPetal getPetal(IVendor vendor, String artifactId, String version);

    /**
     * Method to directly add a petal to the store.<br />
     * This method make the petal persistent in the store.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param properties petal's additional properties
     * @param petalBinary petal's binary
     */
    void addPetal(IVendor vendor, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void removePetal(IVendor vendor, String artifactId, String version);

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
     * @param properties petal's properties
     * @param petalBinary petal's petalBinary
     * @return updated petal
     */
    IPetal updatePetal(IVendor vendor, String artifactId,
            String version, String description, ICategory category,
            List<IRequirement> requirements, List<ICapability> capabilities,
            Map<String, String> properties, File petalBinary);

    /**
     * Method to add a new Capability to the database.
     * 
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     */
    void createCapability(String namespace, Map<String, String> properties);

    /**
     * Method to collect all the capabilities provided by a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all the capabilities provided by a petal
     */
    List<ICapability> collectCapabilities(IVendor vendor, String artifactId, String version);

    /**
     * Method to add a capability to a petal's provided capabilities list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to add to the provided capabilites list of the petal
     * @return updated petal
     */
    IPetal addCapability(IVendor vendor, String artifactId, String version, ICapability capability);

    /**
     * Method to remove a capability from a petal's provided capabilities list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to remove from the provided capabilites  list of the petal
     * @return updated petal
     */
    IPetal removeCapability(IVendor vendor, String artifactId, String version, ICapability capability);

    /**
     * Method to add a new Requirement to the database.
     * 
     * @param namespace requirement's related namespace
     * @param properties requirement's properties (metadata)
     */
    void createRequirement(String namespace, Map<String, String> properties);

    /**
     * Method to collect all the petal's requirements.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    List<IRequirement> collectRequirements(IVendor vendor, String artifactId, String version);

    /**
     * Method to add a requirement to a petal's requirements list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to add to the petal's requirements list
     * @return updated petal
     */
    IPetal addRequirement(IVendor vendor, String artifactId, String version, IRequirement requirement);

    /**
     * Method to remove a requirement from a petal's requirements list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to remove from the petal's requirements
     * @return updated petal
     */
    IPetal removeRequirement(IVendor vendor, String artifactId, String version, IRequirement requirement);
    
    /**
     * Method to add a new category to the database.
     * 
     * @param categoryName cetegory's name
     */
    void createCategory(String categoryName);
    
    /**
     * Method to retrieve a petal's category.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's category
     */
    ICategory getCategory(IVendor vendor, String artifactId, String version);
    
    /**
     * Method to set the petal's category.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param category petal's category
     * @return updated category
     */
    IPetal setCategory(IVendor vendor, String artifactId, String version, ICategory category);
    
    /**
     * Method to create a new vendor on database.
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     */
    void createVendor(String vendorName, String vendorDescription);
    
}
