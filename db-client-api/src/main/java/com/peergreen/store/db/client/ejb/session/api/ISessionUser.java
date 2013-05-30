package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

public interface ISessionUser {
    
    IUser addUser(String pseudo, String password, String email);
    IUser findUserByPseudo(String pseudo);
    Collection <IUser> collectUsers();
    void deleteUserbyPseudo(String pseudo);
    void deleteUser(IUser myUser);
    IUser updateUser(String pseudo, String password, String email);
    IUser addGroup(IGroup group);
    IUser deleteGroup(IGroup group);
    Collection<IGroup> retrieveGroups();
    Collection<IPetal> retrievePetals();

}
