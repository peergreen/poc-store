package com.peergreen.store.controller.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.controller.util.DependencyRequest;
import com.peergreen.store.controller.util.DependencyResult;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all petal related operations.
 * <ul>
 *      <li>Retrieve petal metadata or binary</li>
 *      <li>Resolve petal's dependencies</li>
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
@Component
@Instantiate
@Provides
public class DefaultPetalController implements IPetalController {

    private ISessionCapability capabilitySession;
    private ISessionCategory categorySession;
    private ISessionPetal petalSession;
    private ISessionRequirement requirementSession;
    private ISessionVendor vendorSession;
    private ISessionGroup groupSession;
    /** reference to the aether client for petal persistence. */
    private IPetalsPersistence petalPersistence;
    /** resolver to get all petal"s transitive dependencies. */
    private static Logger theLogger =
            Logger.getLogger(DefaultPetalController.class.getName());

    /**
     * Method to retrieve metadata related to a petal.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal related metadata
     * @throws NoEntityFoundException
     */
    @Override
    public final Map<String, Object> getPetalMetadata(
            String vendorName,
            String artifactId,
            String version)
                    throws NoEntityFoundException {

        Vendor v = vendorSession.findVendor(vendorName);

        if (v == null) {
            throw new NoEntityFoundException("Vendor with name "
                    + vendorName + " does not exist in database.");
        }

        Petal petal = petalSession.findPetal(v, artifactId, version);

        HashMap<String, Object> metadata = new HashMap<String, Object>();

        if (petal != null) {
            try {
                metadata.put("vendor", petal.getVendor());
                metadata.put("artifactId", petal.getArtifactId());
                metadata.put("version", petal.getVersion());
                metadata.put("description", petal.getDescription());
                metadata.put("category", petalSession.getCategory(petal));
                metadata.put("requirements",
                        petalSession.collectRequirements(petal));
                metadata.put("capabilities",
                        petalSession.collectCapabilities(petal));
                metadata.put("groups",
                        petalSession.collectGroups(petal));
                metadata.put("origin", petal.getOrigin());
            } catch (NoEntityFoundException e) {
                theLogger.log(Level.SEVERE, e.getMessage());
                throw new NoEntityFoundException(e);
            }
        }

        return metadata;
    }

    /**
     * Method to retrieve all petals available for each required capability.
     *
     * @param request DependencyRequest containing all constraints.
     * @return list of all petals available for each required capability
     * @throws NoEntityFoundException
     * @see DependencyRequest, DependencyResult
     */
    @Override
    public final DependencyResult getTransitiveDependencies(
            DependencyRequest request) throws NoEntityFoundException {
        // create a DependencyResult
        DependencyResult result = new DependencyResult();

        Petal petal = petalSession.findPetal(
                request.getVendor(),
                request.getArtifactId(),
                request.getVersion());

        Collection<Requirement> requirements = petalSession.
                collectRequirements(petal);
        for (Requirement req : requirements) {
            // retrieve capabilities which meet
            // the requirements in a same namespace
            Collection<Capability> capabilities =
                    requirementSession.findCapabilities(req);

            if (capabilities.size() == 0) {
                result.addUnresolvedRequirement(req);
            } else {
                // retrieve petals providing the capability
                Set<Petal> matchingPetals = new HashSet<>();
                for (Capability capability : capabilities) {
                    // index petals providing the capability
                    matchingPetals.addAll(capabilitySession.
                            collectPetals(capability.getCapabilityName(),
                                    capability.getVersion(),
                                    capability.getNamespace()));
                }
                result.addResolvedDependency(req, matchingPetals);
            }
        }

        return result;
    }

    /**
     * Method to create a new vendor on database.
     *
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     * @return created vendor instance
     * @throws EntityAlreadyExistsException
     */
    public final Vendor createVendor(
            String vendorName,
            String vendorDescription)
                    throws EntityAlreadyExistsException {

        try {
            return vendorSession.addVendor(vendorName, vendorDescription);
        } catch (EntityAlreadyExistsException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new EntityAlreadyExistsException(e);
        }
    }

    /**
     * Method to collect all existing vendors in database.
     *
     * @return list of all existing vendors in database
     */
    @Override
    public final Collection<Vendor> collectVendors() {
        return vendorSession.collectVendors();
    }

    /**
     * Method to retrieve a vendor thanks to its name.
     *
     * @param name vendor name
     * @return specified vendor, or {@literal null}
     * if there is no corresponding vendor
     */
    @Override
    public final Vendor getVendor(String name) {
        return vendorSession.findVendor(name);
    }

    /**
     * Method to update the description of a vendor.<br />
     * Throws {@link NoEntityFoundException} if the vendor does not exists.
     *
     * @param name vendor name
     * @param description new description for the vendor
     * @return updated vendor
     * @throws NoEntityFoundException
     */
    @Override
    public final Vendor updateVendor(String name, String description)
            throws NoEntityFoundException {
        try {
            return vendorSession.updateVendor(name, description);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to retrieve a petal from the local store.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or {@literal null} if not available
     */
    @Override
    public final Petal getPetal(
            String vendorName,
            String artifactId,
            String version) {

        Vendor v = vendorSession.findVendor(vendorName);
        if (v != null) {
            return petalSession.findPetal(v, artifactId, version);
        }

        return null;
    }

    /**
     * Method to retrieve a petal from the local store.
     *
     * @param id petal's id
     * @return corresponding petal or {@literal null} if not available
     */
    @Override
    public final Petal getPetalById(int id) {
        return petalSession.findPetalById(id);
    }

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
     * @param petalBinary petal's binary
     * @param origin petal's origin
     * @return petal created in database
     * after binary has been added to local store.
     * @throws EntityAlreadyExistsException
     * @throws NoEntityFoundException
     */
    @Override
    public final Petal addPetal(
            String vendorName,
            String artifactId,
            String version,
            String description,
            Category category,
            Set<Requirement> requirements,
            Set<Capability> capabilities,
            Origin origin, File petalBinary)
                    throws NoEntityFoundException,
                    EntityAlreadyExistsException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        petalPersistence.addToLocal(
                vendorName,
                artifactId,
                version,
                petalBinary);

        try {
            return petalSession.addPetal(
                    vendor,
                    artifactId,
                    version,
                    description,
                    category,
                    capabilities,
                    requirements,
                    origin);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        } catch (EntityAlreadyExistsException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new EntityAlreadyExistsException(e);
        }
    }

    /**
     * Method to remove a petal from the store thanks to its information.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return removed petal, or {@literal null}
     * if no corresponding petal to remove
     */
    @Override
    public final Petal removePetal(
            String vendorName,
            String artifactId,
            String version) {
        /*
         * Can't remove petal from Maven repository.
         * So remove all group's permission on it
         * except admin group.
         *
         */
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Collection<Group> groups;
        try {
            groups = petalSession.collectGroups(petal);
            Iterator<Group> itgrp = groups.iterator();
            while (itgrp.hasNext()) {
                Group group = itgrp.next();
                if (!group.getGroupname().equalsIgnoreCase("Administrator")) {
                    groupSession.removePetal(group, petal);
                }
            }
            return petal;
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    /**
     * Method to add a new Capability to the database.
     *
     * @param capabilityName capability's name
     * @param version capability's version
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     * @return created capability
     * @throws EntityAlreadyExistsException
     */
    @Override
    public final Capability createCapability(
            String capabilityName,
            String version,
            String namespace,
            Set<Property> properties) throws EntityAlreadyExistsException {

        try {
            return capabilitySession.addCapability(
                    capabilityName,
                    version,
                    namespace,
                    properties);
        } catch (EntityAlreadyExistsException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new EntityAlreadyExistsException(e);
        }
    }

    /**
     * Method to collect all the capabilities provided by a petal.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all the capabilities provided by a petal
     * @throws NoEntityFoundException
     */
    @Override
    public final Collection<Capability> collectCapabilities(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        try {
            return  petalSession.collectCapabilities(petal);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);

        }
    }

    /**
     * Method to retrieve a capability.
     *
     * @param name capability name
     * @param version capability version
     * @param namespace capability namespace
     * @return found capability or {@literal null}
     * if there is no corresponding capability
     */
    @Override
    public final Capability getCapability(
            String name,
            String version,
            String namespace) {

        return capabilitySession.findCapability(name, version, namespace);
    }

    /**
     * Method to add a capability to a petal's provided capabilities list.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param name name of the capability to add
     * @param capabilityVersion version of the capability to add
     * @param namespace namespace of the capability to add
     * @return updated petal
     * @throws NoEntityFoundException
     */
    @Override
    public final Petal addCapability(
            String vendorName,
            String artifactId,
            String version,
            String name,
            String capabilityVersion,
            String namespace) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Capability capability = capabilitySession
                .findCapability(name, capabilityVersion, namespace);

        try {
            return petalSession.addCapability(petal, capability);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to remove a capability from a petal's provided capabilities list.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param name name of the capability to add
     * @param capabilityVersion version of the capability to add
     * @param namespace namespace of the capability to add
     * @return updated petal
     * @throws NoEntityFoundException
     */
    @Override
    public final Petal removeCapability(
            String vendorName,
            String artifactId,
            String version,
            String name,
            String capabilityVersion,
            String namespace) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Capability capability = capabilitySession
                .findCapability(name, capabilityVersion, namespace);
        try {
            return petalSession.removeCapability(petal, capability);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to add a new Requirement to the database.
     *
     * @param requirementName requirement's name
     * @param namespace requirement's namespace
     * @param filter requirement's filter
     * @return created requirement
     * @throws EntityAlreadyExistsException
     */
    @Override
    public final Requirement createRequirement(
            String requirementName,
            String namespace,
            String filter) throws EntityAlreadyExistsException {

        try {
            return requirementSession
                    .addRequirement(requirementName, namespace, filter);
        } catch (EntityAlreadyExistsException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new EntityAlreadyExistsException(e);
        }
    }

    /**
     * Method to collect all the petal's requirements.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return collection containing all existing requirements
     * @throws NoEntityFoundException
     */
    @Override
    public final Collection<Requirement> collectRequirements(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        try {
            return petalSession.collectRequirements(petal);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to retrieve a requirement.
     *
     * @param name requirement name
     * @return found requirement or {@literal null}
     * if there is no corresponding requirement
     */
    @Override
    public final Requirement getRequirement(String name) {
        return requirementSession.findRequirement(name);
    }

    /**
     * Method to add a requirement to a petal's requirements list.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirementName the name of the requirement
     * to add to the petal's requirements list
     * @return updated petal
     * @throws NoEntityFoundException
     */
    @Override
    public final Petal addRequirement(
            String vendorName,
            String artifactId,
            String version,
            String requirementName) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Requirement requirement = requirementSession
                .findRequirement(requirementName);
        try {
            return petalSession.addRequirement(petal, requirement);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to remove a requirement from a petal's requirements list.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirementName the name of  requirement
     * to remove from the petal's requirements
     * @return updated petal
     * @throws NoEntityFoundException
     */
    @Override
    public final Petal removeRequirement(
            String vendorName,
            String artifactId,
            String version,
            String requirementName) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Requirement requirement = requirementSession
                .findRequirement(requirementName);
        try {
            return petalSession.removeRequirement(petal, requirement);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to retrieve a petal's category.
     *
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's category
     * @throws NoEntityFoundException
     */
    @Override
    public final Category getCategory(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        try {
            return petalSession.getCategory(petal);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

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
    @Override
    public final Petal setCategory(
            String vendorName,
            String artifactId,
            String version,
            String categoryName) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Category category = categorySession.findCategory(categoryName);
        try {
            return petalSession.addCategory(petal, category);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to get all the petals which has the requirement given.
     *
     * @param name the requirement's name
     * @return collection of petals sharing this requirement
     * @throws NoEntityFoundException
     */
    @Override
    public final Collection<Petal> getPetalsForRequirement(String name)
            throws NoEntityFoundException {

        try {
            return  requirementSession.collectPetals(name);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to get all the petals which provide the capability given.
     *
     * @param name the capability's name
     * @param version the capabilty's version
     * @param namespace the capability's namespace
     * @return collection of petals providing the specified capability
     * @throws NoEntityFoundException
     */
    @Override
    public final Collection<Petal> getPetalsForCapability(
            String name,
            String version,
            String namespace) throws NoEntityFoundException {

        try {
            return  capabilitySession.collectPetals(name, version, namespace);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to modify description of a petal.
     *
     * @param vendorName name of the vendor which provide the petal
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param newDesc new description for petal
     * @return updated petal
     * @throws NoEntityFoundException
     */
    public Petal updateDescription(
            String vendorName,
            String artifactId,
            String version,
            String newDesc) throws NoEntityFoundException {

        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);

        return petalSession.updateDescription(petal, newDesc);
    }

    /**
     * Method to set ISessionCapability instance to use.
     *
     * @param session the ISessionCapability to set
     */
    @Bind
    public final void bindCapabilitySession(ISessionCapability session) {
        this.capabilitySession = session;
    }

    /**
     * Method to set ISessionCategory instance to use.
     *
     * @param session the ISessionCategory to set
     */
    @Bind
    public final void bindCategorySession(ISessionCategory session) {
        this.categorySession = session;
    }

    /**
     * Method to set ISessionPetal instance to use.
     *
     * @param session the ISessionPetal to set
     */
    @Bind
    public final void bindPetalSession(ISessionPetal session) {
        this.petalSession = session;
    }

    /**
     * Method to set ISessionRequirement instance to use.
     *
     * @param session the ISessionRequirement to set
     */
    @Bind
    public final void bindRequirementSession(ISessionRequirement session) {
        this.requirementSession = session;
    }

    /**
     * Method to set ISessionVendor instance to use.
     *
     * @param session the ISessionVendor to set
     */
    @Bind
    public final void bindVendorSession(ISessionVendor session) {
        this.vendorSession = session;
    }

    /**
     * Method to set IPetalsPersistence instance to use.
     *
     * @param persistence the IPetalsPersistence to set
     */
    @Bind
    public final void bindPetalPersistence(IPetalsPersistence persistence) {
        this.petalPersistence = persistence;
    }

    /**
     * Method to set ISessionGroup instance to use.
     *
     * @param session the ISessionGroup to set
     */
    @Bind
    public final void bindGroupSession(ISessionGroup session) {
        this.groupSession = session;
    }

}