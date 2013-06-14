package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.impl.DefaultUser;

public class DefaultUserTest {

    private DefaultUser sessionUser;
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
        sessionUser = new DefaultUser();
        sessionUser.setEntityManager(entityManager);
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
    }



    @Test
    public void shouldAddUserNonExistent() {

        //When 
        sessionUser.addUser(pseudo, password, email);
        //Then
        verify(entityManager).persist(userArgument.capture());
        Assert.assertEquals(pseudo, userArgument.getValue().getPseudo());
        Assert.assertEquals(password, userArgument.getValue().getPassword());
        Assert.assertEquals(email, userArgument.getValue().getEmail());

    }


    @Test
    public void shouldUpdateUser(){
        //Given
        String password1 = "pwdbis";
        String email1 = "titi@peergreen.com";
        //When
        sessionUser.updateUser(mockuser, pseudo, password1, email1);
        //Then

        verify(mockuser).setPassword(value.capture());
        Assert.assertEquals(password1, value.getValue());

        when(mockuser.getPassword()).thenReturn(password1);
        
        verify(mockuser).setEmail(value.capture());
        Assert.assertEquals(email1, value.getValue());
        
        when(mockuser.getEmail()).thenReturn(email1);

        verify(entityManager).merge(userArgument.capture());
        Assert.assertEquals(password1, userArgument.getValue().getPassword());
        Assert.assertEquals(email1, userArgument.getValue().getEmail());
    }

    @Test
    public void shouldRemoveUserByPseudo(){

        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        //WHen
        sessionUser.removeUserbyPseudo(pseudo);
        //Then
        verify(entityManager).find(eq(User.class), value.capture());
        Assert.assertEquals(pseudo, value.getValue());
        verify(entityManager).remove(userArgument.capture());
        Assert.assertSame(mockuser,userArgument.getValue());        

    }

    /*  @Test(dependsOnMethods={"shouldAddUser"},expectedExceptions= EntityExistsException.class)
    public void shouldThrowsExceptionCauseUserExistent() {
        String pseudo = "toto";
        String password = "pwdbis";
        String mail = "totobis@users.com";
        sessionUser.addUser(pseudo,password,mail);
    }*/

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
    public void shouldAddGroup(){
        //Given
        when(mockuser.getGroupSet()).thenReturn(groups);
        //When
        sessionUser.addGroup(mockuser, mockgroup);
        //Then
        verify(mockuser).getGroupSet();
        verify(groups).add(groupArgument.capture());
        Assert.assertSame(mockgroup, groupArgument.getValue());
        verify(mockuser).setGroupSet(groups);
        verify(entityManager).merge(mockuser);

    }

    @Test
    public void shouldCollectGroup(){
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        //When
        sessionUser.collectGroups(pseudo);
        //Then
        verify(entityManager).find(eq(User.class),value.capture());
        Assert.assertEquals(pseudo, value.getValue());
        verify(mockuser).getGroupSet();
    }

    @Test
    public void shouldRemoveGroup(){
        //Given
        when(mockuser.getGroupSet()).thenReturn(groups);

        //when
        sessionUser.removeGroup(mockuser, mockgroup);
        //
        verify(mockuser).getGroupSet();
        verify(groups).remove(groupArgument.capture());
        Assert.assertEquals(mockgroup, groupArgument.getValue());
        verify(entityManager).merge(mockuser);
    }

    @Test
    public void shouldCollectPetal(){
        //Given
        when(entityManager.find(eq(User.class), anyString())).thenReturn(mockuser);
        when(mockuser.getGroupSet()).thenReturn(groups);
        when(groups.toArray()).thenReturn(groupArray);
       
        //when
        sessionUser.collectPetals(pseudo);
        //Then
        verify(entityManager).find(eq(User.class),value.capture());
        Assert.assertEquals(pseudo, value.getValue());
        
        verify(mockuser).getGroupSet();
        verify(mockgroup).getPetals();
        

    }

}