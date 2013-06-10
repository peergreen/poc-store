package com.peergreen.store.controller;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;

/**
 * Interface defining all group related operations:
 * <ul>
 *      <li>Create group on database</li>
 *      <li>Modify existing group on database</li>
 *      <li>Remove group from database</li>
 *      <li>Retrieve group's member list</li>
 *      <li>Add a user to group</li>
 *      <li>Remove user from group</li>
 * </ul>
 * 
 */
public interface IGroupController {

    /**
     * Method to add a new group in database.
     * 
     * @param groupName group's name
     * @return created group instance
     */
    Group addGroup(String groupName);
    
    /**
     * Method to modify an existing Group entity.
     * 
     * @param groupName old group's name
     * @param groupName new group's name
     * @return updated group
     */
    Group modifyGroup(String oldGroupName, String newGroupName);
    
    /**
     * Method to remove a group from the database.
     * 
     * @param groupName group's name
     */
    void removeGroup(String groupName);
    
    /**
     * Method to collect all the group's users.
     * 
     * @param groupName group's name
     * @return list of all the group's users
     */
    Collection<User> collectUsers(String groupName);
    
    /**
     * Method to add a user to a group.
     * 
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     */
    Group addUser(String groupName, String pseudo);
    
    /**
     * Method to remove a user from a group.
     * 
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     */
    Group removeUser(String groupName, String pseudo);
    
}
