package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;

public interface ISessionGroup {

    /**
     * Method to add a new group in the database.
     * The attributes users and petals are null when creating the group
     * 
     * @param groupName the name of the group to create
     * @return The new group
     */
    Group addGroup(String groupName);

    /**
     * Method to find a group in the database
     * 
     * @param groupName the group's name
     * @return the group with the name 'groupName'
     */
    Group findGroup(String groupName);

    /**
     * Method to change the name of the group 
     * 
     * @param oldGroupName the older name
     * @param groupName the new name
     * @return the group changed 
     */
    Group updateGroup(String oldGroupName, String groupName);

    /**
     * Method to delete the group with the name groupName
     * 
     * @param groupName the name of the group to delete
     */
    void deleteGroup(String groupName);

    /**
     * Method to add a user to the instance of Group 'group'
     * 
     * @param group the group to which add the user
     * @param myUser the user to add to the group 
     * 
     * @return A group with the new user
     */
    Group addUser(Group group, User myUser);  

    /**
     * Method to remove a user to the instance of Group 'group'
     * 
     * @param group the group to which remove the user
     * @param user the user to remove to the group
     * 
     * @return A group without the user deleted
     */
    Group removeUser(Group group, User user);

    /**
     * Method to collect the users which belongs to the group with the name 'groupName'
     * 
     * @param groupName the group's name
     * 
     * @return A collection of users wich belongs to the group
     */
    Collection<User> collectUsers(String groupName);

    /**
     * Method to add a new Petal to the list of petals that are accessible from the Group 'group'
     * 
     * @param group the group to which add a new petal 
     * @param petal the petal to make available from the Group 'group'
     * 
     * @return A new group with the new petal
     */
    Group addPetal(Group group, Petal petal);

    /**
     * Method to remove a Petal from the list of petals that are accessible from the Group 'group'
     * 
     * @param group the group to which remove the petal 
     * @param petal the petal to make inaccessible from the Group 'group'
     * @return
     */
    Group removePetal(Group group, Petal petal);

    /**
     * Method to collect the petals which are accessible from the Group 'group'
     * 
     * @param groupName the group's name
     * 
     * @return A collection of petals which are accessible from the group
     */
    Collection<Petal> collectPetals(String groupName);

}
