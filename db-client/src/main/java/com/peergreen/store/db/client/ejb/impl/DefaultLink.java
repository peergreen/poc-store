package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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


    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new instance of Link in the database
     * It throws an exception "EntityExistsException" 
     * when the entity already exist in the database
     *  
     * @param url the link's url
     * @param description the link's description
     * 
     * @return A new instance of link
     */
    @Override
    public Link addLink(String url, String description) {
        Link link = findLink(url);
        if(link != null){
            throw new EntityExistsException();
        }else{
            link = new Link(url, description);
            entityManager.persist(link);
            return link;
        }


    }

    /**
     * Method to delete an instance of link with the url 'linkUrl'
     * It throws an IllegalArgumentException if the entity to remove
     * doesn't exist in the database.
     * 
     * @param linkUrl the url of the link to delete
     */
    @Override
    public void deleteLink(String linkUrl) {

        Link temp = findLink(linkUrl);
        if(temp != null){
            entityManager.remove(temp);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to find the link with the url 'linkUrl'
     * It returns null if the link doesn't exist.
     *  
     * @param linkUrl the url of the link to find
     * 
     * @return The link with the url linkUrl
     */
    @Override
    public Link findLink(String linkUrl)throws NoResultException {
        Query q = entityManager.createNamedQuery("LinkByUrl");
        q.setParameter("url", linkUrl);
        Link result;
        try{
            result = (Link)q.getSingleResult();
        }catch(NoResultException e) {
            result = null;
        }
        return result;
    }

    /**
     * Method to collect all existing links on database.
     * 
     * @return links list
     */
    @Override
    public Collection<Link> collectLinks() {

        Query links = entityManager.createNamedQuery("Link.findAll");
        List<Link> usersList = links.getResultList();
        Set<Link> linkSet = new HashSet<Link>();
        linkSet.addAll(usersList);
        return linkSet;
    }

    @Override
    public Link updateDescription(Link oldLink, String newDescription) {

        oldLink.setDescription(newDescription);

        return entityManager.merge(oldLink);
    }
}


