package com.peergreen.store.controller;

import java.util.Collection;
import java.util.Map;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all user related operations:
 * <ul>
 *      <li>retrieve metadata</li>
 *      <li>add instance on database</li>
 *      <li>remove instance from database</li>
 *      <li>modify instance on database</li>
 *      <li>collect all the user's groups</li>
 * </ul>
 *
 */
public interface IUserController {

    /**
     * Method to retrieve user's information.
     * 
     * @return indexed collection of user's information or
     * <em>null</em> if user doesn't exist.
     */
    Map<String, String> getUserMetadata(String pseudo);

    /**
     * Method to retrieve a user instance from its pseudo.
     * 
     * @param pseudo user's pseudo
     * @return corresponding User instance
     */
    User getUser(String pseudo);

    /**
     * Method to add a new user to the database.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     * @return created user
     */
    User addUser(String pseudo, String password, String email);

    /**
     * Method to remove a user from the database.
     * 
     * @param pseudo user's pseudo
     */
    void removeUser(String pseudo);

    /**
     * Method to modify a user account.
     * 
     * @param user pseudo of the user to modify
     * @param password user's password
     * @param email user's mail
     * @return modified user
     * @throws NoEntityFoundException 
     */
    public User updateUser(String pseudo, String password, String email) throws NoEntityFoundException;

    /**
     * Method to collect all user's groups.
     * 
     * @param pseudo user's pseudo
     * @return list of all user's groups
     */
    Collection<Group> collectGroups(String pseudo);

    
    // TODO
    /**
     * Method to collect all petals to which the user has access.<br />
     * Throws NoEntityFoundException if the user does not exist in database.
     * 
     * @param pseudo user's pseudo
     * @return list of all petals accessible to the user.
     * @throw NoEntityFoundException
     */
    // Collection<Petal> collectPetals(String pseudo);
}
