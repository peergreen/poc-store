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
    Link addLink(String url, String description) 
            throws EntityAlreadyExistsException;

    /**
     * Method to retrieve a link using his id.
     *
     * @param id link's id
     * @return corresponding link or <em>null</em> if not available
     */
    Link getLink(int id);

    /**
     * Method to remove a link between a remote store and the current one.
     *
     * @param linkId link's url
     * @return Link instance deleted or
     * {@literal null} if the link can't be deleted
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
     * Method to retrieve a category thanks to its name.
     *
     * @param name category name
     * @return specified category,
     * or {@literal null} if no corresponding category
     */
    Category getCategory(String name);

    /**
     * Method to remove a category from the database.
     *
     * @param ame of the category to remove
     * @return Category instance deleted or
     * {@literal null} if the category can't be deleted
     */
    Category removeCategory(String name);

    /**
     * Method to collect all existing categories in database.
     *
     * @return list of all existing categories in database
     */
    Collection<Category> collectCategories();
    
    /**
     * Method to collect all petals associated to a specified category.
     *
     * @param name category name
     * @return collection of associated petals
     * @throws NoEntityFoundException 
     */
    Collection<Petal> getPetalsForCategory(String name)
            throws NoEntityFoundException;

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
            File petalBinary)
                    throws EntityAlreadyExistsException, NoEntityFoundException;

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
    Petal validatePetal(String vendorName, String artifactId, String version)
            throws NoEntityFoundException;

    /**
     * Method to retrieve all the petals provided by a vendor.<br />
     * Throws {@link NoEntityFoundException} if the vendor does not exists.
     *
     * @param name vendor name 
     * @return collection of petals provided by the vendor
     * @throws NoEntityFoundException
     */
    Collection<Petal> collectPetalsByVendor(String name)
            throws NoEntityFoundException;

    /**
     * Method to get a petal (binary) from the local store.<br />
     * Return null if no petal found on the repository.
     *
     * @param vendorName vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return binary of the petal
     */
    File getPetalFromLocal(
            String vendorName,
            String artifactId,
            String version);

    /**
     * Method to get a petal (binary) from the staging store.<br />
     * Return null if no petal found on the repository.
     *
     * @param vendorName vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return binary of the petal
     */
    File getPetalFromStaging(
            String vendorName,
            String artifactId,
            String version);

    /**
     * Method to get a petal (binary) from the remote store(s).<br />
     * Return null if no petal found on the repository.<br />
     * Throws {@link NoEntityFoundException} when specified vendor
     * does not exist in database.
     *
     * @param vendorName vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return binary of the petal
     * @throws NoEntityFoundException 
     */
    File getPetalFromRemote(
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException;

}
