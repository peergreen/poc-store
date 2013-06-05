package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;

public class DefaultGroup implements ISessionGroup {

    @Override
    public Group addGroup(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group findGroup(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group updateGroup(String oldGroupName, String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteGroup(String groupName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Group addUser(Group group, User myUser) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group removeUser(Group group, User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<User> collectUsers(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group addPetal(Group group, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group removePetal(Group group, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

  
}
