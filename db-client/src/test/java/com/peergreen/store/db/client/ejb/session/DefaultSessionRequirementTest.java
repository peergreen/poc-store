package com.peergreen.store.db.client.ejb.session;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
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
    private Iterator<Petal> iterator;
    @Mock
    private Query query;
    @Mock
    private ISessionPetal sessionPetal;



    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        sessionRequirement = new DefaultSessionRequirement();
        sessionRequirement.setEntityManager(entityManager);
        sessionRequirement.setPetalSession(sessionPetal);
        
        requirementArgument = ArgumentCaptor.forClass(Requirement.class);
        idArgument = ArgumentCaptor.forClass(String.class);
        petalArgument = ArgumentCaptor.forClass(Petal.class);
        requirementName = "my requirement";
        namespace="service";
        filter="namespace=service";
        queryString = "Requirement.findAll";
        queryString2 = "RequirementByName";
        
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(mockrequirement.getRequirementName()).thenReturn("name");
        when(sessionPetal.findPetal(any(Vendor.class), anyString(), anyString())).thenReturn(petal);
        when(petals.iterator()).thenReturn(iterator);

    }

    @Test
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

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddCauseAlreadyExist() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);
        //When
        sessionRequirement.addRequirement(requirementName, namespace, filter);
        sessionRequirement.addRequirement(requirementName, namespace, filter);
    }

    @Test
    public void shouldDeleteRequirement() throws NoEntityFoundException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);   
        when(mockrequirement.getPetals()).thenReturn(petals);
        when(iterator.hasNext()).thenReturn(true,false);
        //When
        Requirement result = sessionRequirement.deleteRequirement(requirementName);
        //Then 
        verify(sessionPetal).removeRequirement((Petal) anyObject(), requirementArgument.capture());
        Assert.assertEquals(mockrequirement, requirementArgument.getValue());

        verify(entityManager).remove(requirementArgument.capture());
        Assert.assertSame(mockrequirement,requirementArgument.getValue());
        Assert.assertSame(mockrequirement,result);

    }
    
    @Test
    public void shouldDeleteRequirementBis() throws NoEntityFoundException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);   
        when(mockrequirement.getPetals()).thenReturn(petals);
        when(iterator.hasNext()).thenReturn(true,false);
        //Throwing an exception to verify the catch 
        when(sessionPetal.removeRequirement((Petal) anyObject(), any(Requirement.class))).thenThrow(new NoEntityFoundException("Entity does not exist."));
        //When
        Requirement result = sessionRequirement.deleteRequirement(requirementName);
        //Then 
        verify(sessionPetal).removeRequirement((Petal) anyObject(), requirementArgument.capture());
        Assert.assertEquals(mockrequirement, requirementArgument.getValue());

        Assert.assertSame(null,result);

    }
    

    @Test
    public void shouldReturnNullWhenDeleteCauseEntityNotExisting(){
        //Given
        when(sessionRequirement.findRequirement(anyString())).thenReturn(null);   
        //When
        Requirement result =  sessionRequirement.deleteRequirement(requirementName);
        //Then
        Assert.assertSame(null,result);

    }

    @Test
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
    
    @Test
    public void shouldFindRequirementBis() {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        //Throwing an exception to verify the catch 
        when(query.getSingleResult()).thenThrow(new NoResultException());
        //When
      Requirement result =  sessionRequirement.findRequirement(requirementName);
        //Then
        verify(entityManager).createNamedQuery(idArgument.capture());
        Assert.assertEquals(queryString2, idArgument.getValue());
        verify(query).setParameter(anyString(), idArgument.capture());
        Assert.assertEquals(requirementName, idArgument.getValue());
        Assert.assertSame(null, result);
    }

    @Test
    public void shouldCollectPetals() throws NoEntityFoundException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);  
        //When
        sessionRequirement.collectPetals(requirementName);
        //Then
        verify(mockrequirement).getPetals();
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsCauseEntityNotExisting() throws NoEntityFoundException {
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionRequirement.findRequirement(anyString())).thenReturn(null);  
        //When
        sessionRequirement.collectPetals(requirementName);
    }

    @Test
    public void shouldAddPetalTorequirement() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(mockrequirement);
        when(mockrequirement.getPetals()).thenReturn(petals);
        //When
        sessionRequirement.addPetal(mockrequirement,petal);

        //Then
        verify(mockrequirement).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertSame(petal,petalArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenAddPetalTorequirement() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        when(mockrequirement.getPetals()).thenReturn(petals);
        //When
        sessionRequirement.addPetal(mockrequirement,petal);

        //Then we throw a new NoEntityFoundException
    }

    @Test
    public void shouldRemovePetalFromRequirement() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(mockrequirement);
        when(mockrequirement.getPetals()).thenReturn(petals);
        //When
        sessionRequirement.removePetal(mockrequirement, petal);
        //Then
        verify(mockrequirement).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertSame(petal, petalArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenRemovePetalFromRequirement() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        when(mockrequirement.getPetals()).thenReturn(petals);
        //When
        sessionRequirement.removePetal(mockrequirement, petal);
        
        //Then we throw a new NoEntityFoundException

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
    public void shouldUpdateNamespace() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(mockrequirement);
        //when
        sessionRequirement.updateNamespace(mockrequirement, namespace);
        //then
        verify(mockrequirement).setNamespace(idArgument.capture());
        Assert.assertEquals(namespace, idArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenUpdateNamespace() throws NoEntityFoundException{
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //when
        sessionRequirement.updateNamespace(mockrequirement, namespace);
        //Then throw new NoEntityFoundException 
    }


    @Test
    public void shouldUpdateFilter() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(mockrequirement);
        //when
        sessionRequirement.updateFilter(mockrequirement, filter);
        //then
        verify(mockrequirement).setFilter(idArgument.capture());
        Assert.assertEquals(filter, idArgument.getValue());
        verify(entityManager).merge(mockrequirement);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenUpdateFilter() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //when
        sessionRequirement.updateFilter(mockrequirement, filter);
        //Then throw new NoEntityFoundException 

    }
    
}
