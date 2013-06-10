package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Link;

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
     * Method to add a new instance of Link in the database
     * 
     * @param url the link's url
     * @param description the link's description
     * 
     * @return A new instance of link
     */
    Link addLink(String url, String description);

    /**
     * Method to delete an instance ok link with the url 'linkUrl'
     * 
     * @param linkUrl the url of the link to delete
     */
    void deleteLink(String linkUrl);

    /**
     * Method to find the link with the url 'linkUrl'
     * 
     * @param linkUrl the url of the link to find
     * 
     * @return The link with the url linkUrl
     */
    Link findLink(String linkUrl);
    
    /**
     * Method to collect all existing links on database.
     * 
     * @return links list
     */
    Collection<Link> collectLinks();

}