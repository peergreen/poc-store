package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.peergreen.store.db.client.ejb.impl.DefaultSessionRequirement;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionRequirementTest {

    private DefaultSessionRequirement sessionRequirement;
    private ArgumentCaptor<Requirement> requirementArgument;
    private ArgumentCaptor<String> idArgument;
    private ArgumentCaptor<Petal> petalArgument;
    private String filter;
    private String namespace;
    private String requirementName;
    private String queryString;
    private String queryString2;
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
        sessionRequirement = new DefaultSessionRequirement();
        sessionRequirement.setEntityManager(entityManager);
        requirementArgument = ArgumentCaptor.forClass(Requirement.class);
        idArgument = ArgumentCaptor.forClass(String.class);
        petalArgument = ArgumentCaptor.forClass(Petal.class);
        requirementName = "my requirement";
        namespace="service";
        filter="namespace=service";
        queryString = "Requirement.findAll";
        queryString2 = "RequirementByName";
    }

    //@Test
    public void shouldAddRequirement() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(null);
        //When
        sessionRequirement.addRequirement(requirementName, namespace, filter);
        //Then
        verify(entityManager).persist(requirementArgument.capture());
        Assert.assertEquals(requirementName, requirementArgument.getValue().getRequirementName());
        Assert.assertEquals(namespace, requirementArgument.getValue().getNamespace());
        Assert.assertEquals(filter, requirementArgument.getValue().getFilter());
        Assert.assertTrue(requirementArgument.getValue().getPetals().isEmpty());
    }

    //@Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddCauseAlreadyExist() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);
        //When
        sessionRequirement.addRequirement(requirementName, namespace, filter);
        sessionRequirement.addRequirement(requirementName, namespace, filter);
    }

    //@Test
    public void shouldDeleteRequirement() {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);   
        //When
        sessionRequirement.deleteRequirement(requirementName);
        //Then 
        verify(entityManager).remove(requirementArgument.capture());
        Assert.assertSame(mockrequirement,requirementArgument.getValue());
    }

    //@Test
    public void shouldThrowExceptionWhenDeleteCauseEntityNotExisting(){
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(null);   
        //When
        sessionRequirement.deleteRequirement(requirementName);
    }

    //@Test
    public void shouldFindRequirement() {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        //When
        sessionRequirement.findRequirement(requirementName);
        //Then
        verify(entityManager).createNamedQuery(idArgument.capture());
        Assert.assertEquals(queryString2, idArgument.getValue());
        verify(query).setParameter(anyString(), idArgument.capture());
        Assert.assertEquals(requirementName, idArgument.getValue());
        verify(query).getSingleResult();
    }

    //@Test
    public void shouldCollectPetals() throws NoEntityFoundException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);  
        //When
        sessionRequirement.collectPetals(requirementName);
        //Then
        verify(mockrequirement).getPetals();
    }

    //@Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsCauseEntityNotExisting() throws NoEntityFoundException {
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(null);  
        //When
        sessionRequirement.collectPetals(requirementName);
    }
    
    //@Test
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

    //@Test
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

    //@Test
    public void shouldCollectRequirements() {
        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        //when
        sessionRequirement.collectRequirements();
        //then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }

    //@Test
    public void shouldUpdateNamespace(){

        //when
        sessionRequirement.updateNamespace(mockrequirement, namespace);
        //then
        verify(mockrequirement).setNamespace(idArgument.capture());
        Assert.assertEquals(namespace, idArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }

    //@Test
    public void shouldUpdateFilter() {

        //when
        sessionRequirement.updateFilter(mockrequirement, filter);
        //then
        verify(mockrequirement).setFilter(idArgument.capture());
        Assert.assertEquals(filter, idArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }

}
