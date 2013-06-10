package com.peergreen.store.controller;


import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Bind;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionLink;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.enumeration.Origin;

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
public class DefaultStoreManagement implements IStoreManagment {

    private IPetalsPersistence petalsPersistence;
    private ISessionGroup groupSession;
    private ISessionLink linkSession;
    private ISessionPetal petalSession;
    private ISessionUser userSession;

    /**
     * Method to a link between a remote store and the current one.
     * 
     * @param url path to the remote store
     * @param description link's description
     */
    @Override
    public void addLink(String url, String description) {
        linkSession.addLink(url, description);
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
        Collection<Group> groups = userSession.collectGroups(pseudo);
        Iterator<Group> it = groups.iterator();
        
        Collection<Petal> petals = null;
        
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
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's exported capabilities
     * @param petalBinary petal's binary file
     */
    @Override
    public void submitPetal(String vendor, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities, File petalBinary) {
        petalsPersistence.addToStaging(vendor, artifactId, version, petalBinary);
//        petalSession.addPetal(vendor, artifactId, version, description,
//                category, capabilities, requirements, Origin.STAGING);
    }

    /**
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * @param vendor petal's vendor
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    @Override
    public void validatePetal(String vendor, String artifactId, String version) {
        // TODO Auto-generated method stub

    }

    @Bind
    public void bindPetalsPersistence(IPetalsPersistence petalsPersistence) {
        this.petalsPersistence = petalsPersistence;
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

}
