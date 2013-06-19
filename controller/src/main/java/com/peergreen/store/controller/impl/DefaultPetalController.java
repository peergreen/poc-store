package com.peergreen.store.controller.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;

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
     * Default constructor. Initialize attributes.
     */
    public DefaultPetalController() {
//        petalPersistence = new DefaultPetalsPersistence();
//        resolver = new DefaultResolveContext(resources, wirings, mandatoryResources, null);
    }

    /**
     * Method to retrieve metadata related to a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal related metadata
     */
    @Override
    public Map<String, Object> getPetalMetadata(Vendor vendor, String artifactId, String version) {
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
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param map indexing all petals providing each required capability
     * @param requirements that can't be satisfied
     * @return list of all petals available for each required capability
     */
    @Override
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
    
    
    
    /**
     * Method to retrieve a petal from the local store.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or <em>null</em> if not available
     */
    @Override
    public File getPetal(Vendor vendor, String artifactId, String version) {
        return petalPersistence.getPetalFromLocal(vendor, artifactId, version);
    }

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
     * @param petalBinary petal's binary
     * @param Origin the petal's origin 
     */
    @Override
    public Petal addPetal(Vendor vendor, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities,Origin origin, File petalBinary) {
        petalPersistence.addToLocal(vendor, artifactId, version, petalBinary);
        return petalSession.addPetal(vendor, artifactId, version, description, category, capabilities, requirements,origin);
    }

    /**
     * Method to remove a petal from the store thanks to its information.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    @Override
    public void removePetal(Vendor vendor, String artifactId, String version) {
        /* 
         * Can't remove petal from Maven repository.
         * So remove all group's permission on it
         * except admin group.
         * 
         */
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
    @Override
    public Petal updatePetal(Vendor vendor, String artifactId, String version, String description,
            Category category, Set<Requirement> requirements, Set<Capability> capabilities,
            File petalBinary) {
        return null;
    }

    /**
     * Method to add a new Capability to the database.
     * 
     * @param capabilityName capability's name
     * @param version capability's version
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     */
    @Override
    public Capability createCapability(String capabilityName,String version, String namespace, Map<String,String> properties) {
        return capabilitySession.addCapability(capabilityName,version, namespace, properties);
    }

    /**
     * Method to collect all the capabilities provided by a petal.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return list of all the capabilities provided by a petal
     */
    @Override
    public List<Capability> collectCapabilities(Vendor vendor, String artifactId, String version) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return (List<Capability>) petalSession.collectCapabilities(petal);
    }

    /**
     * Method to add a capability to a petal's provided capabilities list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to add to the provided capabilites list of the petal
     * @return updated petal
     */
    @Override
    public Petal addCapability(Vendor vendor, String artifactId, String version, Capability capability) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.addCapability(petal, capability);
    }

    /**
     * Method to remove a capability from a petal's provided capabilities list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param capability capability to remove from the provided capabilites  list of the petal
     * @return updated petal
     */
    @Override
    public Petal removeCapability(Vendor vendor, String artifactId, String version, Capability capability) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.removeCapability(petal, capability);
    }

    /**
     * Method to add a new Requirement to the database.
     * 
     * @param requirementName requirement's name
     * @param filter requirement's filter
     */
    @Override
    public Requirement createRequirement(String requirementName, String namespace, String filter) {
        return requirementSession.addRequirement(requirementName,namespace,filter);
    }

    /**
     * Method to collect all the petal's requirements.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    @Override
    public List<Requirement> collectRequirements(Vendor vendor, String artifactId, String version) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return (List<Requirement>) petalSession.collectRequirements(petal);
    }

    /**
     * Method to add a requirement to a petal's requirements list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to add to the petal's requirements list
     * @return updated petal
     */
    @Override
    public Petal addRequirement(Vendor vendor, String artifactId, String version, Requirement requirement) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.addRequirement(petal, requirement);
    }

    /**
     * Method to remove a requirement from a petal's requirements list.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param requirement requirement to remove from the petal's requirements
     * @return updated petal
     */
    @Override
    public Petal removeRequirement(Vendor vendor, String artifactId, String version, Requirement requirement) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.removeRequirement(petal, requirement);
    }

    /**
     * Method to add a new category to the database.
     * 
     * @param categoryName cetegory's name
     */
    @Override
    public Category createCategory(String categoryName) {
        return categorySession.addCategory(categoryName);
    }

    /**
     * Method to retrieve a petal's category.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return petal's category
     */
    @Override
    public Category getCategory(Vendor vendor, String artifactId, String version) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.getCategory(petal);
    }

    /**
     * Method to set the petal's category.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param category petal's category
     * @return updated category
     */
    @Override
    public Petal setCategory(Vendor vendor, String artifactId, String version, Category category) {
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        return petalSession.addCategory(petal, category);
    }

    /**
     * Method to create a new vendor on database.
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     */
    @Override
    public Vendor createVendor(String vendorName, String vendorDescription) {
        return vendorSession.addVendor(vendorName, vendorDescription);
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
