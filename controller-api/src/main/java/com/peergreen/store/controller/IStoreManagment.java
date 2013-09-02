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
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining high level operations to manage server.
 * <p>
 * Provided functionalities:
 * <ul>
 *      <li>Add, remove and retrieve links to remote stores</li>
 *      <li>Add, remove and retrieve categories</li>
 *      <li>Retrieve available petals for a specific user,
 *          from local store, from staging store or from remote stores</li>
 *      <li>Retrieve all users</li>
 *      <li>Retrieve all groups</li>
 *      <li>Petal submission and validation</li>
 *      <li>Get a petal from local, staging or remote store</li>
 * </ul>
 */
public interface IStoreManagment {

    /**
     * Method to add a link between a remote store and the current one.
     * 
     * @param url path to the remote store
     * @param description link's description
     * @return created link instance
     * @throws EntityAlreadyExistsException 
     */
    Link addLink(String url, String description) throws EntityAlreadyExistsException;

    /**
     * Method to remove a link between a remote store and the current one.
     * 
     * @param linkId link's url
     * @return Link instance deleted or {@link null} if the link can't be deleted
     */
    Link removeLink(String linkUrl);

    /**
     * Method to collect all existing links in database.
     * 
     * @return list of all existing links in database
     */
    Collection<Link> collectLinks();

    /**
     * Method to add a new category to the database.
     * 
     * @param name of the category
     * @return created Category instance
     * @throws EntityAlreadyExistsException
     */
    Category createCategory(String name) throws EntityAlreadyExistsException;

    /**
     * Method to remove a category from the database.
     * 
     * @param ame of the category to remove
     * @return Category instance deleted or {@link null} if the category can't be deleted
     */
    Category removeCategory(String name);

    /**
     * Method to collect all existing categories in database.
     * 
     * @return list of all existing categories in database
     */
    Collection<Category> collectCategories();

    /**
     * Method to collect available petals.<br />
     * Browse all links and staging.
     * 
     * @return list of available petals
     */
    Collection<Petal> collectPetals();

    /**
     * Method to collect petals in the local repository.
     * 
     * @return list of available petals in local repository
     */
    Collection<Petal> collectPetalsFromLocal();

    /**
     * Method to collect petals in the staging repository.
     * 
     * @return list of available petals in staging repository
     */
    Collection<Petal> collectPetalsFromStaging();

    /**
     * Method to collect petals in all associated remote repositories.
     * 
     * @return list of available petals in associated remote repositories
     */
    Collection<Petal> collectPetalsFromRemote();

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
     * @param vendorName the name of the petal's vendor 
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @param description petal's description
     * @param categoryName petal's category name
     * @param requirements petal's requirements
     * @param capabilities petal's exported capabilities
     * @param petalBinary petal's binary file
     * @return corresponding petal on database
     * @throws NoEntityFoundException
     * @throws EntityAlreadyExistsException
     */
    Petal submitPetal(String vendorName, String artifactId,
            String version, String description, String categoryName,
            Set<Requirement> requirements, Set<Capability> capabilities,
            File petalBinary) throws EntityAlreadyExistsException, NoEntityFoundException;

    /**
     * Method to validate a petal's submission thanks to its information.<br />
     * This method make the petal persistent in the store.
     * 
     * @param vendorName the name of the petal's vendor 
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return corresponding petal on database
     * @throws NoEntityFoundException 
     */
    Petal validatePetal(String vendorName, String artifactId, String version) throws NoEntityFoundException;


    /**
     * Method to get a petal (binary) from the local store.<br />
     * Return null if no petal found on the repository.
     * 
     * @param vendorName vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return binary of the petal
     */
    File getPetalFromLocal(String vendorName, String artifactId, String version);

    Capability getCapability(String name, String version);

    Requirement getRequirement(String name);

    /**
     * Method to get an instance of a vendor using his name 
     * @param name the name of the vendor to retrieve 
     * @return the specified vendor
     */
    Vendor getVendor(String name);

    /**
     * Method to update the description of a vendor
     * @param name the name of the vendor 
     * @param description the new description of the vendor 
     * @return The vendor updated if it exists , 
     * else throws {@link NoEntityFoundException}
     * @throws NoEntityFoundException
     */
    Vendor updateVendor(String name, String description) 
            throws NoEntityFoundException;

    /**
     * Method to retrieve all the petals provided by a vendor
     * @param name The name of the vendor 
     * @return A collection of petals provided by the vendor, 
     * or throws {@link NoEntityFoundException} if the vendor doesn't exist
     * @throws NoEntityFoundException
     */
    Collection<Petal> collectPetalsByVendor(String name)
            throws NoEntityFoundException; 
}