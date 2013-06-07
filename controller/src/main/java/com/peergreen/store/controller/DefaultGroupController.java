package com.peergreen.store.controller;

import java.util.Collection;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

/**
 * Class defining all group related operations:
 * <ul>
 *      <li>Create group on database</li>
 *      <li>Modify existing group on database</li>
 *      <li>Remove group from database</li>
 *      <li>Retrieve group's member list</li>
 *      <li>Add a user to group</li>
 *      <li>Remove user from group</li>
 * </ul>
 * 
 */
@Component
@Instantiate
@Provides
public class DefaultGroupController implements IGroupController {

    private ISessionGroup groupSession;
    private ISessionUser userSession;
    
    /**
     * Method to add a new group in database.
     * 
     * @param groupName group's name
     */
    @Override
    public void addGroup(String groupName) {
        groupSession.addGroup(groupName);
    }

    /**
     * Method to modify an existing Group entity.
     * 
     * @param groupName old group's name
     * @param groupName new group's name
     * @return updated group
     */
    @Override
    public Group modifyGroup(String oldGroupName, String newGroupName) {
        return groupSession.updateGroup(oldGroupName, newGroupName);
    }

    /**
     * Method to remove a group from the database.
     * 
     * @param groupName group's name
     */
    @Override
    public void removeGroup(String groupName) {
        groupSession.deleteGroup(groupName);
    }

    /**
     * Method to collect all the group's users.
     * 
     * @param groupName group's name
     * @return list of all the group's users
     */
    @Override
    public Collection<User> collectUsers(String groupName) {
        return groupSession.collectUsers(groupName);
    }

    /**
     * Method to add a user to a group.
     * 
     * @param pseudo user's pseudo
     * @param groupName group's name
     * @return updated group
     */
    @Override
    public Group addUser(String groupName, String pseudo) {
        Group group = groupSession.findGroup(groupName);
        User user = userSession.findUserByPseudo(pseudo);
        return groupSession.addUser(group, user);
    }

    /**
     * Method to remove a user from a group.
     *
     * @param groupName group's name
     * @param pseudo user's pseudo
     * @return updated group
     */
    @Override
    public Group removeUser(String groupName, String pseudo) {
        Group group = groupSession.findGroup(groupName);
        User user = userSession.findUserByPseudo(pseudo);
        return groupSession.removeUser(group, user);
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
