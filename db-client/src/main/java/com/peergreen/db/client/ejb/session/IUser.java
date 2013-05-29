package com.peergreen.db.ejb.session;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.entity.Group;
import com.peergreen.db.ejb.entity.User;

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
    Collection<Feature> retrieveFeatures();

}