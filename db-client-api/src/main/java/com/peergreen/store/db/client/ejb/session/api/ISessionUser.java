package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;

public interface ISessionUser {
    
    User addUser(String pseudo, String password, String email);
    User findUserByPseudo(String pseudo);
    Collection <User> collectUsers();
    void deleteUserbyPseudo(String pseudo);
    void deleteUser(User myUser);
    User updateUser(String pseudo, String password, String email);
    User addGroup(Group group);
    User deleteGroup(Group group);
    Collection<Group> collectGroups(String pseudo);
    Collection<Petal> collectPetals(String pseudo);

}
