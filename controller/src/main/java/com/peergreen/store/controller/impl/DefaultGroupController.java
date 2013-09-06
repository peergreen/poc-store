package com.peergreen.store.controller.impl;

import java.util.Collection;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;

import com.peergreen.store.controller.IGroupController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

/**
 * Class defining all group related operations.
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
    private ISessionPetal petalSession;
    private ISessionVendor vendorSession;
    private static Log logger = LogFactory.getLog(DefaultGroupController.class);

    /**
     * Method to add a new group in database.
     *
     * @param groupName group's name
     * @return created group instance
     * @throws NoEntityFoundException
     * @throws EntityAlreadyExistsException
     */
    @Override
    public final Group createGroup(String groupName)
            throws  EntityAlreadyExistsException {

        try {
            return groupSession.addGroup(groupName);
        } catch (EntityAlreadyExistsException e) {
            logger.warn("Group with name " + groupName
                    + " already exists in database.", e);
            throw new EntityAlreadyExistsException(e);
        }
    }

    /**
     * Method to remove a group from the database.
     *
     * @param groupName group's name
     * @return Group instance deleted or null if the group can't be deleted
     */
    @Override
    public final Group deleteGroup(String groupName) {
        return groupSession.deleteGroup(groupName);
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
    public final Group addUser(String groupName, String pseudo)
            throws NoEntityFoundException {
        Group group = groupSession.findGroup(groupName);
        User user = userSession.findUserByPseudo(pseudo);
        try {
            return groupSession.addUser(group, user);
        } catch (NoEntityFoundException e) {
            logger.warn("Group " + groupName + " and/or user "
                    + pseudo + "cannot be found.", e);
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
    public final Group removeUser(String groupName, String pseudo)
            throws NoEntityFoundException {

        Group group = groupSession.findGroup(groupName);
        User user = userSession.findUserByPseudo(pseudo);
        try {
            return groupSession.removeUser(group, user);
        } catch (NoEntityFoundException e) {
            logger.warn("Group " + groupName + " and/or user "
                    + pseudo + "cannot be found.", e);
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
    public final Collection<User> collectUsers(String groupName)
            throws NoEntityFoundException {

        try {
            return groupSession.collectUsers(groupName);
        } catch (NoEntityFoundException e) {
            logger.warn("Group " + groupName + " cannot be found.", e);
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
    public final Collection<Petal> collectPetals(String groupName)
            throws NoEntityFoundException {

        try {
            return groupSession.collectPetals(groupName);
        } catch (NoEntityFoundException e) {
            logger.warn("Group " + groupName + " cannot be found.", e);
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to give access to a petal for the specified group.
     *
     * @param groupName group's name
     * @param vendorName petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return modified group
     * @throws NoEntityFoundException
     */
    @Override
    public final Group giveAccessToPetal(
            String groupName,
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException {

        Group group = groupSession.findGroup(groupName);
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        try {
            return groupSession.addPetal(group, petal);
        } catch (NoEntityFoundException e) {
            logger.warn("Group " + groupName + " and/or vendor "
                    + vendorName + " and/or petal "
                    + vendorName + ":" + artifactId + ":" + version
                    + " cannot be found.", e);
            throw new NoEntityFoundException(e);
        }
    }

    /**
     * Method to remove access to a petal from the specified group.
     *
     * @param groupName group's name
     * @param vendorName petal's vendor name
     * @param artifactId petal's artifactId
     * @param version petal's version
     * @return modified group
     * @throws NoEntityFoundException
     */
    @Override
    public final Group removeAccessToPetal(
            String groupName,
            String vendorName,
            String artifactId,
            String version) throws NoEntityFoundException {

        Group group = groupSession.findGroup(groupName);
        Vendor vendor = vendorSession.findVendor(vendorName);
        Petal petal = petalSession.findPetal(vendor, artifactId, version);
        try {
            return groupSession.removePetal(group, petal);
        } catch (NoEntityFoundException e) {
            logger.warn("Group " + groupName + " and/or vendor "
                    + vendorName + " and/or petal "
                    + vendorName + ":" + artifactId + ":" + version
                    + " cannot be found.", e);
            throw new NoEntityFoundException();
        }
    }

    /**
     * Method to set ISessionGroup instance to use.
     *
     * @param session the ISessionGroup to set
     */
    @Bind
    public final void bindGroupSession(ISessionGroup session) {
        this.groupSession = session;
    }

    /**
     * Method to set ISessionUser instance to use.
     *
     * @param session the ISessionUser to set
     */
    @Bind
    public final void bindUserSession(ISessionUser session) {
        this.userSession = session;
    }

    /**
     * Method to set ISessionVendor instance to use.
     *
     * @param session the ISessionVendor to set
     */
    @Bind
    public final void bindVendorSession(ISessionVendor session) {
        this.vendorSession = session;
    }

    /**
     * Method to set ISessionPetal instance to use.
     *
     * @param session the ISessionPetal to set
     */
    @Bind
    public final void bindPetalSession(ISessionPetal session) {
        this.petalSession = session;
    }

}
