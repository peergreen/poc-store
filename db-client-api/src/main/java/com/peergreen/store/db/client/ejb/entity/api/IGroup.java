package com.peergreen.store.db.client.ejb.entity.api;

import java.util.Set;

/**
 * Interface defining an entity bean representing a group of users
 */
public interface IGroup {
	
	/**
	 * Method to retrieve the group's name
	 * 
	 * @return the groupname
	 */
	String getGroupname();
	
	/**
	 * Method to set the group's name
	 * 
	 * @param groupname the name to set
	 */
	void setGroupname(String groupname);

	/**
	 * Method to retrieve all the users that belongs to this group
	 * 
	 * @return Set of users of the group
	 */
	Set<IUser> getUsers();


	/**
	 * Method to add users into the group 
	 * 
	 * @param users Set of users to add into the group
	 */
	void setUsers(Set<IUser> users);


	/**
	 * Method to retrieve all the petals which the users of the group have 
	 * access
	 * 
	 * @return Set containing the petals attainable via the group instance
	 */
	Set<IPetal> getPetals();


	/**
	 * Method to add petals to the Set of petals which the users of the 
	 * group have access
	 * 
	 * @param petals Set of petals to add, for making it attainable via the group
	 * instance
	 */
	void setPetals(Set<IPetal> petals);


}
