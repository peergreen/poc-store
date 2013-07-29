package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public interface ISessionUser {

    /**
     * Method to create a new instance of user and add it in the database.
     * Others attributes are null when creating the user.
     * 
     * @param pseudo the user's pseudo
     * @param password the user's password
     * @param email the user's mail 
     * @return created user instance
     * @throws EntityAlreadyExistsException 
     */
    User addUser(String pseudo, String password, String email) throws EntityAlreadyExistsException;

    /**
     * Method to find a user
     * 
     * @param pseudo the pseudo of the user to find 
     * 
     * @return the user with the pseudo 'pseudo'
     */
    User findUserByPseudo(String pseudo);

    /**
     * Method to collect all the users in the database
     * 
     * @return A collection of all the users 
     */
    Collection <User> collectUsers();

    /**
     * Method to remove a user using its pseudo.
     * 
     * @param pseudo pseudo of the user to remove from the database
     */
    void removeUserbyPseudo(String pseudo);

    /**
     * Method to remove a user
     * 
     * @param myUser The user to remove from the database
     */
    void removeUser(User myUser);

    /**
     * Method to modify a user's password  
     * 
     * @param oldUser the user to modify
     * @param password the new user's password
     * 
     * @return The oldUser modify with the new information
     * @throws NoEntityFoundException
     */
    User updatePassword( User oldUser, String password) throws NoEntityFoundException;

    /**
     * Method to modify a user's mail 
     * 
     * @param oldUser the user to modify
     * @param email the new user's email
     * 
     * @return The oldUser modify with the new information
     * @throws NoEntityFoundException
     */
    User updateMail( User oldUser, String email) throws NoEntityFoundException;

    /**
     * Method to add a group to the list of groups to which a user belongs 
     * 
     * @param user The user that must change the list of groups belonging
     * @param group The group to which add the user
     * 
     * @return A user with new list of groups 
     * @throws NoEntityFoundException
     */
    User addGroup(User user, Group group) throws NoEntityFoundException;

    /**
     * Method to remove a group from the list of groups to which a user belongs 
     * 
     * @param user The user to remove from the group
     * @param group The group to which is removed the user
     * @return A user with new list of groups
     * @throws NoEntityFoundException
     */
    User removeGroup(User user, Group group) throws NoEntityFoundException;

    /**
     * Method to collect all the groups to which a user belongs.
     * 
     * @param pseudo the user's pseudo
     * @return A collection with the groups to which the user with the pseudo 'pseudo' belongs
     * @throws NoEntityFoundException 
     */
    Collection<Group> collectGroups(String pseudo) throws NoEntityFoundException;

    /**
     * Method to collect all the petals to which a user has access.
     * 
     * @param pseudo the user's pseudo
     * @return A collection with the petals to which the user with the pseudo 'pseudo' has access
     * @throws NoEntityFoundException 
     */
    Collection<Petal> collectPetals(String pseudo) throws NoEntityFoundException;

}
