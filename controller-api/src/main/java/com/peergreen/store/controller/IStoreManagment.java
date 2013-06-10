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
 * Interface defining high level operations to manage server.
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
public interface IStoreManagment {

    /**
     * Method to a link between a remote store and the current one.
     * 
     * @param url path to the remote store
     * @param description link's description
     */
    void addLink(String url, String description);

    /**
     * Method to remove a link between a remote store and the current one.
     * 
     * @param linkId link's id
     */
    void removeLink(int linkId);

    /**
     * Method to collect all existing links in database.
     * 
     * @return list of all existing links in database
     */
    Collection<Link> collectLinks();

    /**
     * Method to collect available petals.<br />
     * Browse all links and staging.
     * 
     * @return list of available petals
     */
    Collection<Petal> collectPetals();

    /**
     * Method to collect available petals.<br />
     * Retrieve available petals only for a specific user.
     * 
     * @return list of available petals for a specific user
     */
    Collection<Petal> collectPetalsForUser(String pseudo);

    /**
     * Method to collect petals in the staging repository.
     * 
     * @return list of available petals in staging repository
     */
    Collection<Petal> collectPetalsFromStaging();

    /**
     * Method to collect petals in the local repository.
     * 
     * @return list of available petals in local repository
     */
    Collection<Petal> collectPetalsFromLocal();

    /**
     * Method to collect all existing users on database.
     * 
     * @return list of all database's users
     */
    Collection<User> collectUsers();

    /**
     * Method to collect all existing groups on database.
     * 
     * @return list of all database's groups
     */
    Collection<Group> collectGroups();

    /**
     * Method to submit a petal for an add in the store.<br />
     * Submitted petals needs to be validated to effectively added to the store.
     * 
     * @param vendorName petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param category petal's category
     * @param requirements petal's requirements
     * @param capabilities petal's exported capabilities
     * @param petalBinary petal's binary file
     */
    void submitPetal(String vendorName, String artifactId,
            String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities,
            File petalBinary);

    /**
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * @param vendorName petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     */
    void validatePetal(String vendorName, String artifactId, String version);

}
