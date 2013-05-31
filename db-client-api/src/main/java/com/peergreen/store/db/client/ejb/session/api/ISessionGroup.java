package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

public interface ISessionGroup {
    IGroup addGroup(String groupName);
    IGroup findGroup(String groupName);
    Collection<IGroup> collectGroups();
    IGroup updateGroup(String groupName);
    void deleteGroup(String groupName);
    IGroup addUser(IUser myUser);     
    IGroup deleteUserbyPseudo(String pseudo);
    Collection<IUser> retrieveUsers();
    IGroup addPetal(IPetal petal);
    IGroup deletePetalById(int petalId);
    Collection<IPetal> collectPetals();

}
