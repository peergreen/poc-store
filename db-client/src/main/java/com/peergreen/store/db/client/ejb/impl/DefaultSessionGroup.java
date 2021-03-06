package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


/**
 * Class defining an entity session to manage the entity Group.
 * <ul>
 *      <li>Create a group in a database</li>
 *      <li>Find a group from the database</li>
 *      <li>Modify a group</li>
 *      <li>Remove a group from the database</li>
 *      <li>Add a user to a group</li>
 *      <li>Remove a user from a group</li>
 *      <li>Collect all the users of a group</li>
 *      <li>Add a petal to those which are accessible from a group</li>
 *      <li>Remove an access to a petal from a group</li>
 *      <li>Collect all the petals which the group have access</li>
 *      <li>Collect all existing groups on database</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultSessionGroup implements ISessionGroup {

    private EntityManager entityManager;

    private ISessionPetal sessionPetal;

    private ISessionUser sessionUser;

    private static Log logger = LogFactory.getLog(DefaultSessionGroup.class);



    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Set entity session which manage the entity Petal
     * @param sessionPetal Instance of sessionPetal 
     */
    @EJB
    public void setSessionPetal(ISessionPetal sessionPetal) {
        this.sessionPetal = sessionPetal;
    }

    /**
     * Set entity session which manage the entity User
     * @param sessionUser Instance of sessionUser 
     */
    @EJB
    public void setSessionUser(ISessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }
    /**
     * <p>
     * Method to add a new group in the database.<br />
     * List of allowed petals and users is empty when creating the group
     * </p>
     * <p>
     * Throws {@link EntityAlreadyExistsException}
     *  when an entity with same id already exists in the database.<br />
     * </p>
     * 
     * @param groupName group name to create
     * @return group created
     * @throws EntityAlreadyExistsException
     */
    @Override
    public Group addGroup(String groupName) 
            throws EntityAlreadyExistsException {
        Group group = findGroup(groupName) ;

        if (group != null) {
            throw new EntityAlreadyExistsException("This group " +
                    "already exists on database.");
        } else {
            group = new Group(groupName);
            entityManager.persist(group);
            return group;
        }
    }


    /**
     * Method to find a group in the database.
     *  
     * @param groupName group's name
     * @return found group with the name 'groupName', {@literal null} other wise
     */
    @Override
    public Group findGroup(String groupName) {
        Query q = entityManager.createNamedQuery("GroupByName");
        q.setParameter("name", groupName);
        try {
            return (Group)q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Method to delete the group thanks to its name.<br />
     * 
     * @param groupName the name of the group to delete
     */
    @Override
    public Group deleteGroup(String groupName) {
        // retrieve attached group 
        Group group = findGroup(groupName);
        if(group != null){
            try {
                //Collect all the petals accessible via this group
                Collection<Petal> petals = group.getPetals();
                Iterator<Petal> it = petals.iterator();

                //Remove access to each petal for the group
                while(it.hasNext()) {
                    Petal p = it.next();
                    sessionPetal.removeAccesToGroup(p, group);     
                }

                //Collect all users of the group
                Collection<User> users = group.getUsers();
                Iterator<User> itU = users.iterator();

                //Remove each user from the group
                while(itU.hasNext()) {
                    User u = itU.next();
                    sessionUser.removeGroup(u, group);
                }

                //Then remove the group from the database 
                entityManager.remove(group);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }
            return group ; 
        }else{
            return group; 
        }
    }

    /**
     * Method to add a user to a group.
     * 
     * @param group group to which add the user
     * @param myUser user to add to the group 
     * @return modified Group instance
     * @throws NoEntityFoundException
     */
    @Override
    public Group addUser(Group group, User myUser)
            throws NoEntityFoundException {
        // retrieve attached group
        Group g = findGroup(group.getGroupname());
        if(g!=null){
            // retrieve attached user
            User u = sessionUser.findUserByPseudo(myUser.getPseudo());
            try{
                sessionUser.addGroup(u, g);
                g.getUsers().add(u);
            }catch(NoEntityFoundException e){
                throw new NoEntityFoundException(e);
            }
            return entityManager.merge(g);
        }
        else{
            throw new NoEntityFoundException("Group with name:" 
                    + group.getGroupname() + "doesn't exist in database.");
        }
    }

    /**
     * Method to remove a user from a group.
     * 
     * @param group group from which remove the user
     * @param user user to remove from the group
     * @return modified group
     * @throws NoEntityFoundException
     */
    @Override
    public Group removeUser(Group group, User user) 
            throws NoEntityFoundException {
        // retrieve attached group
        Group g = findGroup(group.getGroupname());
        if(g!=null){
            // retrieve attached user
            User u = sessionUser.findUserByPseudo(user.getPseudo());
            g.getUsers().remove(u);
            try{
                sessionUser.removeGroup(u, g);
            }catch(NoEntityFoundException e){
                logger.warn(e.getMessage(), e);
            }
            return entityManager.merge(g);
        }else{
            throw new NoEntityFoundException("Group with name:" 
                    + group.getGroupname() + "doesn't exist in database.");
        }
    }

    /**
     * Method to collect the users which belong to a specified group.<br />
     * Throws {@link NoEntityFoundException} when the group doesn't exist.
     * 
     * @param groupName group's name
     * @return collection of users which belong to the group
     * @exception NoEntityFoundException
     */
    @Override
    public Collection<User> collectUsers(String groupName) 
            throws NoEntityFoundException {
        Group group = findGroup(groupName);
        if (group != null) {
            //Create new HashSet to avoid lazily exceptions
            Set<User> result = new HashSet<>(); 
            result.addAll(group.getUsers());
            return result;
        } else{
            throw new NoEntityFoundException("Group with name:" 
                    + groupName + "doesn't exist in database.");
        }
    }

    /**
     * Method to make a petal accessible to all the members of a group.
     * 
     * @param group group to which grant access to the petal 
     * @param petal petal to make available for the group
     * @return modified Group instance (updated list of accessible petals)
     */
    @Override
    public Group addPetal(Group group, Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached group
        Group g = findGroup(group.getGroupname());
        if(g!=null){
            // retrieve attached petal
            Petal p = sessionPetal.findPetal(petal.getVendor(),
                    petal.getArtifactId(), petal.getVersion());
            g.getPetals().add(p);
            return entityManager.merge(group);
        }
        else{
            throw new NoEntityFoundException("You have to create" +
                    " the administrator first at all.");
        }
    }

    /**
     * Method to remove a Petal from the list of petals
     *  that are accessible for a group
     * 
     * @param group group to which remove the petal 
     * @param petal petal to make inaccessible for the Group
     * @return modified Group instance (updated list of accessible petals)
     * @exception NoEntityFoundException
     */
    @Override
    public Group removePetal(Group group, Petal petal) 
            throws NoEntityFoundException {
        // retrieve attached group
        Group g = findGroup(group.getGroupname());
        if(g!=null){
            // retrieve attached petal
            Petal p = sessionPetal.findPetal(petal.getVendor(),
                    petal.getArtifactId(), petal.getVersion());
            g.getPetals().remove(p);
            try {
                sessionPetal.removeAccesToGroup(p, g);
            } catch (NoEntityFoundException e) {
                logger.warn(e.getMessage(), e);
            }
            return entityManager.merge(group);
        }
        else{
            throw new NoEntityFoundException("The group " +
                    group.getGroupname() + " doesn't exist in the database");

        }
    }

    /**
     * Method to collect petals which are accessible
     *  for a specified group.<br />
     * Throws {@link NoEntityFoundException} if the group doesn't exist.
     * 
     * @param groupName group name
     * @return collection of petals which are accessible for the group
     * @throws NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String groupName) 
            throws NoEntityFoundException {
        Group group = findGroup(groupName);
        if (group != null) {
            //Create new HashSet to avoid lazily exception
            Set<Petal> result = new HashSet<>(); 
            result.addAll(group.getPetals());
            return result;
        } else {
            throw new NoEntityFoundException("Group with "
                    + groupName + " does not exist in database.");
        }
    }

    /**
     * Method to collect all existing groups in database.
     * 
     * @return groups list
     */
    @Override
    public Collection<Group> collectGroups() {
        Query groups = entityManager.createNamedQuery("Series.findAll");
        @SuppressWarnings("unchecked")
        List<Group> groupList = groups.getResultList();
        Set<Group> groupSet = new HashSet<Group>();
        groupSet.addAll(groupList);
        return groupSet;
    }

}
