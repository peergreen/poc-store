package com.peergreen.store.db.client.ejb.session;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;

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