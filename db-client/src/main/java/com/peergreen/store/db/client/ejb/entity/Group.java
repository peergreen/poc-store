package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Entity Bean representing in the database group of users
 */
@Entity
public class Group {

	@Id
	private String groupname;
	@ManyToMany(mappedBy="groupSet",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<User> users ;
	@ManyToMany(mappedBy="groupSet",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Petal> petals ;
	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}
	/**
	 * @param groupname the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	/**
	 * @return the petals
	 */
	public List<Petal> getPetals() {
		return petals;
	}
	/**
	 * @param petals the petals to set
	 */
	public void setPetals(List<Petal> petals) {
		this.petals = petals;
	}

}
