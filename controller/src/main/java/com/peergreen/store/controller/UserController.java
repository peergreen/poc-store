package com.peergreen.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

@Component
@Instantiate
@Provides
public class UserController implements IUserController {

    private ISessionUser userSession;
    
    public UserController(@Requires ISessionUser userSession) {
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
        IUser user = userSession.findUserByPseudo(pseudo);
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
    public IUser getUser(String pseudo) {
        return userSession.findUserByPseudo(pseudo);
    }

    /**
     * Method to add a new user to the database.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     */
    @Override
    public void addUser(String pseudo, String password, String email) {
        userSession.addUser(pseudo, password, email);
    }

    /**
     * Method to remove a user from the database.
     * 
     * @param pseudo user's pseudo
     */
    @Override
    public void removeUser(String pseudo) {
        userSession.deleteUserbyPseudo(pseudo);
    }

    /**
     * Method to modify a user account.
     * 
     * @param pseudo user's pseudo
     * @param password user's password
     * @param email user's email
     * @return modified user
     */
    @Override
    public IUser modifyUser(String pseudo, String password, String email) {
        return userSession.updateUser(pseudo, password, email);
    }

    /**
     * Method to collect all user's groups.
     * 
     * @param pseudo user's pseudo
     * @return list of all user's groups
     */
    @Override
    public List<IGroup> collectGroups(String pseudo) {
        return (List<IGroup>) userSession.collectGroups(pseudo);
    }

}
