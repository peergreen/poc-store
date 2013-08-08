package com.peergreen.store.db.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionGroup;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionGroupTest {

    private DefaultSessionGroup sessionGroup ; 
    private String groupname;
    private String queryString;
    private String queryString2;
    private List<Group> groupList;

    ArgumentCaptor<Group> groupArgument;
    ArgumentCaptor<String> name;
    ArgumentCaptor<Petal> petalArgument;
    ArgumentCaptor<User> userArgument;


    @Mock
    private EntityManager entityManager;
    @Mock
    private Group mockgroup;
    @Mock
    private Petal mockpetal;
    @Mock
    private User mockuser;
    @Mock
    private Set<Petal> petals;
    @Mock
    private Set<User> users;
    @Mock
    private Query query;
    @Mock
    private ISessionPetal sessionPetal;
    @Mock
    private DefaultSessionUser sessionUser;
    @Mock
    private Iterator<Petal> itP;
    @Mock
    private Iterator<User> itU;


    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        sessionGroup = new DefaultSessionGroup();
        sessionGroup.setEntityManager(entityManager); 
        sessionGroup.setSessionUser(sessionUser);
        sessionGroup.setSessionPetal(sessionPetal);

        groupArgument = ArgumentCaptor.forClass(Group.class);
        name = ArgumentCaptor.forClass(String.class);
        petalArgument  = ArgumentCaptor.forClass(Petal.class);
        userArgument  = ArgumentCaptor.forClass(User.class);

        groupname= "Dev2";
        queryString = "Series.findAll";
        queryString2 = "GroupByName";
        groupList = new ArrayList<Group>();

        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(sessionPetal.findPetal(any(Vendor.class), anyString(), anyString())).thenReturn(mockpetal);
        when(sessionUser.findUserByPseudo(anyString())).thenReturn(mockuser);


        when(petals.iterator()).thenReturn(itP);
        when(users.iterator()).thenReturn(itU);


    }

    @Test
    public void shouldAddGroupNonExistent() throws NoEntityFoundException, EntityAlreadyExistsException{
        //Given : The Group doesn't exist in the database
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionGroup.addGroup(groupname);
        //Then
        verify(entityManager).persist(groupArgument.capture());
        Assert.assertEquals(groupname, groupArgument.getValue().getGroupname());
        Assert.assertTrue(groupArgument.getValue().getPetals().isEmpty());
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddGroupCauseAlreadyExist() throws NoEntityFoundException, EntityAlreadyExistsException {
        //Given: The Group already exists in the database
        when(query.getSingleResult()).thenReturn(mockgroup);
        //When
        sessionGroup.addGroup(groupname);
    }

    @Test
    public void shouldFindGroup() {
        //when
        sessionGroup.findGroup(groupname);
        //Then
        verify(entityManager).createNamedQuery(name.capture());
        Assert.assertEquals(queryString2, name.getValue());
        verify(query).setParameter(anyString(), name.capture());
        Assert.assertEquals(groupname, name.getValue());
        verify(query).getSingleResult();
    }

    @Test
    public void shouldDeleteGroup() throws NoEntityFoundException{
        //Given : A group with 2 users and 1 petal accessible
        when(query.getSingleResult()).thenReturn(mockgroup);
        when(mockgroup.getPetals()).thenReturn(petals);
        when(mockgroup.getUsers()).thenReturn(users);
        when(itP.hasNext()).thenReturn(true,false);
        when(itU.hasNext()).thenReturn(true,true,false);
        //when
        sessionGroup.deleteGroup(groupname);
        //then
        verify(mockgroup).getUsers();
        verify(sessionUser,times(2)).removeGroup((User) anyObject(), groupArgument.capture());
        Assert.assertEquals(mockgroup, groupArgument.getValue());

        verify(mockgroup).getPetals();
        verify(sessionPetal).removeAccesToGroup((Petal) anyObject(), groupArgument.capture());
        Assert.assertEquals(mockgroup, groupArgument.getValue());

        verify(entityManager).remove(mockgroup);
    }

    @Test
    public void shouldAddUserToAGroup() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(mockgroup);
        when(mockgroup.getUsers()).thenReturn(users);
        //when
        sessionGroup.addUser(mockgroup,mockuser);
        //Then
        verify(mockgroup).getUsers();
        verify(users).add(userArgument.capture());
        verify(sessionUser).addGroup(mockuser, mockgroup);
        Assert.assertEquals(mockuser, userArgument.getValue());

    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseAddUserToGroupInexistent() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //when
        sessionGroup.addUser(mockgroup,mockuser);
        //Then throw NoEntityFoundException
    }

    @Test
    public void shouldRemoveUserFromAgroup() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(mockgroup);
        when(mockgroup.getUsers()).thenReturn(users);
        //when

        sessionGroup.removeUser(mockgroup,mockuser);

        //Then
        verify(mockgroup).getUsers();
        verify(users).remove(userArgument.capture());
        Assert.assertEquals(mockuser, userArgument.getValue());
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseRemoveUserFromGroupInexistent() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //when
        sessionGroup.removeUser(mockgroup,mockuser);

        //Then throw NoEntityFoundException
    }

    @Test 
    public void shouldCollectUserOfGroup() throws NoEntityFoundException {

        //Given
        when(sessionGroup.findGroup(anyString())).thenReturn(mockgroup);
        //when
        sessionGroup.collectUsers(groupname);
        //Then
        verify(mockgroup).getUsers();

    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectCauseEntityNotExisting() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionGroup.collectUsers(groupname);      
    }

    @Test
    public void shouldAddPetal() throws NoEntityFoundException {
        //Given
        when(sessionGroup.findGroup(anyString())).thenReturn(mockgroup);
        when(mockgroup.getPetals()).thenReturn(petals);
        //when
        sessionGroup.addPetal(mockgroup,mockpetal);

        //Then
        verify(mockgroup).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertEquals(mockpetal, petalArgument.getValue());
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseGiveAccessToPetalForGroupInexistent() throws NoEntityFoundException{
        //Given
        when(sessionGroup.findGroup(anyString())).thenReturn(null);
        //when
        sessionGroup.addPetal(mockgroup,mockpetal);

        //Then throw NoEntityFoundException 
    }

    @Test
    public void shouldRemovePetal() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(mockgroup);
        when(mockgroup.getPetals()).thenReturn(petals);
        //when
        
        sessionGroup.removePetal(mockgroup,mockpetal);

        //Then
        verify(mockgroup).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertEquals(mockpetal, petalArgument.getValue());
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseRemoveAccessToPetalForGroupInexistent() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //when
        sessionGroup.removePetal(mockgroup,mockpetal);
        //Then throw NoEntityFoundException 
    }
     
    @Test
    public void shouldCollectPetals() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(mockgroup);
        //when
        sessionGroup.collectPetals(groupname);
        //Then
        verify(mockgroup).getPetals();

    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsCauseEntityNotExisting() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionGroup.collectPetals(groupname);
    }

    @Test
    public void shouldCollectAllGroups(){
        //Given
        when(query.getResultList()).thenReturn(groupList);
        //when
        sessionGroup.collectGroups();
        //Then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }
}

