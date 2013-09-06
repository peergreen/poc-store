package com.peergreen.store.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.controller.impl.DefaultGroupController;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultGroupControllerTestCase {

    private DefaultGroupController groupController;
    @Mock
    private ISessionGroup groupSession;
    @Mock
    private ISessionUser userSession;
    @Mock
    private ISessionVendor vendorSession; 
    @Mock
    private ISessionPetal petalSession;

    @BeforeMethod
    public void oneTimeSetUp() {
        groupController = new DefaultGroupController();
        MockitoAnnotations.initMocks(this);
        groupController.bindGroupSession(groupSession);
        groupController.bindUserSession(userSession);
        groupController.bindPetalSession(petalSession);
        groupController.bindVendorSession(vendorSession);
    }

    @Test
    public void testAddGroup() throws EntityAlreadyExistsException, NoEntityFoundException {
        String groupName = "myGroup";
        groupController.createGroup(groupName);
        verify(groupSession).addGroup(groupName);
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionGroupAlreadyExist() throws EntityAlreadyExistsException{
        //Given
        String groupName = "Administrator";
        when(groupSession.addGroup(groupName)).thenThrow(new EntityAlreadyExistsException());
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
    
  @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectUserForGroupNonExistent() throws NoEntityFoundException {
        String groupName = "myGroup";

        // mock facade => always return empty collection
        when(groupSession.collectUsers(groupName)).thenThrow(new NoEntityFoundException());

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
        Group groupBefore = new Group(groupName);
        // entity instance after user add
        Group groupAfter = new Group(groupName);

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
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testAddUserWhenGroupNonExistent() throws NoEntityFoundException {
       
        //Given : user to add and group to which add new user 
        String pseudo = "toto";
        User user = new User();
        user.setPseudo(pseudo);

        String groupName = "myGroup";
        // entity instance before user add
        Group groupBefore = new Group(groupName);
       
        when(groupSession.findGroup(groupName)).thenReturn(groupBefore);
        when(userSession.findUserByPseudo(pseudo)).thenReturn(user);
        // mock facade => throw Exception when call addUser from groupSession 
        when(groupSession.addUser(groupBefore, user)).thenThrow(new NoEntityFoundException());
           
        // When 
        groupController.addUser(groupName, pseudo);
        //Then 
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testRemoveUserFromGroupInexistent() throws NoEntityFoundException {
        // user to add
        String pseudo = "toto";
        User user = new User();
        user.setPseudo(pseudo);

        String groupName = "myGroup";
        // entity instance before user add
        Group groupBefore = new Group(groupName);
     

        // mock facade => always find group called groupName
        when(groupSession.findGroup(groupName)).thenReturn(groupBefore);

        // mock facade => always find user called pseudo
        when(userSession.findUserByPseudo(pseudo)).thenReturn(user);

        when(groupSession.removeUser(groupBefore, user)).thenThrow(new NoEntityFoundException());

        // verify groupSession.collectUsers(...) is called
        groupController.removeUser(groupName, pseudo);
    }
    

    @Test
    public void testRemoveUser() throws NoEntityFoundException {
        // user to add
        String pseudo = "toto";
        User user = new User();
        user.setPseudo(pseudo);

        String groupName = "myGroup";
        // entity instance before user add
        Group groupBefore = new Group(groupName);
        // add the user to groupBefore
        Set<User> list = new HashSet<User>();
        list.add(user);
        groupBefore.setUsers(list);
        // entity instance after user add
        Group groupAfter = new Group(groupName);

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
    
    
    @Test
    public void shouldGiveAccessToNewPetal() throws NoEntityFoundException{
        //Given 
        String groupName = "myGroup";
        String vendorName = "Peergreen";
        String artifactId = "JPA"; 
        String version = "1.0";
        
        Group group= new Group(groupName);
        when(groupSession.findGroup(groupName)).thenReturn(group);
        
        Vendor vendor = new Vendor(vendorName,"");
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        
        Petal petal = new Petal(vendor, artifactId, version, null, "", null, null, Origin.LOCAL);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        //When
        groupController.giveAccessToPetal(groupName, vendorName, artifactId, version);
        //Then 
        verify(groupSession).addPetal(group, petal);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenGiveAccesPetalForGroupInexistent() throws NoEntityFoundException{
        //Given 
        String groupName = "myGroup";
        String vendorName = "Peergreen";
        String artifactId = "JPA"; 
        String version = "1.0";
                
        when(groupSession.addPetal(any(Group.class), any(Petal.class))).thenThrow(new NoEntityFoundException());
        //When
        groupController.giveAccessToPetal(groupName, vendorName, artifactId, version);

    }
    
    
    @Test
    public void shouldRemoveAccessToPetal() throws NoEntityFoundException{
        //Given 
        String groupName = "myGroup";
        String vendorName = "Peergreen";
        String artifactId = "JPA"; 
        String version = "1.0";
        
        Group group= new Group(groupName);
        when(groupSession.findGroup(groupName)).thenReturn(group);
        
        Vendor vendor = new Vendor(vendorName, "");
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        
        Petal petal = new Petal(vendor, artifactId, version, null, "", null, null, Origin.LOCAL);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        //When
        groupController.removeAccessToPetal(groupName, vendorName, artifactId, version);
        //Then 
        verify(groupSession).removePetal(group, petal);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenRemoveAccesPetalForGroupInexistent() throws NoEntityFoundException{
        //Given 
        String groupName = "myGroup";
        String vendorName = "Peergreen";
        String artifactId = "JPA"; 
        String version = "1.0";
        
        Group group= new Group(groupName);
        when(groupSession.findGroup(groupName)).thenReturn(null);
        
        Vendor vendor = new Vendor(vendorName, "");
        when(vendorSession.findVendor(vendorName)).thenReturn(null);
        
        Petal petal = new Petal(vendor, artifactId, version, null, "", null, null, Origin.LOCAL);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(null);
        
        when(groupSession.removePetal(any(Group.class), any(Petal.class))).thenThrow(new NoEntityFoundException());
        //When
        groupController.removeAccessToPetal(groupName, vendorName, artifactId, version);
       
    }
    
    @Test
    public void shouldCollectAllPetalsAccessible() throws NoEntityFoundException{
    
        //Given 
        String groupName = "myGroup";
        String vendorName = "Peergreen";
        String artifactId = "JPA"; 
        String version = "1.0";
        
        Vendor vendor = new Vendor(vendorName, "");
        
        Petal petal = new Petal(vendor, artifactId, version, null, "", null, null, Origin.LOCAL);
        Collection<Petal> petals = new HashSet<>();
        petals.add(petal);
        when(groupSession.collectPetals(groupName)).thenReturn(petals); 
        
        //when
       Collection<Petal> result =  groupController.collectPetals(groupName);
       
       //Then
       Assert.assertEquals(petals, result);
        
        
    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectAllPetalsFor() throws NoEntityFoundException{
    
        //Given 
        String groupName = "myGroup";
        String vendorName = "Peergreen";
        String artifactId = "JPA"; 
        String version = "1.0";
       
        Vendor vendor = new Vendor(vendorName, "");
        
        Petal petal = new Petal(vendor, artifactId, version, null, "", null, null, Origin.LOCAL);
        Collection<Petal> petals = new HashSet<>();
        petals.add(petal);
        when(groupSession.collectPetals(groupName)).thenThrow(new NoEntityFoundException()); 
        
        //when
         groupController.collectPetals(groupName);
       
   
        
        
    }
    
    
}
