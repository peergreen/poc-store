package com.peergreen.store.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;

public class DefaultPetalControllerTestCase {

    @Mock
    private ISessionCapability capabilitySession;
    @Mock
    private ISessionCategory categorySession;
    @Mock
    private ISessionPetal petalSession;
    @Mock
    private ISessionRequirement requirementSession;
    @Mock
    private ISessionVendor vendorSession;
    @Mock
    private IPetalsPersistence petalPersistence;

    private DefaultPetalController petalController;

    @BeforeClass
    public void oneTimeSetUp() {
        petalController = new DefaultPetalController();
        MockitoAnnotations.initMocks(this);
        petalController.bindCapabilitySession(capabilitySession);
        petalController.bindCategorySession(categorySession);
        petalController.bindPetalSession(petalSession);
        petalController.bindRequirementSession(requirementSession);
        petalController.bindVendorSession(vendorSession);
        petalController.bindPetalPersistence(petalPersistence);
    }

    //    Map<String, Object> getPetalMetadata(Vendor vendor, String artifactId, String version);

    @Test
    public void testGetPetal() {
        String vendorName = "Peergreen";
        // create vendor entity instance
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        // create petal entity instance
        Petal petal = new Petal();
        petal.setVendor(vendor);
        petal.setArtifactId(artifactId);
        petal.setVersion(version);

        File binary = new File("/home/toto/petal.jar");

        // verify getPetal is called when petalPersistence.getPetal is used
        when(petalPersistence.getPetal(vendorName, artifactId, version)).thenReturn(binary);

        // verify persistence.getPetal is called
        petalController.getPetal(vendor, artifactId, version);
        verify(petalPersistence).getPetalFromLocal(vendorName, artifactId, version);
    }

    @Test
    public void testAddPetal() {
        String vendorName = "Peergreen";
        // create vendor entity instance
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);

        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        File petal = new File("/home/toto/petal.jar");

        // nothing to mock, void return

        // verify petalPersitence.addToLocal is called
        petalController.addPetal(vendor, artifactId,
                version, "", new Category(),
                new HashSet<Requirement>(),
                new HashSet<Capability>(),
                petal);
        verify(petalPersistence).addToLocal(vendorName, artifactId, version, petal);
    }

    @Test
    public void testRemovePetal() {
        // manage petal part
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // manage group part
        Group group1 = new Group();
        group1.setGroupname("admin");
        Group group2 = new Group();
        group2.setGroupname("web devs");

        // initialize petal
        Petal petal = new Petal();
        petal.setVendor(vendor);
        petal.setArtifactId(artifactId);
        petal.setVersion(version);
        Set<Group> groups = new HashSet<Group>();
        groups.add(group1);
        groups.add(group2);
        petal.setGroupSet(groups);

        // mock => return needed objects to verify method call
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectGroups(petal)).thenReturn(groups);

        // verify petalSession.removeAccesToGroup is called
        // as many times there are allowed groups - 1 (admin group)
        petalController.removePetal(vendor, artifactId, version);
        verify(petalSession, times(1)).removeAccesToGroup(eq(petal), any(Group.class));
    }

    /*
    Petal updatePetal(Vendor vendor, String artifactId,
            String version, String description, Category category,
            Set<Requirement> requirements, Set<Capability> capabilities,
            File petalBinary);
     */

    @Test
    public void testCreateCapability() {
        String capabilityName = "my capability";
        String namespace = "service";
        Properties properties = new Properties();
        Petal petal = new Petal();

        // verify capabilitySession.addCapability(...) is called
        petalController.createCapability(capabilityName, namespace, properties, petal);
        verify(capabilitySession).addCapability(capabilityName, namespace, properties, petal);
    }

    @Test
    public void testCollectCapabilities() {
        // create a petal
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // create empty return list
        Collection<Capability> list = new ArrayList<>();

        // mock petalSession.collectCapabilites behavior
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectCapabilities(petal)).thenReturn(list);

        // verify petalSession.collectCapabilites(...) is called
        petalController.collectCapabilities(vendor, artifactId, version);
        verify(petalSession).collectCapabilities(petal);
    }

    @Test
    public void testAddCapability() {
        // capability attributes
        Capability capability = new Capability();
        // petal
        Petal petal = new Petal();
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => return the specified petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.addCapability(petal, capability)).thenReturn(petal);

        // verify capabilitySession.addCapability(...) is called
        petalController.addCapability(vendor, artifactId, version, capability);
        verify(petalSession).addCapability(petal, capability);
    }

    @Test
    public void testRemoveCapability() {
        Petal petal = new Petal();
        Capability capability = new Capability();
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => always return target petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.remove
        petalController.removeCapability(vendor, artifactId, version, capability);
        verify(petalSession).removeCapability(petal, capability);
    }

    @Test
    public void testCreateRequirement() {
        String requirementName = "my requirement";
        String filter = "namespace=service";


        // verify requirementSession.addRequirement(...) is called
        petalController.createRequirement(requirementName, filter);
        verify(requirementSession).addRequirement(requirementName, filter);
    }

    @Test
    public void testCollectRequirements() {
        // create a petal
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // create empty return list
        Collection<Requirement> list = new ArrayList<>();

        // mock petalSession.collectCapabilites behavior
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectRequirements(petal)).thenReturn(list);

        // verify petalSession.collectCapabilites(...) is called
        petalController.collectRequirements(vendor, artifactId, version);
        verify(petalSession).collectRequirements(petal);
    }

    @Test
    public void testAddRequirement() {
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        Requirement requirement = new Requirement();

        // mock => always return targeted petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.addRequirement(...) is called
        petalController.addRequirement(vendor, artifactId, version, requirement);
        verify(petalSession).addRequirement(petal, requirement);
    }

    @Test
    public void testRemoveRequirement() {
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        Requirement requirement = new Requirement();

        // mock => always return targeted petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.removeRequirement(...) is called
        petalController.removeRequirement(vendor, artifactId, version, requirement);
        verify(petalSession).removeRequirement(petal, requirement);
    }

    @Test
    public void testCreateCategory() {
        String categoryName = "Administration";

        // verify categorySession.addCategory(...) is called
        petalController.createCategory(categoryName);
        verify(categorySession).addCategory(categoryName);
    }

    @Test
    public void testGetCategory() {
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // mock => always return targeted petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.getCategory(...) is called
        petalController.getCategory(vendor, artifactId, version);
        verify(petalSession).getCategory(petal);
    }

    @Test
    public void testSetCategory() {
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        Category category = new Category();

        // mock => always return targeted petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.getCategory(...) is called
        petalController.setCategory(vendor, artifactId, version, category);
        verify(petalSession).addCategory(petal, category);
    }

    @Test
    public void testCreateVendor() {
        String vendorName = "Peergreen";
        String vendorDescription = "Software company started by the core team who created JOnAS";
        
        // verify vendorSession.addVendor(...) is called
        petalController.createVendor(vendorName, vendorDescription);
        verify(vendorSession).addVendor(vendorName, vendorDescription);
    }

}