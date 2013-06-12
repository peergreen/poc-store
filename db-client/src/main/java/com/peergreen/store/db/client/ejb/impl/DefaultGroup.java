package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;

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

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to add a new group in the database.
     * The attributes users and petals are null when creating the group
     * 
     * @param groupName the name of the group to create
     * @return The new group
     */
    @Override
    public Group addGroup(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to find a group in the database
     * 
     * @param groupName the group's name
     * @return the group with the name 'groupName'
     */
    @Override
    public Group findGroup(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to change the name of the group 
     * 
     * @param oldGroupName the older name
     * @param groupName the new name
     * @return the group changed 
     */
    @Override
    public Group updateGroup(String oldGroupName, String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to delete the group with the name groupName
     * 
     * @param groupName the name of the group to delete
     */
    @Override
    public void deleteGroup(String groupName) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect the users which belongs to the group with the name 'groupName'
     * 
     * @param groupName the group's name
     * 
     * @return A collection of users wich belongs to the group
     */
    @Override
    public Collection<User> collectUsers(String groupName) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect the petals which are accessible from the Group 'group'
     * 
     * @param groupName the group's name
     * 
     * @return A collection of petals which are accessible from the group
     */
    @Override
    public Collection<Petal> collectPetals(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all existing groups on database.
     * 
     * @return groups list
     */
    @Override
    public Collection<Group> collectGroups() {
        // TODO Auto-generated method stub
        return null;
    }

}
