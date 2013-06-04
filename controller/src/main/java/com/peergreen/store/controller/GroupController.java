package com.peergreen.store.controller;

import java.util.List;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;

public class GroupController implements IGroupController {

    @Override
    public void addGroup(String groupName) {
        // TODO Auto-generated method stub

    }

    @Override
    public Group modifyGroup(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeGroup(String groupName) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<User> collectUsers(String groupName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addUser(String pseudo, String groupName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUser(String pseudo, String groupName) {
        // TODO Auto-generated method stub

    }

}
