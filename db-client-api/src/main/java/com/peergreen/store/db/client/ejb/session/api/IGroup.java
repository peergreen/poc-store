package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.Group;
import com.peergreen.store.db.client.ejb.entity.api.Petal;
import com.peergreen.store.db.client.ejb.entity.api.User;

public interface IGroup {

    Group addGroup(String groupname);
    Group findGroup(String groupname);
    Collection<Group> collectGroups();
    Group updateGroup(String groupname);
    void deleteGroup(String groupname);
    Group addUser(User myUser);     
    Group deleteUserbyPseudo(String pseudo);
    Collection<User> retrieveUsers();
    Group addPetal(Petal petal);
    Group deletePetalById(int petalid);
    Collection<Petal> retrievePetals();

}