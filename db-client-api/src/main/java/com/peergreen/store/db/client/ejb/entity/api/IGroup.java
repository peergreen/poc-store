package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a group of users
 */
public interface IGroup {

	/**
	 * Method to retrieve all the users that belongs to this group
	 * 
	 * @return List of users of the group
	 */
	List<IUser> getUsers();


	/**
	 * Method to add users into the group 
	 * 
	 * @param List of users to add into the group
	 */
	void setUsers(List<IUser> users);


	/**
	 * Method to retrieve all the petals which the users of the group have 
	 * access
	 * 
	 * @return List containing the petals attainable via the group instance
	 */
	List<IPetal> getPetals();


	/**
	 * Method to add petals to the list of petals which the users of the 
	 * group have access
	 * 
	 * @param List of petals to add, for making it attainable via the group
	 * instance
	 */
	void setPetals(List<IPetal> petals);


}
