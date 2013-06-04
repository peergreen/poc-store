package com.peergreen.store.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;

@Component
@Instantiate
@Provides
public class PetalController implements IPetalController {

    private ISessionCapability capabilitySession;
    private ISessionCategory categorySession;
    private ISessionPetal petalSession;
    private ISessionRequirement requirementSession;
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
    public Map<String, String> getPetalMetadata(IVendor vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
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
    public IPetal getPetal(IVendor vendor, String artifactId, String version) {
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
    public void addPetal(IVendor vendor, String artifactId, String version, String description, ICategory category,
            Set<IRequirement> requirements, Set<ICapability> capabilities, File petalBinary) {
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
    public void removePetal(IVendor vendor, String artifactId, String version) {
        /* 
         * Can't remove petal from Maven repository.
         * So remove all group's permission on it
         * except admin group.
         * 
         */
//        petalSession.
        // TODO
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
    public IPetal updatePetal(IVendor vendor, String artifactId, String version, String description,
            ICategory category, Set<IRequirement> requirements, Set<ICapability> capabilities,
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
    public List<ICapability> collectCapabilities(IVendor vendor, String artifactId, String version) {
        // TODO
//        return petalSession.;
        return null;
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
    public IPetal addCapability(IVendor vendor, String artifactId, String version, ICapability capability) {
        // TODO Auto-generated method stub
        return null;
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
    public IPetal removeCapability(IVendor vendor, String artifactId, String version, ICapability capability) {
        return petalSession.removeCapability(capability);
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
    public List<IRequirement> collectRequirements(IVendor vendor, String artifactId, String version) {
        return (List<IRequirement>) petalSession.collectRequirements(vendor, artifactId, version);
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
    public IPetal addRequirement(IVendor vendor, String artifactId, String version, IRequirement requirement) {
        // TODO Auto-generated method stub
        return null;
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
    public IPetal removeRequirement(IVendor vendor, String artifactId, String version, IRequirement requirement) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to add a new category to the database.
     * 
     * @param categoryName cetegory's name
     */
    @Override
    public void createCategory(String categoryName) {
        // TODO Auto-generated method stub

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
    public ICategory getCategory(IVendor vendor, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
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
    public IPetal setCategory(IVendor vendor, String artifactId, String version, ICategory category) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to create a new vendor on database.
     * 
     * @param vendorName vendor's name
     * @param vendorDescription vendor's description
     */
    @Override
    public void createVendor(String vendorName, String vendorDescription) {
        // TODO Auto-generated method stub

    }

}
