package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.IGroup;

public class DefaultGroup implements IGroup {

    @Override
    public Group addGroup(String groupname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group findGroup(String groupname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Group> collectGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group updateGroup(String groupname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteGroup(String groupname) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Group addUser(User myUser) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group deleteUserbyPseudo(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<User> retrieveUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group addPetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group deletePetalById(int petalid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> retrievePetals() {
        // TODO Auto-generated method stub
        return null;
    }

}
