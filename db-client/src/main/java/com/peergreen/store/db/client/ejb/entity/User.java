package com.peergreen.store.db.client.ejb.entity;

import java.util.Collection;
import java.util.HashSet;


import javax.persistence.*;

/**
 * Entity Bean for representing the user in a database 
 */

@Entity
public class User {

	@Id
	private String pseudo;
	private String password;
	private String email;
	@JoinTable(name = "USERS_GROUPS_MAP",
	joinColumns = {@JoinColumn(name = "personnePseudo", referencedColumnName = "pseudo")},
inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})
	private Collection<Group> groupSet = new HashSet<Group>(); 
	/**
	 * Method for retrieve the pseudonyme of the user
	 * 
	 * 
	 * @return the user pseudonyme
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Method for give the mail of the user
	 * 
	 * 
	 * @return nothing
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Method for retrieve the password of the user
	 * 
	 * 
	 * @return the user password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Method for give the password of the user
	 * 
	 * 
	 * @return nothing
	 */
	public void setPassword(String Password) {
		this.password = Password;
	}
	/**
	 * Method for retrieve the mail of the user
	 * 
	 *
	 * @return the user mail 
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Method for give the mail of the user
	 * @return nothing 
	 */
	public void setEmail(String Email) {
		this.email = Email;
	}
	
	/**
	 * Method for retrieve groups to which the user belongs
	 * 
	 * 
	 * @return all the groups of the user 
	 */
	public Collection<Group> getGroupSet() {
		return groupSet;
	}
	
	/**
	 * Method for specify all the groups to which the user belongs
	 * 
	 * 
	 * @return nothing
	 */
	public void setGroupSet(Collection<Group> groupSet) {
		this.groupSet = groupSet;
	}

	

}
