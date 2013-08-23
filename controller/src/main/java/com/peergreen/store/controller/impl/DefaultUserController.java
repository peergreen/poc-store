package com.peergreen.store.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.controller.IUserController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
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
    private static Logger theLogger = Logger.getLogger(DefaultUserController.class.getName());


    /**
     * Method to retrieve user's information.
     * 
     * @return indexed collection of user's information or
     * <em>null</em> if user doesn't exist.
     */
    @Override
    public Map<String, String> getUserMetadata(String pseudo) {
        User user = userSession.findUserByPseudo(pseudo);

        if (user!=null){
            Map<String, String> metadata = new HashMap<String, String>();
            metadata.put("pseudo", user.getPseudo());
            metadata.put("password", user.getPassword());
            metadata.put("email", user.getEmail());
            return metadata;
        }else{
            return null;
        }
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
     * @throws EntityAlreadyExistsException 
     */
    @Override
    public User addUser(String pseudo, String password, String email) throws EntityAlreadyExistsException {
        User user = null;

        try {
            user = userSession.addUser(pseudo, password, email);
            return user;
        } catch(EntityAlreadyExistsException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new EntityAlreadyExistsException(e);
        }

    }

    /**
     * Method to remove a user from the database.
     * 
     * @param pseudo user's pseudo
     */
    @Override
    public User removeUser(String pseudo) {
        return userSession.removeUserbyPseudo(pseudo);
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
     * @throws NoEntityFoundException 
     */
    @Override
    public Collection<Group> collectGroups(String pseudo) throws NoEntityFoundException {
        Collection<Group> groups = null;
        try {
            groups = userSession.collectGroups(pseudo);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
        return groups; 
    }

    /**
     * Method to collect all petals to which the user has access.<br />
     * Throws NoEntityFoundException if the user does not exist in database.
     * 
     * @param pseudo user's pseudo
     * @return list of all petals accessible to the user.
     * @throws NoEntityFoundException 
     * @throw NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String pseudo) throws NoEntityFoundException {
        try {
            return userSession.collectPetals(pseudo);   
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    @Bind
    public void bindUserSession(ISessionUser userSession) {
        this.userSession = userSession;
    }
}