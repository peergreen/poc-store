package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining an entity session to manage the entity Link:
 * <ul>
 *      <li>Create a link on database</li>
 *      <li>Remove a link from the database</li>
 *      <li>Find a link from the database</li>
 *      <li>Collect all existing links on database</li>
 * </ul>
 * 
 */
public interface ISessionLink {

    /**
     * Method to add a new instance of Link in the database.
     * Throws {@link EntityAlreadyExistsException}
     *  if a link with same URL already exists in the database.
     *  
     * @param url link's url
     * @param description link's description
     * @return created Link instance
     * @throws EntityAlreadyExistsException
     */
    Link addLink(String url, String description) throws EntityAlreadyExistsException;

    /**
     * Method to delete an instance of link.<br />
     * 
     * @param linkUrl url of the link to delete
     * @return Link instance deleted or <code>null</code> if the link doesn't
     * exist.
     */
    Link deleteLink(String linkUrl);

    /**
     * Method to find a link with thanks to its URL.
     *  
     * @param linkUrl url of the link to find
     * @return Link instance found, or {@link null} if no instance found
     */
    Link findLink(String linkUrl);

    /**
     * Method to find a link with thanks to its id.
     * 
     * @param id link's id
     * @return Link instance found, or {@link null} if no instance found
     */
    Link findLinkById(int id);

    /**
     * Method to collect all existing links in database.
     * 
     * @return links list
     */
    Collection<Link> collectLinks();

    /**
     * Method to modify the description of a Link
     * 
     * @param link the link to modify
     * @param newDescription the new link description
     * @return modified Link instance (updated description)
     * @throws NoEntityFoundException if the link doesn't exist
     */
    Link updateDescription(Link link, String newDescription) throws NoEntityFoundException;

}