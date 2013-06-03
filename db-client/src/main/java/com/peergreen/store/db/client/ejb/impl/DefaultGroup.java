package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;

public class DefaultGroup implements ISessionGroup {

    @Override
    public IGroup addGroup(String groupname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGroup findGroup(String groupname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGroup updateGroup(String groupname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteGroup(String groupname) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IGroup addUser(IUser myUser) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGroup deleteUserbyPseudo(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IUser> collectUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGroup addPetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGroup deletePetalById(int petalId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPetal> collectPetals() {
        // TODO Auto-generated method stub
        return null;
    }

    
}
