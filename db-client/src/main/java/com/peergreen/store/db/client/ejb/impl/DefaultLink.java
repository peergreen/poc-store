package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.session.api.ISessionLink;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;

/**
 * Class defining an entity session to manage the entity Link:
 * <ul>
 *      <li>Create a link on database</li>
 *      <li>Remove a link from the database</li>
 *      <li>Find a link from the database</li>
 *      <li>Collect all existing links on database</li>
 *      <li>Modify existing link changing his description</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultLink implements ISessionLink {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
    @Override
    public Link addLink(String url, String description) throws EntityAlreadyExistsException {
        Link link = findLink(url);
        if (link != null) {
            throw new EntityAlreadyExistsException("Link with URL: " + url + " already exists in database.");
        } else {
            link = new Link(url, description);
            entityManager.persist(link);
            return link;
        }
    }

    /**
     * Method to delete an instance of link.<br />
     * 
     * @param linkUrl url of the link to delete
     */
    @Override
    public void deleteLink(String linkUrl) {
        Link temp = findLink(linkUrl);
        if (temp != null) {
            entityManager.remove(temp);
        }
    }

    /**
     * Method to find a link with thanks to its URL.
     *  
     * @param linkUrl url of the link to find
     * @return Link instance found, or {@link null} if no instance found
     */
    @Override
    public Link findLink(String linkUrl) {
        Query q = entityManager.createNamedQuery("LinkByUrl");
        q.setParameter("url", linkUrl);

        Link result;
        try {
            result = (Link)q.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }

        return result;
    }

    /**
     * Method to collect all existing links in database.
     * 
     * @return links list
     */
    @Override
    public Collection<Link> collectLinks() {
        Query links = entityManager.createNamedQuery("Link.findAll");
        @SuppressWarnings("unchecked")
        List<Link> usersList = links.getResultList();
        Set<Link> linkSet = new HashSet<Link>();
        linkSet.addAll(usersList);
        return linkSet;
    }

    /**
     * Method to modify the description of a Link
     * 
     * @param link the link to modify
     * @param newDescription the new link description
     * @return modified Link instance (updated description)
     */
    @Override
    public Link updateDescription(Link link, String newDescription) {
        // retrieve attached link
        Link l = findLink(link.getUrl());
        
        l.setDescription(newDescription);
        return entityManager.merge(l);
    }
}


