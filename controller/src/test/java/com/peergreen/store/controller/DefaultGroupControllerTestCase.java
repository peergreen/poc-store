package com.peergreen.store.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.controller.impl.DefaultGroupController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultGroupControllerTestCase {

    private DefaultGroupController groupController;
    @Mock
    private ISessionGroup groupSession;
    @Mock
    private ISessionUser userSession;

    @BeforeClass
    public void oneTimeSetUp() {
        groupController = new DefaultGroupController();
        MockitoAnnotations.initMocks(this);
        groupController.bindGroupSession(groupSession);
        groupController.bindUserSession(userSession);
    }

    @Test
    public void testAddGroup() throws EntityAlreadyExistsException, NoEntityFoundException {
        String groupName = "myGroup";
        groupController.createGroup(groupName);
        verify(groupSession).addGroup(groupName);
    }

    public void shouldThrowExceptionGroupAlreadyExist() throws EntityAlreadyExistsException, NoEntityFoundException {
        //Given
        String groupName = "Administrator";
        //When
        groupController.createGroup(groupName);
        //then
        verify(groupSession).addGroup(groupName);
    }

    @Test
    public void testRemoveGroup() {
        String groupName = "myGroup";

        // verify groupSession.deleteGroup(...) is called
        groupController.deleteGroup(groupName);
        verify(groupSession).deleteGroup(groupName);
    }

    @Test
    public void testCollectUser() throws NoEntityFoundException {
        String groupName = "myGroup";

        // mock facade => always return empty collection
        Collection<User> list = new ArrayList<>();
        when(groupSession.collectUsers(groupName)).thenReturn(list);

        // verify groupSession.collectUsers(...) is called
        groupController.collectUsers(groupName);
        verify(groupSession).collectUsers(groupName);
    }

    @Test
    public void testAddUser() throws NoEntityFoundException {
        // user to add
        String pseudo = "toto";
        User user = new User();
        user.setPseudo(pseudo);

        String groupName = "myGroup";
        // entity instance before user add
        Group groupBefore = new Group();
        groupBefore.setGroupname(groupName);
        // entity instance after user add
        Group groupAfter = new Group();
        groupAfter.setGroupname(groupName);

        // add the user to groupAfter
        Set<User> list = new HashSet<User>();
        list.add(user);
        groupAfter.setUsers(list);

        // mock facade => always find group called groupName
        when(groupSession.findGroup(groupName)).thenReturn(groupBefore);

        // mock facade => always find user called pseudo
        when(userSession.findUserByPseudo(pseudo)).thenReturn(user);

        // mock facade => return a group
        // with a user list containing the user
        when(groupSession.addUser(groupBefore, user)).thenReturn(groupAfter);

        // verify groupSession.collectUsers(...) is called
        groupController.addUser(groupName, pseudo);
        verify(groupSession).addUser(groupBefore, user);
    }

    @Test
    public void testRemoveUser() throws NoEntityFoundException {
        // user to add
        String pseudo = "toto";
        User user = new User();
        user.setPseudo(pseudo);

        String groupName = "myGroup";
        // entity instance before user add
        Group groupBefore = new Group();
        groupBefore.setGroupname(groupName);
        // add the user to groupBefore
        Set<User> list = new HashSet<User>();
        list.add(user);
        groupBefore.setUsers(list);
        // entity instance after user add
        Group groupAfter = new Group();
        groupAfter.setGroupname(groupName);

        // mock facade => always find group called groupName
        when(groupSession.findGroup(groupName)).thenReturn(groupBefore);

        // mock facade => always find user called pseudo
        when(userSession.findUserByPseudo(pseudo)).thenReturn(user);

        // mock facades => always return group with empty collection
        when(userSession.findUserByPseudo(pseudo)).thenReturn(user);
        when(groupSession.removeUser(groupBefore, user)).thenReturn(groupAfter);

        // verify groupSession.collectUsers(...) is called
        groupController.removeUser(groupName, pseudo);
        verify(groupSession).removeUser(groupBefore, user);
    }

}
