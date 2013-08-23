package com.peergreen.store.controller;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Interface defining all group related operations:
 * <ul>
 *      <li>Create group on database</li>
 *      <li>Modify existing group on database</li>
 *      <li>Remove group from database</li>
 *      <li>Retrieve group's member list</li>
 *      <li>Add a user to group</li>
 *      <li>Remove user from group</li>
 *      <li>Retrieve all users member of this group</li>
 *      <li>Retrieve all petals to those this group has access</li>
 * </ul>
 * 
 */
public interface IGroupController {

    /**
     * Method to add a new group in database.
     * Throws {@link EntityAlreadyExistsException} when the group already exist.
     * @param groupName group's name
     * @return created group instance
     * @throws EntityAlreadyExistsException 
     */
    Group createGroup(String groupName) throws EntityAlreadyExistsException;

    /**
     * Method to remove a group from the database.
     * 
     * @param groupName group's name
     * @return Group instance deleted or null if the group can't be deleted
     */
    Group deleteGroup(String groupName);

    /**
     * Method to collect all the group's users.
     * 
     * @param groupName group's name
     * @return list of all the group's users
     * @throws NoEntityFoundException 
     */
    Collection<User> collectUsers(String groupName) throws NoEntityFoundException;

    /**
     * Method to add a user to a group.
     * 
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     * @throws NoEntityFoundException 
     */
    Group addUser(String groupName, String pseudo) throws NoEntityFoundException;

    /**
     * Method to remove a user from a group.
     * 
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     * @throws NoEntityFoundException 
     */
    Group removeUser(String groupName, String pseudo) throws NoEntityFoundException;

    /**
     * Method to collect all petals accessible for the members of a group.<br />
     * Throws NoEntityFoundException if specified group does not exist.
     * 
     * @param name group name
     * @return list of all petals accessible for members of the specified group
     * @throw NoEntityFoundException
     */
    Collection<Petal> collectPetals(String name) throws NoEntityFoundException;

    Group giveAccessToPetal(String groupName, String vendorName, String artifactId, String version)
            throws NoEntityFoundException;

    Group removeAccessToPetal(String groupName, String vendorName, String artifactId, String version)
            throws NoEntityFoundException;

}