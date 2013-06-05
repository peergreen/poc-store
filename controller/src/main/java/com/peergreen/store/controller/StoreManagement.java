package com.peergreen.store.controller;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;

/**
 * Class defining high level operations to manage server.
 * <p>
 * Provided functionalities:
 * <ul>
 *      <li>Add, remove and retrieve links to remote stores</li>
 *      <li>Retrieve available petals for a specific user,
 *          from local store or from staging store</li>
 *      <li>Retrieve all users</li>
 *      <li>Retrieve all groups</li>
 *      <li>Petal submission and validation</li>
 * </ul>
 */
public class StoreManagement implements IStoreManagment {

    /**
     * Method to a link between a remote store and the current one.
     * 
     * @param url path to the remote store
     * @param description link's description
     */
    @Override
    public void addLink(String url, String description) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to remove a link between a remote store and the current one.
     * 
     * @param linkId link's id
     */
    @Override
    public void removeLink(int linkId) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to collect all existing links in database.
     * 
     * @return list of all existing links in database
     */
    @Override
    public Collection<Link> collectLinks() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect available petals.<br />
     * Browse all links and staging.
     * 
     * @return list of available petals
     */
    @Override
    public Collection<Petal> collectPetals() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect available petals.<br />
     * Retrieve available petals only for a specific user.
     * 
     * @return list of available petals for a specific user
     */
    @Override
    public Collection<Petal> collectPetalsForUser(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect petals in the staging repository.
     * 
     * @return list of available petals in staging repository
     */
    @Override
    public Collection<Petal> collectPetalsFromStaging() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect petals in the local repository.
     * 
     * @return list of available petals in local repository
     */
    @Override
    public Collection<Petal> collectPetalsFromLocal() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all existing users on database.
     * 
     * @return list of all database's users
     */
    @Override
    public Collection<User> collectUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all existing groups on database.
     * 
     * @return list of all database's groups
     */
    @Override
    public Collection<Group> collectGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to submit a petal for an add in the store.<br />
     * Submitted petals needs to be validated to effectively added to the store.
     * 
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's exported capabilities
     * @param petalBinary petal's binary file
     */
    @Override
    public void submitPetal(String groupId, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities, File petalBinary) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * @param groupId petal's groupId
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    @Override
    public void validatePetal(String groupId, String artifactId, String version) {
        // TODO Auto-generated method stub

    }

}
