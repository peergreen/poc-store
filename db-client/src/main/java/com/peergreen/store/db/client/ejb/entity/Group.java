package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

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
	private List<IUser> users ;
	@ManyToMany(mappedBy="groupSet",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<IPetal> petals ;
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
	public List<IUser> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<IUser> users) {
		this.users = users;
	}
	/**
	 * @return the petals
	 */
	public List<IPetal> getPetals() {
		return petals;
	}
	/**
	 * @param petals the petals to set
	 */
	public void setPetals(List<IPetal> petals) {
		this.petals = petals;
	}

}
