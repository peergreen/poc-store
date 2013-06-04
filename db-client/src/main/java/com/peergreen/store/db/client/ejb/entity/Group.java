package com.peergreen.store.db.client.ejb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

/**
 * Entity Bean representing in the database group of users
 */
@Entity
public class Group implements IGroup{

	@Id
	private String groupname;

	@ManyToMany(mappedBy="groupSet",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<IUser> users ;

	@ManyToMany(mappedBy="groupSet",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Set<IPetal> petals ;

	/**
	 * Method to retrieve the group's name
	 * 
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}

	/**
	 * Method to set the group's name
	 * 
	 * @param groupname the name to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	/**
	 * Method to retrieve all the users that belongs to this group
	 * 
	 * @return Set of users of the group
	 */
	public Set<IUser> getUsers() {
		return users;
	}

	/**
	 * Method to add users into the group 
	 * 
	 * @param users Set of users to add into the group
	 */
	public void setUsers(Set<IUser> users) {
		this.users = users;
	}

	/**
	 * Method to retrieve all the petals which the users of the group have 
	 * access
	 * 
	 * @return Set containing the petals attainable via the group instance
	 */
	public Set<IPetal> getPetals() {
		return petals;
	}

	/**
	 * Method to add petals to the Set of petals which the users of the 
	 * group have access
	 * 
	 * @param petals Set of petals to add, for making it attainable via the group
	 * instance
	 */
	public void setPetals(Set<IPetal> petals) {
		this.petals = petals;
	}

}
