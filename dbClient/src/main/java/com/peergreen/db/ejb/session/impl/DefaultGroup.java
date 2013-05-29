package com.peergreen.db.ejb.session.impl;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.entity.Group;
import com.peergreen.db.ejb.entity.User;
import com.peergreen.db.ejb.session.IGroup;

public class DefaultGroup implements IGroup {

	public Group addGroup(String Groupname) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group findGroup(String Groupname) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Group> collectGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	public Group updateGroup(String Groupname) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteGroup(String Groupname) {
		// TODO Auto-generated method stub

	}

	public Group addUser(User myUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group deleteUserbyPseudo(String pseudo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<User> retrieveUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Group addFeature(Feature feature) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group deleteFeatureById(int Featureid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> retrieveFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

}
