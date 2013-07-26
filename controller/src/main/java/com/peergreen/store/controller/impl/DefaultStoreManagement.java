package com.peergreen.store.controller.impl;


import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.IStoreManagment;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionLink;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Class defining high level operations to manage server.
 * <p>
 * Provided functionalities:
 * <ul>
 *      <li>Add, remove and retrieve links to remote stores</li>
 *      <li>Retrieve available petals for a specific user,
 *          from local store, from staging store or from
 *          associated remote repositories</li>
 *      <li>Retrieve all users</li>
 *      <li>Retrieve all groups</li>
 *      <li>Petal submission and validation</li>
 * </ul>
 */
@Component
@Instantiate
@Provides
public class DefaultStoreManagement implements IStoreManagment {

    private IPetalsPersistence petalsPersistence;
    private ISessionCategory categorySession;
    private ISessionGroup groupSession;
    private ISessionLink linkSession;
    private ISessionPetal petalSession;
    private ISessionUser userSession;
    private ISessionVendor vendorSession;

    /**
     * Method to a link between a remote store and the current one.
     * 
     * @param url path to the remote store
     * @param description link's description
     * @param created link instance
     * @throws EntityAlreadyExistsException
     */
    @Override
    public Link addLink(String url, String description) throws EntityAlreadyExistsException {
        Link link = null;
        try {
            link = linkSession.addLink(url, description);
        } catch(EntityAlreadyExistsException e) {
            throw e;
        }
        return link;
    }

    /**
     * Method to remove a link between a remote store and the current one.
     * 
     * @param linkId link's id
     */
    @Override
    public void removeLink(String linkUrl) {
        linkSession.deleteLink(linkUrl);
    }

    /**
     * Method to collect all existing links in database.
     * 
     * @return list of all existing links in database
     */
    @Override
    public Collection<Link> collectLinks() {
        return linkSession.collectLinks();
    }

    /**
     * Method to add a new category to the database.
     * 
     * @param name of the category
     * @return created Category instance
     * @throws EntityAlreadyExistsException
     */
    public Category addCategory(String name) throws EntityAlreadyExistsException {
        Category category = null;
        try {
            category = categorySession.addCategory(name);
        } catch(EntityAlreadyExistsException e) {
            throw e;
        }
        return category;
    }

    /**
     * Method to remove a category from the database.
     * 
     * @param ame of the category to remove
     */
    public void removeCategory(String name) {
        try{
            categorySession.deleteCategory(name);
        }catch(IllegalArgumentException e){

        }
    }

    /**
     * Method to collect all existing categories in database.
     * 
     * @return list of all existing categories in database
     */
    public Collection<Category> collectCategories() {
        return categorySession.collectCategories();
    }

    /**
     * Method to collect available petals.<br />
     * Browse all links and staging.
     * 
     * @return list of available petals
     */
    @Override
    public Collection<Petal> collectPetals() {
        return petalSession.collectPetals();
    }

    /**
     * Method to collect available petals.<br />
     * Retrieve available petals only for a specific user.
     * 
     * @return list of available petals for a specific user
     */
    @Override
    public Collection<Petal> collectPetalsForUser(String pseudo) {
        Collection<Group> groups = null;
        Collection<Petal> petals = null;

        try {
            groups = userSession.collectGroups(pseudo);
            Iterator<Group> it = groups.iterator();
            // retrieve all petals for each group
            while (it.hasNext()) {
                Group g = it.next();
                Collection<Petal> p = groupSession.collectPetals(g.getGroupname());
                if (petals != null) {
                    petals.addAll(p);
                } else {
                    petals = p;
                }
            }
        } catch (NoEntityFoundException e) {
            System.err.println(e.getMessage());
        }

        return petals;
    }

    /**
     * Method to collect petals in the local repository.
     * 
     * @return list of available petals in local repository
     */
    @Override
    public Collection<Petal> collectPetalsFromLocal() {
        return petalSession.collectPetalsFromLocal();
    }

    /**
     * Method to collect petals in the staging repository.
     * 
     * @return list of available petals in staging repository
     */
    @Override
    public Collection<Petal> collectPetalsFromStaging() {
        return petalSession.collectPetalsFromStaging();
    }

    /**
     * Method to collect petals in all associated remote repositories.
     * 
     * @return list of available petals in associated remote repositories
     */
    @Override
    public Collection<Petal> collectPetalsFromRemote() {
        return petalSession.collectPetalsFromRemote();
    }

    /**
     * Method to collect all existing users on database.
     * 
     * @return list of all database's users
     */
    @Override
    public Collection<User> collectUsers() {
        return userSession.collectUsers();
    }

    /**
     * Method to collect all existing groups on database.
     * 
     * @return list of all database's groups
     */
    @Override
    public Collection<Group> collectGroups() {
        return groupSession.collectGroups();
    }

    /**
     * Method to submit a petal for an add in the store.<br />
     * Submitted petals needs to be validated to effectively added to the store.
     * 
     * @param vendorName the name of the petal's vendor 
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's exported capabilities
     * @param petalBinary petal's binary file
     * @return corresponding petal on database
     */
    @Override
    public Petal submitPetal(String vendorName, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities, File petalBinary) {
        Vendor vendor = vendorSession.findVendor(vendorName);

        petalsPersistence.addToStaging(vendor, artifactId, version, petalBinary);

        try {
            petalSession.addPetal(vendor, artifactId, version, description,
                    category, capabilities, requirements, Origin.STAGING);
        } catch(EntityAlreadyExistsException e) {
            // TODO
        } catch (NoEntityFoundException e) {
            // TODO
        }

        return petalSession.findPetal(vendor, artifactId, version);
    }

    /**
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * 
     * @param vendorName the name of the petal's vendor 
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal on database
     */
    @Override
    public Petal validatePetal(String vendorName, String artifactId, String version) {
        Vendor vendor = vendorSession.findVendor(vendorName);
        // retrieve petal from staging repository
        File binary = petalsPersistence.getPetalFromStaging(vendor, artifactId, version);
        // add this petal in local repository
        petalsPersistence.addToLocal(vendor, artifactId, version, binary);
        // change origin attribute to LOCAL
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        petalSession.updateOrigin(petal, Origin.LOCAL);

        return petal;
    }

    @Bind
    public void bindPetalsPersistence(IPetalsPersistence petalsPersistence) {
        this.petalsPersistence = petalsPersistence;
    }

    @Bind
    public void bindCategorySession(ISessionCategory categorySession) {
        this.categorySession = categorySession;
    }

    @Bind
    public void bindGroupSession(ISessionGroup groupSession) {
        this.groupSession = groupSession;
    }

    @Bind
    public void bindLinkSession(ISessionLink linkSession) {
        this.linkSession = linkSession;
    }

    @Bind
    public void bindPetalSession(ISessionPetal petalSession) {
        this.petalSession = petalSession;
    }

    @Bind
    public void bindUserSession(ISessionUser userSession) {
        this.userSession = userSession;
    }
    
    @Bind
    public void bindVendorSession(ISessionVendor vendorSession) {
        this.vendorSession = vendorSession;
    }


}
