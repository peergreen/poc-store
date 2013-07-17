package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultVendor;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultVendorTest {

    private DefaultVendor vendorSession;
    ArgumentCaptor<Vendor> vendorArgument;
    ArgumentCaptor<Petal> petalArgument;
    ArgumentCaptor<String> id ;
    private String queryString;
    
    String vendorName ;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Petal petal;
    @Mock
    private Vendor mockvendor;
    @Mock
    private Set<Petal> petals;
    @Mock
    private Query query;


    @BeforeMethod
    public void setUp() {
        vendorSession = new DefaultVendor();
        MockitoAnnotations.initMocks(this);
        vendorSession.setEntityManager(entityManager);
        vendorArgument = ArgumentCaptor.forClass(Vendor.class);
        petalArgument = ArgumentCaptor.forClass(Petal.class);
        id = ArgumentCaptor.forClass(String.class);
        vendorName = "toto";
        queryString = "Vendor.findAll";
    }

    @Test
    public void shouldAddVendor() throws EntityAlreadyExistsException {
        //Given
        String vendorName = "toto";
        String vendorDescription = "SSII";
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(null);
        //When
        vendorSession.addVendor(vendorName, vendorDescription);
        //Then
        verify(entityManager).persist(vendorArgument.capture());
        Assert.assertEquals(vendorName, vendorArgument.getValue().getVendorName());
        Assert.assertEquals(vendorDescription, vendorArgument.getValue().getVendorDescription());
        Assert.assertNotNull(vendorArgument.getValue().getPetals());
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddCauseAlreadyExist() throws EntityAlreadyExistsException {

        //Given
        String vendorName = "toto";
        String vendorDescription = "SSII";
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(mockvendor);
        //When
        vendorSession.addVendor(vendorName, vendorDescription);
    }

    @Test
    public void shouldDeleteVendor() {
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(mockvendor);
        //When
        vendorSession.deleteVendor(vendorName);
        //Then
        verify(entityManager).find(eq(Vendor.class), id.capture());
        Assert.assertEquals(vendorName, id.getValue());
        verify(entityManager).remove(vendorArgument.capture());
        Assert.assertSame(mockvendor, vendorArgument.getValue());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteCauseEntityNotExisting() {
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(null);
        //When
        vendorSession.deleteVendor(vendorName);
    }

    @Test
    public void shouldFindVendor() {
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(mockvendor);
        //When
        vendorSession.findVendor(vendorName);
        //Then
        verify(entityManager).find(eq(Vendor.class), id.capture());
        Assert.assertEquals(vendorName, id.getValue());
    }

    @Test
    public void testCollectPetals() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(mockvendor);

        //When
        vendorSession.collectPetals(vendorName);
        //Then
        verify(entityManager).find(eq(Vendor.class), id.capture());
        Assert.assertEquals(vendorName, id.getValue());
        verify(mockvendor).getPetals();
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsCauseEntityNotExisting() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(Vendor.class), anyString())).thenReturn(null);
        //When
        vendorSession.collectPetals(vendorName);
    }

    @Test
    public void shouldAddPetal() {
        //Given
        when(mockvendor.getPetals()).thenReturn(petals);
        //When
        vendorSession.addPetal(mockvendor, petal);
        //Then
        verify(mockvendor).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertSame(petal,petalArgument.getValue());
        verify(mockvendor).setPetals(petals);
        verify(entityManager).merge(mockvendor);
    }

    @Test
    public void shouldRemovePetal() {
        //Given
        when(mockvendor.getPetals()).thenReturn(petals);
        //When
        vendorSession.removePetal(mockvendor, petal);
        //Then
        verify(mockvendor).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertEquals(petal, petalArgument.getValue());
        verify(entityManager).merge(vendorArgument.capture());
        Assert.assertEquals(mockvendor, vendorArgument.getValue());
    }

    @Test 
    public void shouldCollectVendors() {
        //given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        //when
        vendorSession.collectVendors();
        //then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }
}