package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.IUser;

@Stateless
public class DefaultUser implements IUser {

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
    public void deleteUserbyPseudo(String pseudo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteUser(User myUser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public User updateUser(String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User addGroup(Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User deleteGroup(Group group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Group> retrieveGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> retrievePetals() {
        // TODO Auto-generated method stub
        return null;
    }

}
