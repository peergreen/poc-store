package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


/**
 * Interface defining an entity session to manage the entity Group.
 * <ul>
 *      <li>Create a group in a database</li>
 *      <li>Find a group from the database</li>
 *      <li>Modify a group</li>
 *      <li>Remove a group from the database</li>
 *      <li>Add a user to a group</li>
 *      <li>Remove a user from a group</li>
 *      <li>Collect all the users of a group</li>
 *      <li>Add a petal to those which are accessible from a group</li>
 *      <li>Remove an access to a petal from a group</li>
 *      <li>Collect all the petals which the group have access</li>
 *      <li>Collect all existing groups on database</li>
 * </ul>
 * 
 */
public interface ISessionGroup {

    /**
     * <p>
     * Method to add a new group in the database.<br />
     * List of allowed petals is empty when creating the group
     *  but one user is automatically added to users list: Administrator.
     * </p>
     * <p>
     * Throws {@link EntityAlreadyExistsException}
     *  when an entity with same id already exists in the database.<br />
     * Throws {@link NoEntityFoundException} if user Administrator doesn't already exist.
     * </p>
     * 
     * @param groupName group name to create
     * @return group created
     * @throws EntityAlreadyExistsException
     * @throws NoEntityFoundException
     */
    Group addGroup(String groupName) throws EntityAlreadyExistsException, NoEntityFoundException;

    /**
     * Method to find a group in the database.
     *  
     * @param groupName group's name
     * @return found group with the name 'groupName', {@literal null} other wise
     */
    Group findGroup(String groupName);

    /**
     * Method to delete the group thanks to its name.<br />
     * Throws an IllegalArgumentException if the entity to remove
     *  doesn't exist in the database.
     * 
     * @param groupName the name of the group to delete
     */
    void deleteGroup(String groupName);

    /**
     * Method to add a user to a group.
     * 
     * @param group group to which add the user
     * @param myUser user to add to the group 
     * @return modified Group instance
     * @throws NoEntityFoundException
     */
    Group addUser(Group group, User myUser) throws NoEntityFoundException;  

    /**
     * Method to remove a user from a group.
     * 
     * @param group group from which remove the user
     * @param user user to remove from the group
     * @return modified group
     * @throws NoEntityFoundException
     */
    Group removeUser(Group group, User user) throws NoEntityFoundException;

    /**
     * Method to collect the users which belong to a specified group.<br />
     * Throws {@link NoEntityFoundException} when the group doesn't exist.
     * 
     * @param groupName group's name
     * @return collection of users which belong to the group
     * @exception NoEntityFoundException
     */
    Collection<User> collectUsers(String groupName) throws NoEntityFoundException;

    /**
     * Method to make a petal accessible to all the members of a group.
     * 
     * @param group group to which grant access to the petal 
     * @param petal petal to make available for the group
     * @return modified Group instance (updated list of accessible petals)
     * @throws NoEntityFoundException
     */
    Group addPetal(Group group, Petal petal) throws NoEntityFoundException;

    /**
     * Method to remove a Petal from the list of petals that are accessible for a group
     * 
     * @param group group to which remove the petal 
     * @param petal petal to make inaccessible for the Group
     * @return modified Group instance (updated list of accessible petals)
     * @throws NoEntityFoundException
     */
    Group removePetal(Group group, Petal petal)throws NoEntityFoundException;

    /**
     * Method to collect petals which are accessible for a specified group.<br />
     * Throws {@link NoEntityFoundException} if the group doesn't exist.
     * 
     * @param groupName group name
     * @return collection of petals which are accessible for the group
     * @throws NoEntityFoundException
     */
    Collection<Petal> collectPetals(String groupName) throws NoEntityFoundException;

    /**
     * Method to collect all existing groups in database.
     * 
     * @return groups list
     */
    Collection<Group> collectGroups();

}
