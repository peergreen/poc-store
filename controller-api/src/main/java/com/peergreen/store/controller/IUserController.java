package com.peergreen.store.controller;

import java.util.List;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

public interface IUserController {
    
    /**
     * Method to update a user account.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     * @return modified user
     */
    IUser updateUser(String pseudo, String password, String email);
    
    /**
     * Method to collect all server's users.
     * 
     * @return list of all server's users
     */
    List<IUser> collectUsers();
    
    /**
     * Method to retrieve user's information.
     * 
     * @return indexed collection of user's information.
     */
    Map<String, String> getUserMetadata();
    
    /**
     * Method to add a new user on the server.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     */
    void addUser(String pseudo, String password, String email);
    
    /**
     * Method to remove a user from the server.
     * 
     * @param pseudo user's pseudo
     */
    void removeUser(String pseudo);
    
    /**
     * Method to collect all user's groups.
     * 
     * @return list of all user's groups
     */
    List<IGroup> collectGroups();
    
}
