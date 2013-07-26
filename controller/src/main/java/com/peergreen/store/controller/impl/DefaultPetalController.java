package com.peergreen.store.controller.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.resource.Resource;
import org.osgi.resource.Wiring;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.controller.util.DependencyRequest;
import com.peergreen.store.controller.util.DependencyResult;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all petal related operations:
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

    @Requires
    private ISessionCapability capabilitySession;
    @Requires
    private ISessionCategory categorySession;
    @Requires
    private ISessionPetal petalSession;
    @Requires
    private ISessionRequirement requirementSession;
    @Requires
    private ISessionVendor vendorSession;
    /** reference to the aether client for petal persistence */
    @Requires
    private IPetalsPersistence petalPersistence;
    /** resolver to get all petal"s transitive dependencies */
    //    private ResolveContext resolver;
    /** associated variables */
    Collection<Resource> resources;
    Map<Resource, Wiring> wirings;
    Collection<Resource> mandatoryResources;

    /**
     * Method to retrieve metadata related to a petal.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal related metadata
     */
    @Override
    public Map<String, Object> getPetalMetadata(String vendorName, String artifactId, String version) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("vendor", vendor);
        metadata.put("artifactId", artifactId);
        metadata.put("version", version);
        metadata.put("description", petal.getDescription());
        metadata.put("categorie", petalSession.getCategory(petal));
        metadata.put("requirements", petalSession.collectRequirements(petal));
        metadata.put("capabilities", petalSession.collectCapabilities(petal));

        return metadata;
    }

    /**
     * Method to retrieve all petals available for each required capability.
     * 
     * @param request DependencyRequest containing all constraints.
     * @return list of all petals available for each required capability
     * @see DependencyRequest
     */
    @Override
    public DependencyResult getTransitiveDependencies(DependencyRequest request) {
        // create a DependencyResult
        DependencyResult result = new DependencyResult();
        
        Petal petal = petalSession.findPetal(request.getVendor(), request.getArtifactId(), request.getVersion());
        Collection<Requirement> requirements = petal.getRequirements();
        
        for (Requirement req : requirements) {
            // retrieve capabilities which meet the requirements in a same namespace
            Collection<Capability> capabilities = requirementSession.findCapabilities(request.getNamspace(), req);
            
            // retrieve petals providing the capability
            for (Capability capability : capabilities) {
                Set<Petal> petals = capability.getPetals();
            
                if (petals.isEmpty()) {
                    // declare missing capability
                    result.addUnresolvedRequirement(req);
                } else {
                    // index petals providing the capability
                    result.addResolvedDependency(req, petals);
                }
            }
        }
        
        return result;
    }
    /*
    public Collection<PetalId> getTransitiveRequirements(
            Vendor vendor,
            String artifactId,
            String version,
            Map<Capability, Set<Petal>> resolvedCapabilities,
            Set<Requirement> unresolvedRequirements) {

        // find petal and its requirements
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Collection<Requirement> requirements = petal.getRequirements();

        // for each requirement, retrieve matching capabilities
        for (Requirement requirement : requirements) {
            // need: retrieve capabilities which meet the requirements in a same namespace
            //            Collection<Capability> capabilities = capabilitySession.findCapabilities("namespace", "filtre LDAP");

            Collection<Capability> capabilities = new HashSet<>();

            // retrieve petals providing the capability
            for (Capability capability : capabilities) {
                Collection<Petal> petals = capability.getPetals();
                HashSet<Petal> setPetals = new HashSet<>(petals);

                if (petals.isEmpty()) {
                    // declare missing capability
                    unresolvedRequirements.add(requirement);
                } else {
                    // index petals providing the capability
                    resolvedCapabilities.put(capability, setPetals);
                }
            }
        }

        return null;
    }
    */

    /**
     * Method to create a new vendor on database.
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     * @return created vendor instance
     * @throws EntityAlreadyExistsException 
     */
    public Vendor createVendor(String vendorName, String vendorDescription) throws EntityAlreadyExistsException {
        return vendorSession.addVendor(vendorName, vendorDescription);
    }

    /**
     * Method to collect all existing vendors in database.
     * 
     * @return list of all existing vendors in database
     */
    public Collection<Vendor> collectVendors() {
        return vendorSession.collectVendors();
    }

    /**
     * Method to retrieve a petal from the local store.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or <em>null</em> if not available
     */
    @Override
    public File getPetal(String vendorName, String artifactId, String version) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        return petalPersistence.getPetalFromLocal(vendor, artifactId, version);
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
     * @param Origin the petal's origin 
     * @throws EntityAlreadyExistsException 
     * @throws NoEntityFoundException 
     */
    @Override
    public Petal addPetal(String vendorName, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities,Origin origin, File petalBinary) throws NoEntityFoundException, EntityAlreadyExistsException {
        Vendor vendor = vendorSession.findVendor(vendorName);
        petalPersistence.addToLocal(vendor, artifactId, version, petalBinary);
        return petalSession.addPetal(vendor, artifactId, version, description, category, capabilities, requirements,origin); 
    }

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    @Override
    public void removePetal(String vendorName, String artifactId, String version) {
        /* 
         * Can't remove petal from Maven repository.
         * So remove all group's permission on it
         * except admin group.
         * 
         */
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        Collection<Group> groups = petalSession.collectGroups(petal);
        Iterator<Group> it = groups.iterator();

        while (it.hasNext()) {
            Group group = it.next();
            if (!group.getGroupname().equals("admin")) {
                petalSession.removeAccesToGroup(petal, group);
            }
        }
    }

    /**
     * Method to add a new Capability to the database.
     * 
     * @param capabilityName capability's name
     * @param version capability's version
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     * @throws EntityAlreadyExistsException
     */
    @Override
    public Capability createCapability(String capabilityName, String version,
            String namespace, Map<String,String> properties) throws EntityAlreadyExistsException {
        
        Capability capability = null;
        try {
            capability = capabilitySession.addCapability(capabilityName, version, namespace, properties);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        }
        return capability;
    }

    /**
     * Method to collect all the capabilities provided by a petal.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all the capabilities provided by a petal
     */
    @Override
    public List<Capability> collectCapabilities(String vendorName, String artifactId, String version) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return (List<Capability>) petalSession.collectCapabilities(petal);
    }

    /**
     * Method to add a capability to a petal's provided capabilities list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to add to the provided capabilites list of the petal
     * @return updated petal
     */
    @Override
    public Petal addCapability(String vendorName, String artifactId, String version, Capability capability) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.addCapability(petal, capability);
    }

    /**
     * Method to remove a capability from a petal's provided capabilities list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to remove from the provided capabilites  list of the petal
     * @return updated petal
     */
    @Override
    public Petal removeCapability(String vendorName, String artifactId, String version, Capability capability) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.removeCapability(petal, capability);
    }

    /**
     * Method to add a new Requirement to the database.
     * 
     * @param requirementName requirement's name
     * @param filter requirement's filter
     * @throws EntityAlreadyExistsException 
     */
    @Override
    public Requirement createRequirement(String requirementName, String namespace, String filter) throws EntityAlreadyExistsException {
        return requirementSession.addRequirement(requirementName, namespace, filter);
    }

    /**
     * Method to collect all the petal's requirements.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    @Override
    public List<Requirement> collectRequirements(String vendorName, String artifactId, String version) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return (List<Requirement>) petalSession.collectRequirements(petal);
    }

    /**
     * Method to add a requirement to a petal's requirements list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to add to the petal's requirements list
     * @return updated petal
     */
    @Override
    public Petal addRequirement(String vendorName, String artifactId, String version, Requirement requirement) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.addRequirement(petal, requirement);
    }

    /**
     * Method to remove a requirement from a petal's requirements list.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to remove from the petal's requirements
     * @return updated petal
     */
    @Override
    public Petal removeRequirement(String vendorName, String artifactId, String version, Requirement requirement) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.removeRequirement(petal, requirement);
    }

    /**
     * Method to retrieve a petal's category.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's category
     */
    @Override
    public Category getCategory(String vendorName, String artifactId, String version) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.getCategory(petal);
    }

    /**
     * Method to set the petal's category.
     * 
     * @param vendorName the name of the petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param category petal's category
     * @return updated category
     */
    @Override
    public Petal setCategory(String vendorName, String artifactId, String version, Category category) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.addCategory(petal, category);
    }

    @Bind
    public void bindCapabilitySession(ISessionCapability capabilitySession) {
        this.capabilitySession = capabilitySession;
    }

    @Bind
    public void bindCategorySession(ISessionCategory categorySession) {
        this.categorySession = categorySession;
    }

    @Bind
    public void bindPetalSession(ISessionPetal petalSession) {
        this.petalSession = petalSession;
    }

    @Bind
    public void bindRequirementSession(ISessionRequirement requirementSession) {
        this.requirementSession = requirementSession;
    }

    @Bind
    public void bindVendorSession(ISessionVendor vendorSession) {
        this.vendorSession = vendorSession;
    }

    @Bind
    public void bindPetalPersistence(IPetalsPersistence petalPersistence) {
        this.petalPersistence = petalPersistence;
    }
}