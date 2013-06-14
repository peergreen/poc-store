package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyInt;
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

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.impl.DefaultRequirement;

public class DefaultRequirementTest {

    private DefaultRequirement sessionRequirement;
    private ArgumentCaptor<Requirement> requirementArgument;
    private ArgumentCaptor<String> idArgument;
    private ArgumentCaptor<Petal> petalArgument;
    private String filter;
    private String namespace;
    private String requirementName;
    private String queryString;
    private List<Requirement> requs;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Requirement mockrequirement;
    @Mock
    private Petal petal;
    @Mock
    private Set<Petal> petals;
    @Mock
    private Query query;



    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        sessionRequirement = new DefaultRequirement();
        sessionRequirement.setEntityManager(entityManager);
        requirementArgument = ArgumentCaptor.forClass(Requirement.class);
        idArgument = ArgumentCaptor.forClass(String.class);
        petalArgument = ArgumentCaptor.forClass(Petal.class);
        requirementName = "my requirement";
        namespace="service";
        filter="namespace=service";
        queryString = "Requirement.findAll";
        requs = new ArrayList<Requirement>();
    }

    @Test
    public void shouldAddMissingRequirement() {
      
        //When
        sessionRequirement.addRequirement(requirementName, namespace, filter);
        //Then
        verify(entityManager).persist(requirementArgument.capture());
        Assert.assertEquals(requirementName, requirementArgument.getValue().getRequirementName());
        Assert.assertEquals(namespace, requirementArgument.getValue().getNamespace());
        Assert.assertEquals(filter, requirementArgument.getValue().getFilter());
        Assert.assertTrue(requirementArgument.getValue().getPetals().isEmpty());
    }


    @Test
    public void shouldDeleteRequirement(){
        //Given
        when(entityManager.find(eq(Requirement.class), anyInt())).thenReturn(mockrequirement);
        //When
        sessionRequirement.deleteRequirement(requirementName);
        //Then
        verify(entityManager).find(eq(Requirement.class),idArgument.capture());
        Assert.assertEquals(requirementName, idArgument.getValue());
        verify(entityManager).remove(requirementArgument.capture());
        Assert.assertSame(mockrequirement,requirementArgument.getValue() );

    }

    @Test
    public void shouldFindRequirement() {

        //When
        sessionRequirement.findRequirement(requirementName);
        //Then
        verify(entityManager).find(eq(Requirement.class),idArgument.capture());
        Assert.assertEquals(requirementName, idArgument.getValue());

    }

    @Test
    public void shouldCollectPetals(){
        //Given
        when(entityManager.find(eq(Requirement.class), anyString())).thenReturn(mockrequirement);
        //When
        sessionRequirement.collectPetals(requirementName);
        //Then
        verify(entityManager).find(eq(Requirement.class),idArgument.capture());
        Assert.assertEquals(requirementName, idArgument.getValue());
        verify(mockrequirement).getPetals();
    }

    @Test
    public void shouldAddPetalTorequirement(){
        //Given
        when(mockrequirement.getPetals()).thenReturn(petals);

        //When
        sessionRequirement.addPetal(mockrequirement,petal);

        //Then
        verify(mockrequirement).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertSame(petal,petalArgument.getValue());
        verify(mockrequirement).setPetals(petals);
        verify(entityManager).merge(mockrequirement);
    }

    @Test
    public void shouldRemovePetalFromRequirement(){
        //Given
        when(mockrequirement.getPetals()).thenReturn(petals);
        //When
        sessionRequirement.removePetal(mockrequirement, petal);
        //Then
        verify(mockrequirement).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertSame(petal, petalArgument.getValue());
        verify(entityManager).merge(mockrequirement);


    }
    
    @Test
    public void shouldCollectRequirements() {
        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        //when
        sessionRequirement.collectRequirements();
        //then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }
    
    @Test
    public void shouldUpdateNamespace(){
        
      //when
        sessionRequirement.updateNamespace(mockrequirement, namespace);
        //then
        verify(mockrequirement).setNamespace(idArgument.capture());
        Assert.assertEquals(namespace, idArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }
    
    @Test
    public void shouldUpdateFilter() {
        
      //when
        sessionRequirement.updateFilter(mockrequirement, filter);
        //then
        verify(mockrequirement).setFilter(idArgument.capture());
        Assert.assertEquals(filter, idArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }
    
}
