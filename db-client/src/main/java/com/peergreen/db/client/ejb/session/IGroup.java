package com.peergreen.db.ejb.session;

import java.util.Collection;

import com.peergreen.db.ejb.entity.*;

public interface IGroup {

    Group addGroup(String groupname);
    Group findGroup(String groupname);
    Collection<Group> collectGroups();
    Group updateGroup(String groupname);
    void deleteGroup(String groupname);
    Group addUser(User myUser);     
    Group deleteUserbyPseudo(String pseudo);
    Collection<User> retrieveUsers();
    Group addFeature(Feature feature);
    Group deleteFeatureById(int featureid);
    Collection<Feature> retrieveFeatures();

}