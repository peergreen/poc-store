package com.peergreen.store.db.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionProperty;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;


public class DefaultSessionCapabilityTest {

    private DefaultSessionCapability sessionCapability;

    private String queryString;
    private String version; 
    private String capabilityName; 
    private String namespace; 
    private List<Capability> capabilityList;
    private Set<Property> properties;

    ArgumentCaptor<Capability> capabilityArgumentCaptor;
    ArgumentCaptor<String> value;
    ArgumentCaptor<Map<String,String>> propertiesArgumentCaptor;
    ArgumentCaptor<String> stringArgumentCaptor;
    ArgumentCaptor<Petal> petalArgumentCaptor ;


    @Mock
    private EntityManager entityManager; 
    @Mock
    private ISessionPetal sessionPetal;
    @Mock
    private ISessionProperty sessionProperty;
    @Mock 
    private Capability mockcapability;
    @Mock
    private Query query;
    @Mock
    private Petal petal;
    @Mock
    private Vendor vendor;
    @Mock
    private Set<Petal> petals;
    @Mock
    private Iterator<Petal> itP;




    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        namespace = "DB";
        version ="1.0";
        capabilityName = "name";
        properties = new HashSet<>();
        capabilityList = new ArrayList<Capability>() ;

        sessionCapability = new DefaultSessionCapability();
        sessionCapability.setEntityManager(entityManager);       
        sessionCapability.setSessionPetal(sessionPetal);
        sessionCapability.setSessionProperty(sessionProperty);

        capabilityArgumentCaptor = ArgumentCaptor.forClass(Capability.class);
        value = ArgumentCaptor.forClass(String.class);
        stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        petalArgumentCaptor = ArgumentCaptor.forClass(Petal.class);

        when(sessionPetal.findPetal(any(Vendor.class), anyString(), anyString())).thenReturn(petal);

        when(entityManager.createNamedQuery(anyString())).thenReturn(query);

        when(mockcapability.getVersion()).thenReturn(version);
        when(mockcapability.getNamespace()).thenReturn(namespace);
        when(mockcapability.getCapabilityName()).thenReturn(capabilityName);


    }


    @Test
    /**
     * Test to check that adding a capability     
     */
    public void shouldAddCapability() throws EntityAlreadyExistsException {
        //Given : the entity odesn't exist 
        when(query.getSingleResult()).thenReturn(null);
        //When 
        sessionCapability.addCapability(capabilityName, version, namespace, properties);
        //Then 
        verify(entityManager).persist(capabilityArgumentCaptor.capture());
        Assert.assertEquals(capabilityName, capabilityArgumentCaptor.getValue().getCapabilityName());
        Assert.assertEquals(version, capabilityArgumentCaptor.getValue().getVersion());
        Assert.assertEquals(namespace, capabilityArgumentCaptor.getValue().getNamespace());
        Assert.assertEquals(properties, capabilityArgumentCaptor.getValue().getProperties());
        Assert.assertTrue(capabilityArgumentCaptor.getValue().getPetals().isEmpty());
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shoudThrowExceptionWhenAddCauseEntityAlreadyExits() throws EntityAlreadyExistsException {
        //Given : The entity already exist in database
        when(query.getSingleResult()).thenReturn(mockcapability);

        //when
        sessionCapability.addCapability("capabilityName",version, "namespace", properties);
        sessionCapability.addCapability("capabilityName",version, "namespace", properties);

        //Then throw a new EntityAlreadyExistsException
    }

    @Test
    /**
     * Test to check the search of a capability 
     */
    public void shouldFindCapability(){
        //Given
        queryString = "CapabilityByName";
        when(query.getSingleResult()).thenReturn(mockcapability);
        //When 
        sessionCapability.findCapability("capabilityName",version);
        //Then 
        verify(entityManager).createNamedQuery(stringArgumentCaptor.capture());
        Assert.assertEquals(queryString, stringArgumentCaptor.getValue());
        verify(query).getSingleResult();

    }

    @Test
    /**
     *Test to check deleting capability
     */
    public void shouldRemoveCapability() throws NoEntityFoundException{
        //Given : There is 2 petals which provide this capability 
        when(query.getSingleResult()).thenReturn(mockcapability);

        when(mockcapability.getPetals()).thenReturn(petals);
        when(petals.iterator()).thenReturn(itP);
        when(itP.hasNext()).thenReturn(true,true,false);

        //When  
        sessionCapability.deleteCapability(capabilityName,version);

        //Then 
        verify(mockcapability).getPetals();


        verify(sessionPetal,times(2)).removeCapability((Petal) anyObject(), capabilityArgumentCaptor.capture());

        Assert.assertEquals(mockcapability, capabilityArgumentCaptor.getValue());
        verify(entityManager).remove(mockcapability);
    }


    @Test
    /**
     *Test to check if the feature to add petal to a capability works well  
     */
    public void shouldAddPetalToCapability() throws NoEntityFoundException
    {
        //Given 
        ArgumentCaptor<Petal> petalArgument = ArgumentCaptor.forClass(Petal.class);

        when(query.getSingleResult()).thenReturn(mockcapability);
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

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseAddingPetalToCapabilityInexistent() throws NoEntityFoundException{

        //Given : The capability doesn't exist in the database
        when(query.getSingleResult()).thenReturn(null);

        //When trying to add a petal to this capability 

        sessionCapability.addPetal(mockcapability, petal);

        //Then throw a new EntityNotFoundException
    }


    @Test
    /**
     *Test to check if the feature to remove petal from a capability works well  
     */
    public void shouldRemovePetalFromCapabilityPetals() throws NoEntityFoundException
    {
        //Given 
        when(query.getSingleResult()).thenReturn(mockcapability);
        when(mockcapability.getPetals()).thenReturn(petals);
        when(petals.isEmpty()).thenReturn(false);

        //When

        sessionCapability.removePetal(mockcapability, petal);

        //Then 
        verify(mockcapability).getPetals();
        verify(petals).remove(petalArgumentCaptor.capture());
        Assert.assertEquals(petal,petalArgumentCaptor.getValue());

        verify(entityManager).merge(capabilityArgumentCaptor.capture());
        Assert.assertEquals(mockcapability,capabilityArgumentCaptor.getValue());

    }

    @Test
    /**
     *Test to check if the feature to remove petal from a capability works well  
     */
    public void shouldRemoveTheOnlyPetalGivenACapability() throws NoEntityFoundException
    {
        //Given 
        when(query.getSingleResult()).thenReturn(mockcapability);
        when(mockcapability.getPetals()).thenReturn(petals);
        when(petals.isEmpty()).thenReturn(true);

        //When

        sessionCapability.removePetal(mockcapability, petal);

        //Then 
        verify(mockcapability).getPetals();
        verify(petals).remove(petalArgumentCaptor.capture());
        Assert.assertEquals(petal,petalArgumentCaptor.getValue());

        verify(entityManager).remove(capabilityArgumentCaptor.capture());
        Assert.assertEquals(mockcapability,capabilityArgumentCaptor.getValue());

    }

    @Test
    /**
     * Test to check if the feature to collect petals which provides a capability works well
     */
    public void shouldCollectPetals() throws NoEntityFoundException{
        //Given 
        when(query.getSingleResult()).thenReturn(mockcapability);
        //When
        sessionCapability.collectPetals(capabilityName,version);
        //Then
        verify(mockcapability).getPetals();
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsFromCapabilityInexistent() throws NoEntityFoundException
    {
        //Given : The capability given in parameter doesn't exist in the database 
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCapability.collectPetals(capabilityName,version);
        //Then throw a new EntityNotFoundException
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectCauseEntityNotExistent() throws NoEntityFoundException {
        //Given 
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCapability.collectPetals(capabilityName,version);
    }

    @Test
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

    @Test
    public void shouldUpdateNamespace() throws NoEntityFoundException {

        //Given
        when(query.getSingleResult()).thenReturn(mockcapability);
        //when

        sessionCapability.updateNamespace(mockcapability, "namespace");

        //then
        verify(mockcapability).setNamespace(value.capture());
        Assert.assertEquals("namespace", value.getValue());
        verify(entityManager).merge(mockcapability);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenUpdateNamespaceOfCapabilityInexistent() throws NoEntityFoundException{
        //Given : The capability given as parameter doesn't exist in the database 
        when(query.getSingleResult()).thenReturn(null);
        //when

        sessionCapability.updateNamespace(mockcapability, namespace);

        //Then throw a new EntityNotFoundException
    }
    
    @Test
    public void shouldUpdateProperties() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(mockcapability);
        //when
        sessionCapability.updateProperties(mockcapability, properties);

        //then
        verify(mockcapability).setProperties(properties);
        verify(entityManager).merge(mockcapability);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenUpdatePropertiesOfCapabilityInexistent() throws NoEntityFoundException{
        //Given : The capability given as parameter doesn't exist in the database 
        when(query.getSingleResult()).thenReturn(null);
        //when

        sessionCapability.updateProperties(mockcapability, properties);

        //Then throw a new EntityNotFoundException
    }

}
