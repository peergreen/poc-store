package com.peergreen.store.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityExistsException;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import com.peergreen.store.controller.IUserController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

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

        try{
            user = userSession.addUser(pseudo, password, email);
        }catch(EntityExistsException e){

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
        try{
            userSession.removeUserbyPseudo(pseudo);
        }catch(IllegalArgumentException e){

        }
    }

    /**
     * Method to modify a user account.
     * 
     * @param oldUser the user to modify
     * @param password user's password
     * @return modified user
     */
    @Override
    public User modifyUserPassword(User oldUser, String password) {
        return userSession.updatePassword(oldUser, password);
    }

    /**
     * Method to modify a user account.
     * 
     * @param oldUser the user to modify
     * @param email user's email
     * @return modified user
     */
    @Override
    public User modifyUserEmail(User oldUser,String email) {
        return userSession.updateMail(oldUser, email);
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
        try{
            groups = userSession.collectGroups(pseudo);
        }
        catch(IllegalArgumentException e){

        }
        return groups; 
    }

    @Bind
    private void bindUserSession(ISessionUser userSession) {
        this.userSession = userSession;
    }
}
