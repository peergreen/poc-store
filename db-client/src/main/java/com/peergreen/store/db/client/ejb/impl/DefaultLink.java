package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.session.api.ISessionLink;

/**
 * Class defining an entity session to manage the entity Link:
 * <ul>
 *      <li>Create a link on database</li>
 *      <li>Remove a link from the database</li>
 *      <li>Find a link from the database</li>
 *      <li>Collect all existing links on database</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultLink implements ISessionLink {

    @PersistenceContext
    private EntityManager entityManager = null;

    /**
     * Method to add a new instance of Link in the database
     * 
     * @param url the link's url
     * @param description the link's description
     * 
     * @return A new instance of link
     */
    @Override
    public Link addLink(String url, String description) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to delete an instance ok link with the url 'linkUrl'
     * 
     * @param linkUrl the url of the link to delete
     */
    @Override
    public void deleteLink(String linkUrl) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to find the link with the url 'linkUrl'
     * 
     * @param linkUrl the url of the link to find
     * 
     * @return The link with the url linkUrl
     */
    @Override
    public Link findLink(String linkUrl) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all existing links on database.
     * 
     * @return links list
     */
    @Override
    public Collection<Link> collectLinks() {
        // TODO Auto-generated method stub
        return null;
    }

}
