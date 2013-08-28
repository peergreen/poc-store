package com.peergreen.store.controller.impl;


import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 *      <li>Add, remove and retrieve categories</li>
 *      <li>Retrieve available petals for a specific user,
 *          from local store, from staging store or from remote stores</li>
 *      <li>Retrieve all users</li>
 *      <li>Retrieve all groups</li>
 *      <li>Petal submission and validation</li>
 *      <li>Get a petal from local, staging or remote store</li>
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
	private static Logger theLogger = Logger.getLogger(DefaultStoreManagement.class.getName());

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
			theLogger.log(Level.SEVERE, e.getMessage());
			throw new EntityAlreadyExistsException(e);
		}
		return link;
	}

	/**
	 * Method to remove a link between a remote store and the current one.
	 * 
	 * @param linkUrl link's url
	 * @return Link instance deleted or {@link null} if the link can't be deleted
	 */
	@Override
	public Link removeLink(String linkUrl) {
		return linkSession.deleteLink(linkUrl);
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
	public Category createCategory(String name) throws EntityAlreadyExistsException {
		Category category = null;
		try {
			category = categorySession.addCategory(name);
		} catch(EntityAlreadyExistsException e) {
			theLogger.log(Level.SEVERE, e.getMessage());
			throw new EntityAlreadyExistsException(e);
		}
		return category;
	}

	/**
	 * Method to remove a category from the database.
	 * 
	 * @param name of the category to remove
	 * @return Category instance deleted or {@link null} if the Category can't be deleted
	 */
	public Category removeCategory(String name) {
		return  categorySession.deleteCategory(name);
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
	 * @throws EntityAlreadyExistsException, NoEntityFoundException 
	 */
	@Override
	public Petal submitPetal(String vendorName, String artifactId, String version, String description, Category category,
			Set<Requirement> requirements, Set<Capability> capabilities, File petalBinary) throws EntityAlreadyExistsException, NoEntityFoundException {
		Vendor vendor = vendorSession.findVendor(vendorName);

		petalsPersistence.addToStaging(vendorName, artifactId, version, petalBinary);

		try {
			petalSession.addPetal(vendor, artifactId, version, description,
					category, capabilities, requirements, Origin.STAGING);
			return petalSession.findPetal(vendor, artifactId, version);

		} catch(EntityAlreadyExistsException e) {
			theLogger.log(Level.SEVERE, e.getMessage());
			throw new EntityAlreadyExistsException(e);
		} catch (NoEntityFoundException e) {
			theLogger.log(Level.SEVERE, e.getMessage());
			throw new NoEntityFoundException(e);
		}

	}

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
	@Override
	public Petal validatePetal(String vendorName, String artifactId, String version) throws NoEntityFoundException {
		Vendor vendor = vendorSession.findVendor(vendorName);
		// retrieve petal from staging repository
		File binary = petalsPersistence.getPetalFromStaging(vendorName, artifactId, version);
		// add this petal in local repository
		petalsPersistence.addToLocal(vendorName, artifactId, version, binary);
		// change origin attribute to LOCAL
		Petal petal = petalSession.findPetal(vendor, artifactId, version);
		try {
			petalSession.updateOrigin(petal, Origin.LOCAL);
		} catch (NoEntityFoundException e) {
			theLogger.log(Level.SEVERE, e.getMessage());
			throw new NoEntityFoundException(e);
		}

		return petal;
	}

	/**
	 * Method to get a petal (binary) from the local store.<br />
	 * Return null if no petal found on the repository.
	 * 
	 * @param vendorName vendor name
	 * @param artifactId petal's artifactId
	 * @param version petal's version
	 * @return binary of the petal
	 */
	@Override
	public File getPetalFromLocal(String vendorName, String artifactId,
			String version) {
		return petalsPersistence.getPetalFromLocal(vendorName, artifactId, version);
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
