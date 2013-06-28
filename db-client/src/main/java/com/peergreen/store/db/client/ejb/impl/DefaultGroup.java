package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

/**
 * Class defining an entity session to manage the entity Group
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
public class DefaultGroup implements ISessionGroup {

    private EntityManager entityManager;

    private ISessionUser sessionUser;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new group in the database.
     * The attributes petals are null when creating the group
     * but we have one user : the administrator
     * It throws an exception "EntityExistsException" 
     * when the entity already exist in the database
     * 
     * @param groupName the name of the group to create
     * @return The new group
     */
    @Override
    public Group addGroup(String groupName) throws EntityExistsException{
        Group group = findGroup(groupName) ;

        if(group != null) {
            throw new EntityExistsException();
        }
        else{
            group = new Group(groupName);
            entityManager.persist(group);
            return group;
        }
    }

    /**
     * Method to find a group in the database
     * It returns null if the group doesn't exist. 
     *  
     * @param groupName the group's name
     * @return the group with the name 'groupName'
     */
    @Override
    public Group findGroup(String groupName)throws NoResultException {
        Query q = entityManager.createNamedQuery("GroupByName");
        q.setParameter("name", groupName);
        Group result;
        try{
            result = (Group)q.getSingleResult();
        }catch(NoResultException e){
            result = null;
        }
        return result;
    }


    /**
     * Method to delete the group with the name groupName
     * It throws an IllegalArgumentException if the entity to remove
     * doesn't exist in the database.
     * @param groupName the name of the group to delete
     */
    @Override
    public void deleteGroup(String groupName)throws IllegalArgumentException {
        Group group = findGroup(groupName);
        if(group != null){
            entityManager.remove(group);
        }
        else{
            throw new IllegalArgumentException();
        }   
    }

    /**
     * Method to add a user to the instance of Group 'group'
     * 
     * @param group the group to which add the user
     * @param myUser the user to add to the group 
     * 
     * @return A group with the new user
     */
    @Override
    public Group addUser(Group group, User myUser) {

        Set<User> users = group.getUsers();
        users.add(myUser);
        group.setUsers(users);
        group=  entityManager.merge(group);
        sessionUser.addGroup(myUser, group);
        return group;
    }

    /**
     * Method to remove a user to the instance of Group 'group'
     * 
     * @param group the group to which remove the user
     * @param user the user to remove to the group
     * 
     * @return A group without the user deleted
     */
    @Override
    public Group removeUser(Group group, User user) {
        Set<User> users = group.getUsers();
        users.remove(user);
        group.setUsers(users);
        group=  entityManager.merge(group);
        sessionUser.removeGroup(user, group);
        return group;
    }

    /**
     * Method to collect the users which belongs to the group 
     * with the name 'groupName'
     * It throws an IllegalArgumentException
     * when the group doesn't exist
     * @param groupName the group's name
     * 
     * @return A collection of users wich belongs to the group
     */
    @Override
    public Collection<User> collectUsers(String groupName)throws IllegalArgumentException{
        Group group = findGroup(groupName);

        if(group != null){
            return group.getUsers();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to add a new Petal to the list of petals that are accessible from the Group 'group'
     * 
     * @param group the group to which add a new petal 
     * @param petal the petal to make available from the Group 'group'
     * 
     * @return A new group with the new petal
     */
    @Override
    public Group addPetal(Group group, Petal petal) {
        Set<Petal> petals = group.getPetals();
        petals.add(petal);
        group.setPetals(petals);
        group=  entityManager.merge(group);
        return group;
    }

    /**
     * Method to remove a Petal from the list of petals that are accessible from the Group 'group'
     * 
     * @param group the group to which remove the petal 
     * @param petal the petal to make inaccessible from the Group 'group'
     * @return
     */
    @Override
    public Group removePetal(Group group, Petal petal) {
        Set<Petal> petals = group.getPetals();
        petals.remove(petal);
        group.setPetals(petals);
        group=  entityManager.merge(group);
        return group;
    }

    /**
     * Method to collect the petals which are accessible from the Group 'group'
     * It throws an IllegalArgumentException when the group doesn't exist
     * 
     * @param groupName the group's name
     * 
     * @return A collection of petals which are accessible from the group
     */
    @Override
    public Collection<Petal> collectPetals(String groupName)throws IllegalArgumentException {
        Group group = findGroup(groupName);

        if(group != null){
            return group.getPetals();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to collect all existing groups on database.
     * 
     * @return groups list
     */
    @Override
    public Collection<Group> collectGroups() {
        Query groups = entityManager.createNamedQuery("Series.findAll");
        List groupList = groups.getResultList();


        Set<Group> groupSet = new HashSet<Group>();
        Iterator<Group> it = groupSet.iterator();
        int index =0;
        while(it.hasNext()){
            Group gp = it.next();
            gp = (Group) groupList.get(index);
            index ++;
        }
        groupSet.addAll(groupList);
        return groupSet;
    }


    /**
     * @param sessionUser the sessionUser to set
     */
    @EJB
    public void setSessionUser(ISessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

}
