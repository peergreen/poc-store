package com.peergreen.store.controller;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;

public class StoreManagement implements IStoreManagment {

    @Override
    public void addLink(String url, String description) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeLink(int linkId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Link> collectLinks() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Petal> collectPetals() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Petal> collectPetalsForUser(String pseudo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Petal> collectPetalsFromStaging() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Petal> collectPetalsFromLocal() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> collectUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> collectGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void submitPetal(String groupId, String artifactId, String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities, File petalBinary) {
        // TODO Auto-generated method stub

    }

    @Override
    public void validatePetal(String groupId, String artifactId, String version) {
        // TODO Auto-generated method stub

    }

}
