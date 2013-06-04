package com.peergreen.store.db.client.ejb.entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

/**
 * Entity Bean representing in the database the user
 */
@Entity
public class User implements IUser {

	@Id
	private String pseudo;

	private String password;

	private String email;

	@JoinTable(name = "USERS_GROUPS_MAP",
			joinColumns = {@JoinColumn(name = "personnePseudo", referencedColumnName = "pseudo")},
			inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})
	private Set<IGroup> groupSet;

	/**
	 * Method to get the user's pseudonyme
	 * 
	 * @return the pseudonyme of the user
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Method to set the user's pseudonyme
	 * 
	 * @param pseudo the pseudonyme of the user to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Method to get the user's password
	 * 
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method to set the user's password
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Method to get the user's email
	 * 
	 * @return the email of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to set the user's email 
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Method to get the groups to which the user belongs
	 * 
	 * @return A Set containing all the groups to which the user belongs
	 */
	public Set<IGroup> getGroupSet() {
		return groupSet;
	}

	/**
	 * Method to add the user to new groups
	 * 
	 * @param groupSet A Set of new group to which the user is added
	 */
	public void setGroupSet(Set<IGroup> groupSet) {
		this.groupSet = groupSet;
	} 

}
