package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

@Stateless
public class DefaultUser implements ISessionUser {

    @Override
    public User addUser(String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findUserByPseudo(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<User> collectUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeUserbyPseudo(String pseudo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeUser(User myUser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public User updateUser(User oldUser, String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User addGroup(User user, Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User removeGroup(User user, Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Group> collectGroups(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }


}
