package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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


    private EntityManager entityManager;

    /**
     * Method to create a new instance of user and add it in the database
     * Others attributes are null when creating the user
     * 
     * @param pseudo the user's pseudo
     * @param password the user's password
     * @param email the user's mail 
     * @return created user instance
     */
    @Override
    public User addUser(String pseudo, String password, String email)throws EntityExistsException  {
        User user = entityManager.find(User.class, pseudo);

        if (user != null){
            throw new EntityExistsException("This user already exists");
        }
        else{
            user = new User(pseudo, password, email);
            entityManager.persist(user);
            return user;
        }
    }

    /**
     * Method to find a user.
     * It returns null if the user doesn't exist.
     * 
     * @param pseudo the pseudo of the user to find 
     * 
     * @return the user with the pseudo 'pseudo'
     */
    @Override
    public User findUserByPseudo(String pseudo){
        User user = entityManager.find(User.class, pseudo);
        return user;
    }

    /**
     * Method to collect all the users in the database
     * 
     * @return A collection of all the users 
     */
    @Override
    public Collection<User> collectUsers() {
        Query users = entityManager.createNamedQuery("User.findAll");
        List<User> usersList = users.getResultList();
        Set<User> userSet = new HashSet<User>();
        userSet.addAll(usersList);
        return userSet;
    }

    /**
     * Method to remove a user using his pseudo.
     * It throws new IllegalArgumentException if the user is null
     * 
     * @param pseudo The pseudo of the user to remove from the database
     */
    @Override
    public void removeUserbyPseudo(String pseudo)throws IllegalArgumentException {

        User user = entityManager.find(User.class, pseudo);
        if(user!=null){
            entityManager.remove(user);}
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method to remove a user
     * 
     * @param myUser The user to remove from the database
     */
    @Override
    public void removeUser(User myUser){

        entityManager.remove(myUser);
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

        Set<Group> groups = user.getGroupSet();
        groups.add(group);
        user.setGroupSet(groups);
        user =  entityManager.merge(user);
        return user;
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

        Set<Group> groups = user.getGroupSet();
        groups.remove(group);
        user.setGroupSet(groups);

        user = entityManager.merge(user);
        return user;
    }

    /**
     * Method to collect all the groups to which a user belongs
     * It throws new IllegalArgumentException if the user is null
     * 
     * @param pseudo the user's pseudo
     * 
     * @return A collection with the groups to which the user with the pseudo 'pseudo' belongs
     */
    @Override
    public Collection<Group> collectGroups(String pseudo)throws IllegalArgumentException  {

        User user = entityManager.find(User.class, pseudo);
        if(user!=null){
            return user.getGroupSet();
        }
        else{
            throw new IllegalArgumentException("This user doesnt exist in the database");
        }

    }

    /**
     * Method to collect all the petals to which a user has access 
     * It throws new IllegalArgumentException if the user is null
     * 
     * @param pseudo the user's pseudo
     * 
     * @return A collection with the petals to which the user with the pseudo 'pseudo' has access
     */
    @Override
    public Collection<Petal> collectPetals(String pseudo)throws IllegalArgumentException {


        Set<Group> groups = new HashSet<Group>();
        Set<Petal> petals = new HashSet<Petal>();

        User user = entityManager.find(User.class, pseudo);
        if(user!=null){
            groups = user.getGroupSet();

            Object [] groupsTab = groups.toArray();
            for(int i =0; i<groupsTab.length; i++)
            {
                petals.addAll(((Group) groupsTab[i]).getPetals());
            }

            return petals;}
        else{
            throw new IllegalArgumentException("This user doesn't exist in the database");
        }
    }

    /**
     * Method to set entity manager.
     * 
     * @param entityManager entity manager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Method to modify a user's password  
     * 
     * @param oldUser the user to modify
     * @param password the new user's password
     * 
     * @return The oldUser modify with the new information
     */
    @Override
    public User updatePassword(User oldUser, String password) {

        oldUser.setPassword(password);

        return entityManager.merge(oldUser);
    }

    /**
     * Method to modify a user's mail 
     * 
     * @param oldUser the user to modify
     * @param email the new user's email
     * 
     * @return The oldUser modify with the new information
     */
    @Override
    public User updateMail(User oldUser, String email) {

        oldUser.setPassword(email);

        return entityManager.merge(oldUser);
    }

}
