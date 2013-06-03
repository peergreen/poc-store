package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

@Stateless
public class DefaultUser implements ISessionUser {

    @Override
    public IUser addUser(String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IUser findUserByPseudo(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IUser> collectUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteUserbyPseudo(String pseudo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteUser(IUser myUser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IUser updateUser(String pseudo, String password, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IUser addGroup(IGroup group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IUser deleteGroup(IGroup group) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IGroup> collectGroups(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPetal> collectPetals(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

}
