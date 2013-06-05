package com.peergreen.store.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.aether.client.IPetalsPersistence;
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

@Component
@Instantiate
@Provides
public class PetalController implements IPetalController {

    private ISessionCapability capabilitySession;
    private ISessionCategory categorySession;
    private ISessionPetal petalSession;
    private ISessionRequirement requirementSession;
    private ISessionVendor vendorSession;
    /** reference to the aether client for petal persistence */
    private IPetalsPersistence petalPersistence;

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
     * Method to retrieve a petal from the local store.
     * 
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal or <em>null</em> if not available
     */
    @Override
    public Petal getPetal(Vendor vendor, String artifactId, String version) {
        return petalSession.findPetal(vendor, artifactId, version);
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
     */
    @Override
    public void addPetal(Vendor vendor, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities, File petalBinary) {
        petalSession.addPetal(vendor, artifactId, version, description, category, capabilities, requirements);
        petalPersistence.addToLocal(vendor, artifactId, version, description, category, requirements, capabilities, petalBinary);
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
        List<Group> groups = (List<Group>) petalSession.collectGroups(petal);
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
     * @param namespace capability's related namespace
     * @param properties capability's properties (metadata)
     */
    @Override
    public void createCapability(String namespace, Map<String, String> properties) {
        capabilitySession.addCapability(namespace, properties);
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
     * @param namespace requirement's related namespace
     * @param properties requirement's properties (metadata)
     */
    @Override
    public void createRequirement(String namespace, Map<String, String> properties) {
        requirementSession.addRequirement(namespace, properties);
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
    public void createCategory(String categoryName) {
        categorySession.addCategory(categoryName);
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
    public void createVendor(String vendorName, String vendorDescription) {
        vendorSession.addVendor(vendorName, vendorDescription);
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
