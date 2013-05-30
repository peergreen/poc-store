package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.Group;
import com.peergreen.store.db.client.ejb.entity.api.Petal;
import com.peergreen.store.db.client.ejb.entity.api.User;

public interface IUser {

    User addUser(String pseudo, String password, String email);
    User findUserByPseudo(String pseudo);
    Collection <User> collectUsers();
    void deleteUserbyPseudo(String pseudo);
    void deleteUser(User myUser);
    User updateUser(String pseudo, String password, String email);
    User addGroup(Group group);
    User deleteGroup(Group group);
    Collection<Group> retrieveGroups();
    Collection<Petal> retrievePetals();

}