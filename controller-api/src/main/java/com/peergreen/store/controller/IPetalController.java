package com.peergreen.store.controller;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.peergreen.store.controller.util.DependencyRequest;
import com.peergreen.store.controller.util.DependencyResult;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all petal related operations:
 * <ul>
 *      <li>Retrieve petal metadata or binary</li>
 *      <li>Resolve petal's dependencies</li>
 *      <li>Create, retrieve or remove vendors on database</li>
 *      <li>Create, remove or modify petals on database</li>
 *      <li>Create, retrieve capabilities on database</li>
 *      <li>Add or remove capabilities to petals</li>
 *      <li>Create, retrieve requirements</li>
 *      <li>Add or remove requirements to petals</li>
 *      <li>Create, retrieve categories on database</li>
 *      <li>Create vendors on database</li>
 * </ul>
 * 
 */
public interface IPetalController {

    // TODO add:
    /*
     * collectProperties
     * createProperty
     * addProperty
     * removeProperty
     */

    /**
     * Method to retrieve metadata related to a petal.
     * 
     * @param vendorName name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal related metadata
     * @throws NoEntityFoundException
     */
    Map<String, Object> getPetalMetadata(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException;

    /**
     * Method to retrieve all petals available for each required capability.
     * 
     * @param request DependencyRequest containing all constraints.
     * @return list of all petals available for each required capability
     * @throws NoEntityFoundException 
     */
    DependencyResult getTransitiveDependencies(DependencyRequest request)
            throws NoEntityFoundException;

    /**
     * Method to create a new vendor on database.
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     * @return created vendor instance
     * @throws EntityAlreadyExistsException 
     */
    Vendor createVendor(String vendorName, String vendorDescription)
            throws EntityAlreadyExistsException;

    /**
     * Method to collect all existing vendors in database.
     * 
     * @return list of all existing vendors in database
     */
    Collection<Vendor> collectVendors();

    /**
     * Method to retrieve a vendor thanks to its name.
     * 
     * @param name vendor name
     * @return specified vendor, or {@literal null}
     * if there is no corresponding vendor
     */
    Vendor getVendor(String name);

    /**
     * Method to update the description of a vendor.<br />
     * Throws {@link NoEntityFoundException} if the vendor does not exists.
     * 
     * @param name vendor name
     * @param description new description for the vendor 
     * @return updated vendor
     * @throws NoEntityFoundException
     */
    Vendor updateVendor(String name, String description) 
            throws NoEntityFoundException;

    /**
     * Method to retrieve a petal from the local store.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or <em>null</em> if not available
     */
    File getPetal(String vendorName, String artifactId, String version);

    /**
     * Method to directly add a petal to the store.<br />
     * This method make the petal persistent in the store.
     * 
     * @param vendorName the name of the petal's vendor 
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's capabilities
     * @param origin the petal's origin
     * @param petalBinary petal's binary
     * @return corresponding Petal instance
     * @throws EntityAlreadyExistsException 
     * @throws NoEntityFoundException 
     */
    Petal addPetal(
            String vendorName,
            String artifactId,
            String version,
            String description,
            Category category,
            Set<Requirement> requirements,
            Set<Capability> capabilities,
            Origin origin,
            File petalBinary)
                    throws NoEntityFoundException, EntityAlreadyExistsException;

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return The petal removed 
     */
    Petal removePetal(String vendorName, String artifactId, String version);

    /**
     * Method to add a new Capability to the database.
     * 
     * @param capabilityName capability's name
     * @param version capability's version
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     * @throws EntityAlreadyExistsException 
     */
    Capability createCapability(
            String capabilityName,
            String version,
            String namespace,
            Set<Property> properties) throws EntityAlreadyExistsException;

    /**
     * Method to collect all the capabilities provided by a petal.
     * 
     * @param vendorName the petal of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all the capabilities provided by a petal
     * @throws NoEntityFoundException 
     */
    Collection<Capability> collectCapabilities(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException;

    /**
     * Method to retrieve a capability.
     * 
     * @param name capability name
     * @param version capability version
     * @return found capability or {@literal null}
     * if there is no corresponding capability
     */
    Capability getCapability(String name, String version);

    /**
     * Method to add a capability to a petal's provided capabilities list.
     * 
     * @param vendorName the petal of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param name the name of the capability to add
     * to the provided capabilities list of the petal
     * @param version the version of the capability to add
     * to the provided capabilities list of the petal
     * @return updated petal
     * @throws NoEntityFoundException 
     */
    Petal addCapability(
            String vendorName,
            String artifactId,
            String version,
            String name,
            String capabilityVersion) throws NoEntityFoundException;

    /**
     * Method to remove a capability from a petal's provided capabilities list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param name the name of capability to remove
     * from the provided capabilities list of the petal
     * @param version the version of capability to remove
     * from the provided capabilities list of the petal
     * @return updated petal
     * @throws NoEntityFoundException 
     */
    Petal removeCapability(
            String vendorName,
            String artifactId,
            String version,
            String name,
            String capabilityVersion) throws NoEntityFoundException;

    /**
     * Method to add a new Requirement to the database.
     * 
     * @param requirementName requirement's name
     * @param filter requirement's filter
     * @throws EntityAlreadyExistsException 
     */
    Requirement createRequirement(
            String requirementName,
            String namespace, 
            String filter) throws EntityAlreadyExistsException;

    /**
     * Method to collect all the petal's requirements.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @throws NoEntityFoundException 
     */
    Collection<Requirement> collectRequirements(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException;

    /**
     * Method to retrieve a requirement.
     * 
     * @param name requirement name
     * @return found requirement or {@literal null}
     * if there is no corresponding requirement
     */
    Requirement getRequirement(String name);

    /**
     * Method to add a requirement to a petal's requirements list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirementName the name of requirement to add
     * to the petal's requirements list
     * @return updated petal
     * @throws NoEntityFoundException 
     */
    Petal addRequirement(
            String vendorName,
            String artifactId,
            String version,
            String requirementName) throws NoEntityFoundException;

    /**
     * Method to remove a requirement from a petal's requirements list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirementName the name of requirement to remove
     * from the petal's requirements
     * @return updated petal
     * @throws NoEntityFoundException 
     */
    Petal removeRequirement(
            String vendorName,
            String artifactId,
            String version,
            String requirementName) throws NoEntityFoundException;

    /**
     * Method to retrieve a petal's category.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's category
     * @throws NoEntityFoundException 
     */
    Category getCategory(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException;

    /**
     * Method to set the petal's category.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param categoryName the name of petal's category
     * @return updated category
     * @throws NoEntityFoundException 
     */
    Petal setCategory(
            String vendorName,
            String artifactId,
            String version,
            String categoryName) throws NoEntityFoundException;

    /**
     * Method to get all the petals which has the requirement given 
     * @param name the requirement's name
     * @throws NoEntityFoundException 
     */
    Collection<Petal> getPetalsForRequirement(String name)
            throws NoEntityFoundException;

    /**
     * Method to get all the petals which provide the capability given 
     * @param name the capability's name
     * @throws NoEntityFoundException 
     */
    Collection<Petal> getPetalsForCapability(String name, String version) 
            throws NoEntityFoundException;

    /**
     * Method to modify description of a petal.
     * @param vendorName name of the vendor which provide the petal))
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description the new description 
     * @return updated petal
     * @throws NoEntityFoundException 
     */
    Petal updateDescription(
            String vendorName,
            String artifactId,
            String version,
            String description) throws NoEntityFoundException; 

}
