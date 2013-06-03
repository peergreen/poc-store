package com.peergreen.store.db.client.ejb.entity.api;


import java.util.List;

/**
 * Interface defining an entity bean representing the user
 */

public interface IUser {
	
	/**
	 * Method to get the user's pseudonyme
	 * 
	 * @return the pseudonyme of the user
	 */
	String getPseudo();
	
	/**
	 * Method to set the user's pseudonyme
	 * 
	 * @param the pseudonyme of the user to set
	 */
	void setPseudo(String pseudo);

	/**
	 * Method to get the user's password
	 * 
	 * @return the password of the user
	 */
	String getPassword();


	/**
	 * Method to set the user's password
	 * 
	 * @param the password to set
	 */
	void setPassword(String password) ;


	/**
	 * Method to get the user's email
	 * 
	 * @return the email of the user
	 */
	String getEmail();


	/**
	 * Method to set the user's email 
	 * 
	 * @param the email to set
	 */
	void setEmail(String email);


	/**
	 * Method to get the groups to which the user belongs
	 * 
	 * @return A list containing all the groups to which the user belongs
	 */
	List<IGroup> getGroupSet();


	/**
	 * Method to add the user to new groups
	 * 
	 * @param A List of new group to which the user is added
	 */
	void setGroupSet(List<IGroup> groupSet) ;

}
