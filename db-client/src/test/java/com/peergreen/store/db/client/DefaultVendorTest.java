package com.peergreen.store.db.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultVendor;

public class DefaultVendorTest {

    private DefaultVendor vendorSession;
    private HashMap<String, Vendor> vendors;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Petal petal;
    @Mock
    private Vendor vendor;

    @BeforeClass
    public void setUp() {
        vendorSession = new DefaultVendor();
        MockitoAnnotations.initMocks(this);
        vendorSession.setEntityManager(entityManager);
        vendors = new HashMap<String, Vendor>();

        doAnswer(new Answer<Vendor>() {
            @Override
            public Vendor answer(InvocationOnMock invocation) throws Throwable {
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
        Assert.assertNotNull(entityManager.find(Vendor.class, vendor.getVendorName()));
    }

    @Test(dependsOnMethods = {"testAddVendor"})
    public void testDeleteVendor() {
        vendorSession.deleteVendor(vendor.getVendorName());
        verify(entityManager).remove(vendor);
        Assert.assertNull(entityManager.find(Vendor.class, vendor.getVendorName()));
    }

    @Test
    public void testFindVendor() {
        entityManager.clear();

        // insert vendor instance in the entity manager
        vendor = new Vendor();
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);

        vendorSession.findVendor(vendor.getVendorName());
        Assert.assertTrue(entityManager.find(Vendor.class, vendor.getVendorName()).equals(vendor));
    }

    @Test
    public void testCollectPetals() {
        entityManager.clear();

        // a vendor with 2 petals
        Vendor v1 = new Vendor();
        v1.setVendorName("Peergreen");

        // add several petals associated with its name
        Petal p1 = new Petal();
        p1.setArtifactId("p1");
        Petal p2 = new Petal();
        p2.setArtifactId("p2");
        Set<Petal> petalsV1 = new HashSet<>();
        petalsV1.add(p1);
        petalsV1.add(p2);
        v1.setPetals(petalsV1);
        // persist v1 into database
        entityManager.persist(v1);

        
        // an another vendor
        Vendor v2 = new Vendor();
        v2.setVendorName("Adobe");
        // add a petal associated with its name
        Petal p3 = new Petal();
        p3.setArtifactId("p3");
        Set<Petal> petalsV2 = new HashSet<>();
        petalsV2.add(p3);
        v2.setPetals(petalsV2);
        // persist v2 into database
        entityManager.persist(v2);

        // verify collect method collect just a specific vendor petals
        vendorSession.collectPetals(v1.getVendorName());
        Assert.assertTrue(entityManager.find(Vendor.class, v1.getVendorName()).getPetals().size() == 2);
    }

    @Test
    public void testAddPetal() {
        entityManager.clear();

        // insert vendor instance in the entity manager
        vendor = mock(Vendor.class);
        vendor.setVendorName("Peergreen");
        entityManager.persist(vendor);
        Set<Petal> newPtls = new HashSet<Petal>();
        when(vendor.getPetals()).thenReturn(newPtls);
        
        // create a petal to add
        petal = mock(Petal.class);
        petal.setArtifactId("Tomcat HTTP server");
        petal.setVersion("7.0.39");
   

        // verify relationship update
        vendorSession.addPetal(vendor, petal);

        // verify petal added to vendor petal list
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
        when(vendor.getPetals()).thenReturn(new HashSet<Petal>());
        vendorSession.addPetal(vendor, petal);
        vendorSession.addPetal(vendor, petal);
        
        Assert.assertTrue(entityManager.find(Vendor.class, vendor.getVendorName()).getPetals().size() == 1);
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
        Assert.assertTrue(entityManager.find(Vendor.class, vendor.getVendorName()).getPetals().isEmpty());
    }

}
