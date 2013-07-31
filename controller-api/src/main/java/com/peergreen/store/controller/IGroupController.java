package com.peergreen.store.controller;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all group related operations:
 * <ul>
 *      <li>Create group on database</li>
 *      <li>Modify existing group on database</li>
 *      <li>Remove group from database</li>
 *      <li>Retrieve group's member list</li>
 *      <li>Add a user to group</li>
 *      <li>Remove user from group</li>
 *      <li>Retrieve all users member of this group</li>
 *      <li>Retrieve all petals to those this group has access</li>
 * </ul>
 * 
 */
public interface IGroupController {

    /**
     * Method to add a new group in database.
     * 
     * @param groupName group's name
     * @return created group instance
     * @throws NoEntityFoundException 
     * @throws EntityAlreadyExistsException 
     */
    Group createGroup(String groupName) throws EntityAlreadyExistsException, NoEntityFoundException;
    
    /**
     * Method to remove a group from the database.
     * 
     * @param groupName group's name
     */
    void deleteGroup(String groupName);
    
    /**
     * Method to collect all the group's users.
     * 
     * @param groupName group's name
     * @return list of all the group's users
     * @throws NoEntityFoundException 
     */
    Collection<User> collectUsers(String groupName) throws NoEntityFoundException;
    
    /**
     * Method to add a user to a group.
     * 
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     * @throws NoEntityFoundException 
     */
    Group addUser(String groupName, String pseudo) throws NoEntityFoundException;
    
    /**
     * Method to remove a user from a group.
     * 
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     * @throws NoEntityFoundException 
     */
    Group removeUser(String groupName, String pseudo) throws NoEntityFoundException;
 
    // TODO
    /**
     * Method to collect all users member of a group.<br />
     * Throws NoEntityFoundException if specified group does not exist.
     * 
     * @param name group name
     * @return list of all users member of the specified group
     * @throw NoEntityFoundException
     */
    // Collection<User> collectUsers(String name);
    
}