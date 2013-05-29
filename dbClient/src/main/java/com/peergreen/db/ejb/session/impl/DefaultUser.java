package com.peergreen.db.ejb.session.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.entity.Group;
import com.peergreen.db.ejb.entity.User;
import com.peergreen.db.ejb.session.IUser;

@Stateless
public class DefaultUser implements IUser {

	public User addUser(String Pseudo, String Password, String Email) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findUserByPseudo(String Pseudo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<User> collectUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUserbyPseudo(String Pseudo) {
		// TODO Auto-generated method stub
		
	}

	public void deleteUser(User myUser) {
		// TODO Auto-generated method stub
		
	}

	public User updateUser(String Pseudo, String Password, String Email) {
		// TODO Auto-generated method stub
		return null;
	}

	public User addGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	public User deleteGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Group> retrieveGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> retrieveFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

}
