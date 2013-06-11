package com.peergreen.store.db.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultVendor;

public class DefaultVendorTest {

    private DefaultVendor vendorSession;
    private Properties properties;
    private HashMap<String, Vendor> vendors;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Petal petal;
    @Mock
    private Vendor vendor;
    
    @BeforeMethod
    public void setUp() {
        vendorSession = new DefaultVendor();
        MockitoAnnotations.initMocks(this);
        vendorSession.setEntityManager(entityManager);
        properties = new Properties();
        vendors = new HashMap<String, Vendor>();

        doAnswer(new Answer<Vendor>() {
            @Override
            public Vendor answer(InvocationOnMock invocation) throws Throwable {
                // TODO Auto-generated method stub
                Object[] args = invocation.getArguments();
                Vendor vendor = (Vendor) args[0];
                String key = vendor.getVendorName();
                vendors.put(key, vendor);
                
                return vendor; 
            }
        }).when(entityManager).persist(any(Vendor.class));

        doAnswer(new Answer<Vendor>() {
            @Override
            public Vendor answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String key = (String) args[1];
                if(vendors.containsKey(key) == true) {
                    return vendors.get(key);
                } else {
                    return null;
                }
            }
        }).when(entityManager).find(eq(Vendor.class), anyString());

        doAnswer(new Answer<Vendor>() {
            @Override
            public Vendor answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Vendor vendor = (Vendor) args[0];
                String key = vendor.getVendorName();
                vendors.put(key, vendor);
                
                return vendors.get(key);
            }
        }).when(entityManager).merge(any(Vendor.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Vendor vendor = (Vendor) args[0];
                String key = vendor.getVendorName();
                vendors.remove(key);
                
                return null;
            }
        }).when(entityManager).remove(any(Vendor.class));
    }
    
    @Test
    public void testAddVendor() {
        entityManager.clear();

        // insert vendor instance in the entity manager
        vendor = mock(Vendor.class);
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);
        
        vendorSession.addVendor(vendor.getVendorName(), "");
        verify(entityManager).persist(vendor);
        Assert.assertNotNull(entityManager.find(Vendor.class, vendor));
    }
    
    @Test(dependsOnMethods = {"testAddVendor"})
    public void testDeleteVendor() {
        vendorSession.deleteVendor(vendor.getVendorName());
        verify(entityManager).remove(vendor);
        Assert.assertNull(entityManager.find(Vendor.class, vendor));
    }
    
    @Test
    public void testFindVendor() {
        entityManager.clear();

        // insert vendor instance in the entity manager
        vendor = mock(Vendor.class);
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);
        
        vendorSession.findVendor(vendor.getVendorName());
        verify(entityManager).find(Vendor.class, vendor.getVendorName());
        Assert.assertTrue(entityManager.find(Vendor.class, vendor.getVendorName()).equals(vendor));
    }
    
    @Test
    public void testCollectPetals() {
        entityManager.clear();
        
        // insert vendor instance in the entity manager
        Vendor v1 = new Vendor();
        vendor.setVendorName("Peergreen");
        entityManager.persist(v1);
        
        // add several petals associated with its name
        Petal p1 = new Petal();
        p1.setArtifactId("p1");
        vendorSession.addPetal(v1, p1);
        Petal p2 = new Petal();
        p2.setArtifactId("p2");
        vendorSession.addPetal(v1, p2);
        
        // insert an another vendor in the databse
        Vendor v2 = new Vendor();
        vendor.setVendorName("Adobe");
        entityManager.persist(vendor);
        
        // add a petal associated with its name
        Petal p3 = new Petal();
        p3.setArtifactId("p3");
        vendorSession.addPetal(v2, p3);
        
        // verify collect method collect just a specific vendor petals
        vendorSession.collectPetals(vendor.getVendorName());
        Assert.assertTrue(entityManager.find(Vendor.class, v1).getPetals().size() == 2);
    }
    
    @Test
    public void testAddPetal() {
        entityManager.clear();
        
        // insert vendor instance in the entity manager
        vendor = mock(Vendor.class);
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);
        
        // create a petal to add
        petal = mock(Petal.class);
        petal.setArtifactId("Tomcat HTTP server");
        petal.setVersion("7.0.39");
 
        // verify relationship update
        vendorSession.addPetal(vendor, petal);
        verify(petal).setVendor(vendor);
        verify(entityManager).merge(petal);

        // verify petal added to vendor petal list
        verify(entityManager).find(Vendor.class, vendor.getVendorName());
        verify(vendor).setPetals(anySetOf(Petal.class));
        verify(entityManager).merge(vendor);
        
        // verify petal had correctly been added
        Assert.assertTrue(entityManager.find(Vendor.class, vendor.getVendorName()).getPetals().size() == 1);
    }
    
    @Test
    public void testAddPetalAlreadyExists() {
        entityManager.clear();
        
        // insert vendor instance in the entity manager
        vendor = mock(Vendor.class);
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);
        
        // create a petal to add
        petal = mock(Petal.class);
        petal.setArtifactId("Tomcat HTTP server");
        petal.setVersion("7.0.39");
 
        // verify petal can't be added twice
        vendorSession.addPetal(vendor, petal);
        vendorSession.addPetal(vendor, petal);
        Assert.assertTrue(entityManager.find(Vendor.class, vendor).getPetals().size() == 1);
    }
    
    @Test
    public void testRemovePetal() {
        entityManager.clear();
        
        // insert vendor instance in the entity manager
        vendor = mock(Vendor.class);
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);
        
        // create a petal to add
        petal = mock(Petal.class);
        petal.setArtifactId("Tomcat HTTP server");
        petal.setVersion("7.0.39");
 
        // associate this petal to the vendor
        vendorSession.addPetal(vendor, petal);
        
        // verify petal is effectively removed from list
        vendorSession.removePetal(vendor, petal);
        Assert.assertTrue(entityManager.find(Vendor.class, vendor).getPetals().isEmpty());
    }

}
