package com.peergreen.store.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import com.peergreen.store.controller.IUserController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Class defining all user related operations:
 * <ul>
 *      <li>retrieve metadata</li>
 *      <li>retrieve instance</li>
 *      <li>add instance on database</li>
 *      <li>remove instance from database</li>
 *      <li>modify instance on database</li>
 *      <li>collect all the user's groups</li>
 * </ul>
 *
 */
@Component
@Instantiate
@Provides
public class DefaultUserController implements IUserController {

    private ISessionUser userSession;

    public DefaultUserController(@Requires ISessionUser userSession) {
        this.userSession = userSession;
    }

    /**
     * Method to retrieve user's information.
     * 
     * @return indexed collection of user's information or
     * <em>null</em> if user doesn't exist.
     */
    @Override
    public Map<String, String> getUserMetadata(String pseudo) {
        User user = userSession.findUserByPseudo(pseudo);
        Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("pseudo", user.getPseudo());
        metadata.put("password", user.getPassword());
        metadata.put("email", user.getEmail());

        return metadata;
    }

    /**
     * Method to retrieve a user instance from its pseudo.
     * 
     * @param pseudo user's pseudo
     * @return corresponding User instance
     */
    @Override
    public User getUser(String pseudo) {
        return userSession.findUserByPseudo(pseudo);
    }

    /**
     * Method to add a new user to the database.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     * @return created user
     */
    @Override
    public User addUser(String pseudo, String password, String email) {
        User user = null;

        try {
            user = userSession.addUser(pseudo, password, email);
        } catch(EntityAlreadyExistsException e) {
            System.out.println("User--------------------");
            System.err.println("User already exists.");
        }
        
        return user;
    }

    /**
     * Method to remove a user from the database.
     * 
     * @param pseudo user's pseudo
     */
    @Override
    public void removeUser(String pseudo) {
        userSession.removeUserbyPseudo(pseudo);
    }

    /**
     * Method to modify a user account.<br />
     * All attributes must be provided but can be unchanged if needed.
     * 
     * @param user pseudo of the user to modify
     * @param password user's password
     * @param email user's mail
     * @return modified user
     * @throws NoEntityFoundException
     */
    @Override
    public User updateUser(String pseudo, String password, String email) throws NoEntityFoundException {
        User u = userSession.findUserByPseudo(pseudo);
        if (u != null) {
            userSession.updatePassword(u, password);
            userSession.updateMail(u, email);
            return u;
        } else {
            throw new NoEntityFoundException("The user " + pseudo + " does not exist in database.");
        }
    }

    /**
     * Method to collect all user's groups.
     * 
     * @param pseudo user's pseudo
     * @return list of all user's groups
     */
    @Override
    public Collection<Group> collectGroups(String pseudo) {
        Collection<Group> groups = null;
        try {
            groups = userSession.collectGroups(pseudo);
        } catch (NoEntityFoundException e) {
            // TODO
        }
        return groups; 
    }

    @Bind
    private void bindUserSession(ISessionUser userSession) {
        this.userSession = userSession;
    }
}
