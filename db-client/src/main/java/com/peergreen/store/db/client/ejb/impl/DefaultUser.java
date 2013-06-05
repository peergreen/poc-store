package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

/**
 * Class defining an entity session to manage the entity User
 * <ul>
 *      <li>Create user on database</li>
 *      <li>Find a user from the database</li>
 *      <li>Collect all the users from the database</li>
 *      <li>Remove a user by his pseudo</li>
 *      <li>Remove a user</li>
 *      <li>Modify a user</li>
 *      <li>Add a group to those which the user belongs</li>
 *      <li>Remove a group from those which the user belongs</li>
 *      <li>Collect all the groups which the user belongs</li>
 *      <li>Collect all the petals which the user have access</li>
 * </ul>
 * 
 */
@Stateless
public class DefaultUser implements ISessionUser {

    /**
     * Method to create a new instance of user and add it in the database
     * Others attributes are null when creating the user
     * 
     * @param pseudo the user's pseudo
     * @param password the user's password
     * @param email the user's mail 
     * @return
     */
    @Override
    public User addUser(String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to find a user
     * 
     * @param pseudo the pseudo of the user to find 
     * 
     * @return the user with the pseudo 'pseudo'
     */
    @Override
    public User findUserByPseudo(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all the users in the database
     * 
     * @return A collection of all the users 
     */
    @Override
    public Collection<User> collectUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to remove a user using his pseudo
     * 
     * @param pseudo The pseudo of the user to remove from the database
     */
    @Override
    public void removeUserbyPseudo(String pseudo) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to remove a user
     * 
     * @param myUser The user to remove from the database
     */
    @Override
    public void removeUser(User myUser) {
        // TODO Auto-generated method stub

    }

    /**
     * Method to modify a user 
     * 
     * @param oldUser the user to modify
     * @param pseudo the new user's pseudo
     * @param password the new user's password
     * @param email the new user's email
     * 
     * @return The oldUser modify with the new informations
     */
    @Override
    public User updateUser(User oldUser, String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to add a group to the list of groups to which a user belongs 
     * 
     * @param user The user that must change the list of groups belonging
     * @param group The group to which add the user
     * 
     * @return A user with new list of groups 
     */
    @Override
    public User addGroup(User user, Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to remove a group from the list of groups to which a user belongs 
     * 
     * @param user The user to remove from the group
     * @param group The group to which is removed the user
     * 
     * @return A user with new list of groups
     */
    @Override
    public User removeGroup(User user, Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all the groups to which a user belongs
     * 
     * @param pseudo the user's pseudo
     * 
     * @return A collection with the groups to which the user with the pseudo 'pseudo' belongs
     */
    @Override
    public Collection<Group> collectGroups(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method to collect all the petals to which a user has access 
     * 
     * @param pseudo the user's pseudo
     * 
     * @return A collection with the petals to which the user with the pseudo 'pseudo' has access
     */
    @Override
    public Collection<Petal> collectPetals(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }


}
