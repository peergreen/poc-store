package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
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
    public User addUser(String pseudo, String password, String email) {
        User user = new User();
        user.setPseudo(pseudo);
        user.setPassword(password);
        user.setEmail(email);
        Set<Group> groups = new HashSet<Group>();
        user.setGroupSet(groups);
        entityManager.persist(user);
        return user;
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
     * Method to remove a user using his pseudo
     * 
     * @param pseudo The pseudo of the user to remove from the database
     */
    @Override
    public void removeUserbyPseudo(String pseudo) {

        User user = entityManager.find(User.class, pseudo);
        entityManager.remove(user);
    }

    /**
     * Method to remove a user
     * 
     * @param myUser The user to remove from the database
     */
    @Override
    public void removeUser(User myUser) {

        entityManager.remove(myUser);
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

        oldUser.setPassword(password);
        oldUser.setEmail(email);

        entityManager.merge(oldUser);

        return oldUser;
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
     * 
     * @param pseudo the user's pseudo
     * 
     * @return A collection with the groups to which the user with the pseudo 'pseudo' belongs
     */
    @Override
    public Collection<Group> collectGroups(String pseudo) {

        User user = entityManager.find(User.class, pseudo);

        return user.getGroupSet();
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
        Set<Group> groups = new HashSet<Group>();
        Set<Petal> petals = new HashSet<Petal>();

        User user = entityManager.find(User.class, pseudo);
        groups = user.getGroupSet();

        Object [] groupsTab = groups.toArray();
        for(int i =0; i<groupsTab.length; i++)
        {
            petals.addAll(((Group) groupsTab[i]).getPetals());
        }

        return petals;
    }

    /**
     * Method to retrieve entity manager.
     * 
     * @return entity manager
     */
    public EntityManager getEntityManager() {
        return this.entityManager;
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

    @Override
    public Collection<Group> collectGroups() {
        // TODO Auto-generated method stub
        return null;
    }

}
