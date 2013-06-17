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

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.impl.DefaultGroup;

public class DefaultGroupTest {
  
    private DefaultGroup sessionGroup ; 
    private String groupname;
    ArgumentCaptor<Group> groupArgument;
    ArgumentCaptor<String> name;
    ArgumentCaptor<Petal> petalArgument;
    ArgumentCaptor<User> userArgument;
    private String queryString;
    private List<Group> groupList;
    
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
    
  @BeforeMethod
  public void beforeMethod() {
      MockitoAnnotations.initMocks(this);
      sessionGroup = new DefaultGroup();
      sessionGroup.setEntityManager(entityManager);  
      groupArgument = ArgumentCaptor.forClass(Group.class);
      name = ArgumentCaptor.forClass(String.class);
      petalArgument  = ArgumentCaptor.forClass(Petal.class);
      userArgument  = ArgumentCaptor.forClass(User.class);
      groupname="usersgroup";
      queryString = "Group.findAll";
      groupList = new ArrayList<Group>();


  }
  
  @Test
  public void shouldAddGroup(){
      
      //When
      sessionGroup.addGroup(groupname);
      //Then
      verify(entityManager).persist(groupArgument.capture());
      Assert.assertEquals(groupname, groupArgument.getValue().getGroupname());
      Assert.assertTrue(groupArgument.getValue().getPetals().isEmpty());
      Assert.assertTrue(groupArgument.getValue().getUsers().isEmpty());

      
  }

  @Test
  public void shouldFindGroup(){
      
      //when
      sessionGroup.findGroup(groupname);
      //Then
      verify(entityManager).find(eq(Group.class),name.capture());
      Assert.assertEquals(groupname, name.getValue());
  }
  
 
  @Test
  public void shouldDeleteGroup(){
      //Given
      when(entityManager.find(eq(Group.class), anyString())).thenReturn(mockgroup);
      //when
      sessionGroup.deleteGroup(groupname);
      //then
      verify(entityManager).find(eq(Group.class),name.capture());
      Assert.assertEquals(groupname, name.getValue());
      verify(entityManager).remove(mockgroup);

  }
  
  @Test
  public void shouldAddUserToAGroup(){
      //Given
      when(mockgroup.getUsers()).thenReturn(users);
      //when
      sessionGroup.addUser(mockgroup,mockuser);
      //Then
      verify(mockgroup).getUsers();
      verify(users).add(userArgument.capture());
      Assert.assertEquals(mockuser, userArgument.getValue());
      verify(mockgroup).setUsers(users);

  }
  
  @Test
  public void shouldRemoveUserFromAgroup(){
      //Given
      when(mockgroup.getUsers()).thenReturn(users);
      //when
      sessionGroup.removeUser(mockgroup,mockuser);
      //Then
      verify(mockgroup).getUsers();
      verify(users).remove(userArgument.capture());
      Assert.assertEquals(mockuser, userArgument.getValue());
      verify(mockgroup).setUsers(users);
  }
  
  @Test 
  public void shouldCollectUserOfGroup() {
      
      //Given
      when(entityManager.find(eq(Group.class), anyString())).thenReturn(mockgroup);
      //when
      sessionGroup.collectUsers(groupname);
      //Then
      verify(entityManager).find(eq(Group.class),name.capture());
      Assert.assertEquals(groupname, name.getValue());
      verify(mockgroup).getUsers();
   
  }
  
  @Test
  public void shouldAddPetal() {
      //Given
      when(mockgroup.getPetals()).thenReturn(petals);
      //when
      sessionGroup.addPetal(mockgroup,mockpetal);
      //Then
      verify(mockgroup).getPetals();
      verify(petals).add(petalArgument.capture());
      Assert.assertEquals(mockpetal, petalArgument.getValue());
      verify(mockgroup).setPetals(petals);
  }
  
  @Test
  public void shouldRemovePetal(){
      //Given
      when(mockgroup.getPetals()).thenReturn(petals);
      //when
      sessionGroup.removePetal(mockgroup,mockpetal);
      //Then
      verify(mockgroup).getPetals();
      verify(petals).remove(petalArgument.capture());
      Assert.assertEquals(mockpetal, petalArgument.getValue());
      verify(mockgroup).setPetals(petals);
  }
  
  @Test
  public void shouldCollectPetals(){
    //Given
      when(entityManager.find(eq(Group.class), anyString())).thenReturn(mockgroup);
      //when
      sessionGroup.collectPetals(groupname);
      //Then
      verify(entityManager).find(eq(Group.class),name.capture());
      Assert.assertEquals(groupname, name.getValue());
      verify(mockgroup).getPetals();
   
  }
  
  @Test
  public void shouldCollectAllGroups(){
      //Given
      when(entityManager.createNamedQuery(anyString())).thenReturn(query);
      when(query.getResultList()).thenReturn(groupList);
      
      //when
      sessionGroup.collectGroups();
      //Then
      verify(entityManager).createNamedQuery(queryString);
      verify(query).getResultList();
  }
}

