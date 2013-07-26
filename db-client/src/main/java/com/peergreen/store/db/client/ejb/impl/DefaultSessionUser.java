package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


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
public class DefaultSessionUser implements ISessionUser {
    private EntityManager entityManager;

    private ISessionGroup groupSession;
    
    /**
     * Method to create a new instance of User and add it in the database.<br />
     * Others attributes are null when creating the user.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's mail 
     * @return created user instance
     * @exception EntityExistsException
     */
    @Override
    public User addUser(String pseudo, String password, String email) throws EntityAlreadyExistsException {
        User user = entityManager.find(User.class, pseudo);

        if (user != null) {
            throw new EntityAlreadyExistsException("This user already exists.");
        } else {
            user = new User(pseudo, password, email);
            entityManager.persist(user);
            return user;
        }
    }

    /**
     * Method to find a user.<br />
     * It returns null if the user doesn't exist.
     * 
     * @param pseudo the pseudo of the user to find 
     * @return the user with the pseudo 'pseudo'
     */
    @Override
    public User findUserByPseudo(String pseudo){
        return entityManager.find(User.class, pseudo);
    }

    /**
     * Method to collect all the users in the database.
     * 
     * @return collection of all the users 
     */
    @Override
    public Collection<User> collectUsers() {
        Query users = entityManager.createNamedQuery("User.findAll");
        @SuppressWarnings("unchecked")
        List<User> usersList = users.getResultList();
        Set<User> userSet = new HashSet<User>();
        userSet.addAll(usersList);
        return userSet;
    }

    /**
     * Method to remove a user using its pseudo.
     * 
     * @param pseudo pseudo of the user to remove from the database
     */
    @Override
    public void removeUserbyPseudo(String pseudo) {
        User user = entityManager.find(User.class, pseudo);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    /**
     * Method to remove a user.
     * 
     * @param myUser user to remove from the database
     */
    @Override
    public void removeUser(User myUser) {
        User u = findUserByPseudo(myUser.getPseudo());
        if (u != null) {
            entityManager.remove(u);
        }
    }

    /**
     * Method to add a group to the list of groups to which a user belongs.
     * 
     * @param user The user that must change the list of groups belonging
     * @param group The group to which add the user
     * @return A user with new list of groups 
     */
    @Override
    public User addGroup(User user, Group group) {
        // retrieve attached user
        User u = findUserByPseudo(user.getPseudo());
        // retrieve attached group
        Group g = groupSession.findGroup(group.getGroupname());
        
        u.getGroupSet().add(g);
        return entityManager.merge(u);
    }

    /**
     * Method to remove a group from the list of groups to which a user belongs.
     * 
     * @param user The user to remove from the group
     * @param group The group to which is removed the user
     * @return A user with new list of groups
     */
    @Override
    public User removeGroup(User user, Group group) {
        // retrieve attached user
        User u = findUserByPseudo(user.getPseudo());
        // retrieve attached group
        Group g = groupSession.findGroup(group.getGroupname());
        
        u.getGroupSet().remove(g);
        return entityManager.merge(u);
    }

    /**
     * Method to collect all the groups to which a user belongs.<br />
     * Throws NoEntityFoundException cannot be found in database.
     * 
     * @param pseudo the user's pseudo
     * @return A collection with the groups to which the user with the pseudo 'pseudo' belongs
     * @exception NoEntityFoundException
     */
    @Override
    public Collection<Group> collectGroups(String pseudo) throws NoEntityFoundException {
        User user = entityManager.find(User.class, pseudo);
        if (user != null) {
            return user.getGroupSet();
        } else {
            throw new NoEntityFoundException("This user doesn't exist in the database.");
        }
    }

    /**
     * Method to collect all the petals to which a user has access.<br />
     * Throws NoEntityFoundException if user cannot be found in database.
     * 
     * @param pseudo the user's pseudo
     * @return A collection with the petals to which the user with the pseudo 'pseudo' has access
     * @exception NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String pseudo) throws NoEntityFoundException {
        User user = entityManager.find(User.class, pseudo);
        
        if (user != null) {
            Set<Petal> petals = new HashSet<Petal>();
            Set<Group> groups = user.getGroupSet();

            Iterator<Group> it = groups.iterator();
            while (it.hasNext()) {
                petals.addAll(it.next().getPetals());
            }

            return petals;
        } else{
            throw new NoEntityFoundException("This user doesn't exist in the database.");
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
     * Method to modify a user's password.
     * 
     * @param oldUser the user to modify
     * @param password the new user's password
     * @return The oldUser modify with the new information
     */
    @Override
    public User updatePassword(User oldUser, String password) {
        oldUser.setPassword(password);
        return entityManager.merge(oldUser);
    }

    /**
     * Method to modify a user's mail .
     * 
     * @param oldUser the user to modify
     * @param email the new user's email
     * @return The oldUser modify with the new information
     */
    @Override
    public User updateMail(User oldUser, String email) {
        oldUser.setPassword(email);
        return entityManager.merge(oldUser);
    }
    
    @EJB
    public void setGroupSession(ISessionGroup groupSession) {
        this.groupSession = groupSession;
    }
}
