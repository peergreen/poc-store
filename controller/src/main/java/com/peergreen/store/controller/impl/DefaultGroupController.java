package com.peergreen.store.controller.impl;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.controller.IGroupController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Class defining all group related operations:
 * <ul>
 *      <li>Create group on database</li>
 *      <li>Remove group from database</li>
 *      <li>Add a user to group</li>
 *      <li>Remove user from group</li>
 *      <li>Retrieve all users member of this group</li>
 *      <li>Retrieve all petals to those this group has access to</li>
 * </ul>
 * 
 */
@Component
@Instantiate
@Provides
public class DefaultGroupController implements IGroupController {

    private ISessionGroup groupSession;
    private ISessionUser userSession;
    private static Logger theLogger = Logger.getLogger(DefaultGroupController.class.getName());
    
    /**
     * Method to add a new group in database.
     * 
     * @param groupName group's name
     * @return created group instance
     * @throws NoEntityFoundException 
     * @throws EntityAlreadyExistsException 
     */
    @Override
    public Group createGroup(String groupName) throws  EntityAlreadyExistsException, NoEntityFoundException{
        try {
            return groupSession.addGroup(groupName);
        } catch (EntityAlreadyExistsException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new EntityAlreadyExistsException(e);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to remove a group from the database.
     * 
     * @param groupName group's name
     */
    @Override
    public void deleteGroup(String groupName) {
        groupSession.deleteGroup(groupName);
    }

    /**
     * Method to add a user to a group.
     * 
     * @param pseudo user's pseudo
     * @param groupName group's name
     * @return updated group
     * @throws NoEntityFoundException 
     */
    @Override
    public Group addUser(String groupName, String pseudo) throws NoEntityFoundException {
        Group group = groupSession.findGroup(groupName);
        User user = userSession.findUserByPseudo(pseudo);
        try {
            return groupSession.addUser(group, user);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to remove a user from a group.
     *
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     * @throws NoEntityFoundException 
     */
    @Override
    public Group removeUser(String groupName, String pseudo) throws NoEntityFoundException {
        Group group = groupSession.findGroup(groupName);
        User user = userSession.findUserByPseudo(pseudo);
        try {
            return groupSession.removeUser(group, user);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to collect all users member of a group.<br />
     * Throws NoEntityFoundException if specified group does not exist.
     * 
     * @param groupName group name
     * @return list of all users member of the specified group
     * @throw NoEntityFoundException
     */
    @Override
    public Collection<User> collectUsers(String groupName) throws NoEntityFoundException {
        try {
            return groupSession.collectUsers(groupName);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to collect all accessible petals for a group.<br />
     * Throws NoEntityFoundException if specified group does not exist.
     * 
     * @param groupName group name
     * @return list of all accessible petals for the group
     * @throw NoEntityFoundException
     */
    @Override
    public Collection<Petal> collectPetals(String groupName) throws NoEntityFoundException {
        try {
            return groupSession.collectPetals(groupName);
        } catch (NoEntityFoundException e) {
            theLogger.log(Level.SEVERE, e.getMessage());
            throw new NoEntityFoundException(e);
        }
    }

    @Bind
    public void bindGroupSession(ISessionGroup groupSession) {
        this.groupSession = groupSession;
    }

    @Bind
    public void bindUserSession(ISessionUser userSession) {
        this.userSession = userSession;
    }

}
