package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.impl.DefaultCapability;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


public class DefaultCapabilityTest {

    private DefaultCapability sessionCapability;
    private String queryString;
    private String version; 
    private List<Capability> capabilityList;
    private Map<String,String> properties;
    ArgumentCaptor<Capability> capability1;
    ArgumentCaptor<String> value;
    ArgumentCaptor<Map<String,String>> propArgument;
    ArgumentCaptor<String> stringArgumentCaptor;

    @Mock
    private EntityManager entityManager;  
    @Mock 
    private Capability mockcapability;
    @Mock
    private Query query;
    @Mock
    private Petal petal;
    @Mock
    private Set<Petal> petals;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionCapability = new DefaultCapability();
        sessionCapability.setEntityManager(entityManager);       
        capability1 = ArgumentCaptor.forClass(Capability.class);
        value = ArgumentCaptor.forClass(String.class);
        stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        properties = new HashMap<String,String>();
        capabilityList = new ArrayList<Capability>() ;
        version ="1.0";

    }


//    @Test
    /**
     * Test to check that adding a capability     
     */
    public void shouldAddCapability() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        //When 
        sessionCapability.addCapability("capabilityName",version, "namespace", properties);
        //Then 
        verify(entityManager).persist(capability1.capture());
        Assert.assertEquals("capabilityName", capability1.getValue().getCapabilityName());
        Assert.assertEquals(version, capability1.getValue().getVersion());
        Assert.assertEquals("namespace", capability1.getValue().getNamespace());
        Assert.assertEquals(properties, capability1.getValue().getProperties());
        Assert.assertTrue(capability1.getValue().getPetals().isEmpty());
    }

//    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shoudThrowExceptionWhenAddCauseEntityAlreadyExits() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockcapability);
        when(mockcapability.getVersion()).thenReturn(version);
        when(mockcapability.getNamespace()).thenReturn("namespace");

        //when
        sessionCapability.addCapability("capabilityName",version, "namespace", properties);
        sessionCapability.addCapability("capabilityName",version, "namespace", properties);
    }

//    @Test
    /**
     * Test to check if the search of a capability non existent returns null 
     */
    public void shouldFindCapability(){
        //Given
        queryString = "CapabilityByName";
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockcapability);
        //When 
        sessionCapability.findCapability("capabilityName",version);
        //Then 

        verify(entityManager).createNamedQuery(stringArgumentCaptor.capture());
        Assert.assertEquals(queryString, stringArgumentCaptor.getValue());
        verify(query, times(2)).setParameter(anyString(), anyString());
        verify(query).getSingleResult();


    }

//    @Test
    /**
     *Test to check if the delete feature works well  
     */
    public void shouldRemoveCapability(){
        //Given a petal that provided a capability
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockcapability);
        //When  
        sessionCapability.deleteCapability("capabilityName",version);
        //Then 

        verify(entityManager).remove(mockcapability);
    }

//    @Test
    /**
     *Test to check if the delete feature works well  
     */
    public void shouldThrowExceptionWhenDeleteCauseEntityNotExistent(){
        //Given a petal that provided a capability
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        //When  
        sessionCapability.deleteCapability("capabilityName",version);

    }

//    @Test
    /**
     *Test to check if the feature to add petal to a capability works well  
     */
    public void shouldAddPetalToCapability()
    {
        //Given petals that provided a capability existent in the database
        ArgumentCaptor<Petal> petalArgument = ArgumentCaptor.forClass(Petal.class);

        when(mockcapability.getCapabilityName()).thenReturn("capabilityName");
        when(mockcapability.getPetals()).thenReturn(petals);

        //When adding a new petal 
        sessionCapability.addPetal(mockcapability, petal);

        //Then  
        verify(mockcapability).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertSame(petal,petalArgument.getValue());
        verify(mockcapability).setPetals(petals);
        verify(entityManager).merge(mockcapability);

    }


//    @Test
    /**
     *Test to check if the feature to remove petal from a capability works well  
     */
    public void shouldRemoveOnePetalGivenACapability()
    {
        //Given 
        ArgumentCaptor<Petal> petalArgument = ArgumentCaptor.forClass(Petal.class);

        when(mockcapability.getPetals()).thenReturn(petals);
        when(mockcapability.getCapabilityName()).thenReturn("Mock");

        //When
        sessionCapability.removePetal(mockcapability, petal);

        //Then 
        verify(mockcapability).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertEquals(petal,petalArgument.getValue());

        when(petals.isEmpty()).thenReturn(false);
        verify(entityManager).merge(capability1.capture());
        Assert.assertEquals(mockcapability,capability1.getValue());

    }

//    @Test
    /**
     *Test to check if the feature to remove petal from a capability works well  
     */
    public void shouldRemoveTheOnlyPetalGivenACapability()
    {
        //Given 
        ArgumentCaptor<Petal> petalArgument = ArgumentCaptor.forClass(Petal.class);

        when(mockcapability.getPetals()).thenReturn(petals);
        when(mockcapability.getCapabilityName()).thenReturn("Mock");
        when(petals.isEmpty()).thenReturn(true);

        //When
        sessionCapability.removePetal(mockcapability, petal);

        //Then 
        verify(mockcapability).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertEquals(petal,petalArgument.getValue());

        verify(entityManager).remove(capability1.capture());
        Assert.assertEquals(mockcapability,capability1.getValue());

    }

//    @Test
    /**
     * Test to check if the feature to collect petals which provides a capability works well
     */
    public void shouldCollectPetals() throws NoEntityFoundException{
        //Given 
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockcapability);
        //When
        sessionCapability.collectPetals("capabilityName",version);
        //Then
        verify(mockcapability).getPetals();
    }

//    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectCauseEntityNotExistent() throws NoEntityFoundException {
        //Given 
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCapability.collectPetals("capabilityName",version);
    }

//    @Test
    public void shouldCollectAllCapabilities() {
        queryString = "Capability.findAll";

        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(capabilityList);

        //when
        sessionCapability.collectCapabilities();
        //Then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }

//    @Test
    public void shouldUpdateNamespace() {

        //when
        sessionCapability.updateNamespace(mockcapability, "namespace");
        //then
        verify(mockcapability).setNamespace(value.capture());
        Assert.assertEquals("namespace", value.getValue());
        verify(entityManager).merge(mockcapability);
    }

//    @Test
    public void shouldUpdateProperties() {

        //when
        sessionCapability.updateProperties(mockcapability, properties);
        //then
        verify(mockcapability).setProperties(properties);
        verify(entityManager).merge(mockcapability);
    }
}
