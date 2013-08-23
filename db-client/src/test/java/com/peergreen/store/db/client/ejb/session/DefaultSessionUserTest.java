package com.peergreen.store.db.client.ejb.session;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionUserTest {

    private DefaultSessionUser sessionUser;
    private String pseudo;
    private String password;
    private String email;
    private String queryString;

    @Mock
    private EntityManager entityManager;
    @Mock
    private Group mockgroup;
    @Mock
    private Query query;
    @Mock
    private User mockuser;
    @Mock
    private Set<Group> groups;
    @Mock
    private ISessionGroup sessionGroup;
    @Mock
    private Iterator<Group> itGroup;

    private Group groupArray[];

    private List<User> userList;
    @Mock
    private Set<User> userSet;

    ArgumentCaptor<User> userArgument;
    ArgumentCaptor<String> value;
    ArgumentCaptor<Group> groupArgument;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionUser = new DefaultSessionUser();
        sessionUser.setEntityManager(entityManager);
        sessionUser.setGroupSession(sessionGroup);
        
        pseudo = "toto";
        password = "pwd";
        email = "toto@peergreen.com";
        queryString = "User.findAll";
        groupArray = new Group[1];
        groupArray[0] = mockgroup;
        userArgument = ArgumentCaptor.forClass(User.class);
        groupArgument = ArgumentCaptor.forClass(Group.class);
        value = ArgumentCaptor.forClass(String.class);
        userList = new ArrayList<User>();
        
        when(sessionGroup.findGroup(anyString())).thenReturn(mockgroup);
        when(mockuser.getGroupSet()).thenReturn(groups);
        when(groups.iterator()).thenReturn(itGroup);
    }

    @Test
    public void shouldAddUserNonExistent() throws EntityAlreadyExistsException {
        //When 
        sessionUser.addUser(pseudo, password, email);
        
        //Then
        verify(entityManager).persist(userArgument.capture());
        Assert.assertEquals(pseudo, userArgument.getValue().getPseudo());
        Assert.assertEquals(password, userArgument.getValue().getPassword());
        Assert.assertEquals(email, userArgument.getValue().getEmail());
    }

    @Test
    public void shouldUpdateUserPassword() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(mockuser);
        String password1 = "pwdbis";
        //When
        sessionUser.updatePassword(mockuser, password1);
        //Then

        verify(mockuser).setPassword(value.capture());
        Assert.assertEquals(password1, value.getValue());

        when(mockuser.getPassword()).thenReturn(password1);

        verify(entityManager).merge(userArgument.capture());
        Assert.assertEquals(password1, userArgument.getValue().getPassword());
    }
    
    @Test(expectedExceptions= NoEntityFoundException.class)
    public void shouldThrowsExceptionWhenUpdateUserPassword() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(null);
        String password1 = "pwdbis";
        //When
        sessionUser.updatePassword(mockuser, password1);
        //Then throws new EntityNotFoundException
    }
    
    @Test
    public void shouldUpdateUserMail() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(mockuser);
        String email1 = "titi@peergreen.com";
        //When
        sessionUser.updateMail(mockuser, email1);
        //Then

        verify(mockuser).setEmail(value.capture());
        Assert.assertEquals(email1, value.getValue());

        when(mockuser.getEmail()).thenReturn(email1);

        verify(entityManager).merge(userArgument.capture());
        Assert.assertEquals(email1, userArgument.getValue().getEmail());
    }
    
    @Test(expectedExceptions= NoEntityFoundException.class)
    public void shouldThrowsExceptionWhenUpdateUserMail() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(null);
        String email1 = "titi@peergreen.com";
        //When
        sessionUser.updateMail(mockuser, email1);
        //Then throws new EntityNotFoundException
    }

    @Test
    public void shouldRemoveUserByPseudo() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(mockuser);
        when(itGroup.hasNext()).thenReturn(true,false);

        //When
       User result = sessionUser.removeUserbyPseudo(pseudo);
        //Then
        verify(entityManager).find(eq(User.class), value.capture());
        Assert.assertEquals(pseudo, value.getValue());
        verify(sessionGroup).removeUser((Group) anyObject(), userArgument.capture());
        Assert.assertEquals(mockuser, userArgument.getValue());

        verify(entityManager).remove(userArgument.capture());
        Assert.assertSame(mockuser,userArgument.getValue());        
        Assert.assertSame(mockuser, result);
    }

    @Test
    public void shouldRemoveUserByPseudoBis() throws NoEntityFoundException  {
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(mockuser);
        when(itGroup.hasNext()).thenReturn(true,false);
        //Throwing an exception to verify the catch 
        when(sessionGroup.removeUser((Group) anyObject(), any(User.class))).thenThrow(new NoEntityFoundException());
        //When
        User result = sessionUser.removeUserbyPseudo(pseudo);
        //Then
        verify(entityManager).find(eq(User.class), value.capture());
        Assert.assertEquals(pseudo, value.getValue());
        verify(sessionGroup).removeUser((Group) anyObject(), userArgument.capture());
        Assert.assertEquals(mockuser, userArgument.getValue());

        Assert.assertSame(null, result);


    }
    
    @Test
    public void shouldThrowExceptionWhenDeleteEntityCauseUserNotExisting() throws NoEntityFoundException  {
        //Given
        when(entityManager.find(eq(User.class),anyString())).thenReturn(null);

        //When
        User result = sessionUser.removeUserbyPseudo(pseudo);
        //Then
        Assert.assertSame(null, result);
    }
    
    @Test(expectedExceptions= EntityAlreadyExistsException.class)
    public void shouldThrowsExceptionWhenAddCauseUserExistent() throws EntityAlreadyExistsException {
        when(entityManager.find(eq(User.class),anyString())).thenReturn(mockuser);
        sessionUser.addUser(pseudo,password,email);
    }
    
    @Test
    public void shouldFindUser() {
        //When
        sessionUser.findUserByPseudo(pseudo);
        //Then
        verify(entityManager).find(eq(User.class),value.capture());
        Assert.assertEquals(pseudo, value.getValue());
    }

    @Test
    public void shouldCollectUsers(){
        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(userList);

        //when
        sessionUser.collectUsers();
        //Then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }

    @Test
    public void shouldAddGroup() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        when(mockuser.getGroupSet()).thenReturn(groups);
        //When
        sessionUser.addGroup(mockuser, mockgroup);
        //Then
        verify(mockuser).getGroupSet();
        verify(groups).add(groupArgument.capture());
        Assert.assertSame(mockgroup, groupArgument.getValue());
        verify(entityManager).merge(mockuser);

    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenAddGroupForUserInexistent() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(null);
        when(mockuser.getGroupSet()).thenReturn(groups);
        //When
        sessionUser.addGroup(mockuser, mockgroup);
        //Then
    }

    @Test
    public void shouldCollectGroup() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        //When
        sessionUser.collectGroups(pseudo);
        //Then
        verify(entityManager).find(eq(User.class),value.capture());
        Assert.assertEquals(pseudo, value.getValue());
        verify(mockuser).getGroupSet();
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectGroupCauseEntityNotExisting() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(null);
        //When
        sessionUser.collectGroups(pseudo);
    }

    @Test
    public void shouldRemoveGroup() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        when(mockuser.getGroupSet()).thenReturn(groups);

        //when
        sessionUser.removeGroup(mockuser, mockgroup);
        //
        verify(mockuser).getGroupSet();
        verify(groups).remove(groupArgument.capture());
        Assert.assertEquals(mockgroup, groupArgument.getValue());
        verify(entityManager).merge(mockuser);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenRemoveGroupForUserInexistent() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(null);
        //when
        sessionUser.removeGroup(mockuser, mockgroup);
        //Then throws new NoEntityFoundException
    }
    
    @Test
    public void shouldCollectPetal() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        Set<Group> userGroups = new HashSet<>();
        
        Group group = mock(Group.class);
        Set<Petal> userPetals = new HashSet<>();
        when(group.getPetals()).thenReturn(userPetals);
        
        userGroups.add(group);
        
        when(mockuser.getGroupSet()).thenReturn(userGroups);

        //when
        sessionUser.collectPetals(pseudo);
        //Then
        verify(entityManager).find(eq(User.class), value.capture());
        Assert.assertEquals(pseudo, value.getValue());

        verify(mockuser).getGroupSet();
        verify(group).getPetals();
    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalCauseEntityNotExisting() throws NoEntityFoundException{
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(null);
        //When
        sessionUser.collectPetals(pseudo);
    }

}
