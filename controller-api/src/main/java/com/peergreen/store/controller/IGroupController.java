package com.peergreen.store.controller;

import java.util.List;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

public interface IGroupController {

    /**
     * Method to add a new group in database.
     * 
     * @param groupName group's name
     */
    void addGroup(String groupName);
    
    /**
     * Method to modify an existing Group entity.
     * 
     * @param groupName group's name
     * @return
     */
    IGroup modifyGroup(String groupName);
    
    /**
     * Method to remove a group from the database.
     * 
     * @param groupName group's name
     */
    void removeGroup(String groupName);
    
    /**
     * Method to collect all the group's users.
     * 
     * @return list of all the group's users
     */
    List<IUser> collectUsers();
    
    /**
     * Method to add a user to a group.
     * 
     * @param pseudo user's pseudo
     * @param groupName group's name
     */
    void addUser(String pseudo, String groupName);
    
    /**
     * Method to remove a user from a group.
     * 
     * @param pseudo user's pseudo
     * @param groupName group's name
     */
    void removeUser(String pseudo, String groupName);
    
}
