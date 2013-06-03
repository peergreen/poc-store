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
    
    @Override
    public Map<String, String> getUserMetadata(String pseudo) {
        IUser user = userSession.findUserByPseudo(pseudo);
        Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("pseudo", user.getPseudo());
        metadata.put("password", user.getPassword());
        metadata.put("email", user.getEmail());
        
        return metadata;
    }

    @Override
    public IUser getUser(String pseudo) {
        return userSession.findUserByPseudo(pseudo);
    }

    @Override
    public void addUser(String pseudo, String password, String email) {
        userSession.addUser(pseudo, password, email);
    }

    @Override
    public void removeUser(String pseudo) {
        userSession.deleteUserbyPseudo(pseudo);
    }

    @Override
    public IUser modifyUser(String pseudo, String password, String email) {
        IUser user = userSession.updateUser(pseudo, password, email);
        return user;
    }

    @Override
    public List<IGroup> collectGroups(String pseudo) {
        return (List<IGroup>) userSession.collectGroups(pseudo);
    }

}
