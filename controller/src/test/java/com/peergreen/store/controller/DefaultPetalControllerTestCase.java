package com.peergreen.store.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.impl.DefaultPetalController;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultPetalControllerTestCase {

    @Mock
    private ISessionCapability capabilitySession;
    @Mock
    Capability mockcapability;
    @Mock
    private ISessionCategory categorySession;
    @Mock
    private ISessionPetal petalSession;
    @Mock
    private ISessionRequirement requirementSession;
    @Mock
    Requirement mockrequirement;
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
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        // verify getPetal is called when petalPersistence.getPetal is used
        when(petalPersistence.getPetal(vendor, artifactId, version)).thenReturn(binary);

        // verify persistence.getPetal is called
        petalController.getPetal(vendorName, artifactId, version);

        verify(petalPersistence).getPetalFromLocal(vendor, artifactId, version);
    }

    @Test
    public void testAddPetal() throws NoEntityFoundException, EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        // create vendor entity instance
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);

        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        File petal = new File("/home/toto/petal.jar");

        // nothing to mock, void return
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        // verify petalPersitence.addToLocal is called
        petalController.addPetal(vendorName, artifactId,
                version, "", new Category(),
                new HashSet<Requirement>(),
                new HashSet<Capability>(),
                Origin.LOCAL,
                petal);
        verify(petalPersistence).addToLocal(vendor, artifactId, version, petal);
    }

    @Test
    public void testRemovePetal() throws NoEntityFoundException {
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
        petal.setGroups(groups);

        // mock => return needed objects to verify method call
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectGroups(petal)).thenReturn(groups);

        // verify petalSession.removeAccesToGroup is called
        // as many times there are allowed groups - 1 (admin group)
        petalController.removePetal(vendorName, artifactId, version);
        //verify(petalSession, times(1)).removeAccesToGroup(eq(petal), any(Group.class));
    }

//    @Test
    public void testCreateCapability() throws EntityAlreadyExistsException {
        String capabilityName = "my capability";
        String namespace = "service";
        String version = "1.0";
        Set<Property> properties = new HashSet<>();

        // verify capabilitySession.addCapability(...) is called
        petalController.createCapability(capabilityName,version, namespace, properties);
        verify(capabilitySession).addCapability(capabilityName, version, namespace, properties);
    }

    @Test
    public void testCollectCapabilities() throws NoEntityFoundException {
        // create a petal
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // create empty return list
        Collection<Capability> list = new ArrayList<>();

        // mock petalSession.collectCapabilites behavior
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectCapabilities(petal)).thenReturn(list);

        // verify petalSession.collectCapabilites(...) is called
        petalController.collectCapabilities(vendorName, artifactId, version);
        verify(petalSession).collectCapabilities(petal);
    }

    @Test
    public void shouldReturnNullWhenCollectCapabilitiesCausePetalNonExistent() throws NoEntityFoundException {
        //Given
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "";
        String version = "";
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(null);

        //when
        Collection<Capability> capabilities = petalController.collectCapabilities(vendorName, artifactId, version);

        Assert.assertTrue(capabilities.size() == 0);
    }

    @Test
    public void testAddCapability() throws NoEntityFoundException {
        // capability attributes
        Capability capability = new Capability();
        // petal
        Petal petal = new Petal();
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => return the specified petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.addCapability(petal, capability)).thenReturn(petal);

        // verify capabilitySession.addCapability(...) is called
        petalController.addCapability(vendorName, artifactId, version, capability);
        verify(petalSession).addCapability(petal, capability);
    }

    @Test
    public void testRemoveCapability() throws NoEntityFoundException {
        Petal petal = new Petal();
        Capability capability = new Capability();
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => always return target petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.remove
        petalController.removeCapability(vendorName, artifactId, version, capability);
        verify(petalSession).removeCapability(petal, capability);
    }

    @Test
    public void testCreateRequirement() throws EntityAlreadyExistsException {
        String requirementName = "my requirement";
        String namespace = "";
        String filter = "namespace=service";

        // verify requirementSession.addRequirement(...) is called
        petalController.createRequirement(requirementName,namespace, filter);
        verify(requirementSession).addRequirement(requirementName,namespace, filter);
    }

//    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowEntityExistExceptionForRequirement() throws EntityAlreadyExistsException {
        String requirementName = "requirement";
        String namespace = "service";
        String filter = "version=2.0";

        petalController.createRequirement(requirementName, namespace, filter);
        petalController.createRequirement(requirementName, namespace, filter);
    }

    @Test
    public void testCollectRequirements() throws NoEntityFoundException {
        // create a petal
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // create empty return list
        Collection<Requirement> list = new ArrayList<>();

        // mock petalSession.collectCapabilites behavior
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectRequirements(petal)).thenReturn(list);

        // verify petalSession.collectCapabilites(...) is called
        petalController.collectRequirements(vendorName, artifactId, version);
        verify(petalSession).collectRequirements(petal);
    }

    @Test
    public void shouldReturnNullWhenCollectRequirementCausePetalNonExistent() throws NoEntityFoundException {
        //Given
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "";
        String version = "";
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(null);

        //when
        Collection<Requirement> requirements = petalController.collectRequirements(vendorName, artifactId, version);

        Assert.assertTrue(requirements.size() == 0);
    }

    @Test
    public void testAddRequirement() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        Requirement requirement = mock(Requirement.class);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.addRequirement(...) is called
        petalController.addRequirement(vendorName, artifactId, version, requirement);
        verify(petalSession).addRequirement(petal, requirement);
    }

    @Test
    public void testRemoveRequirement() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        Requirement requirement = mock(Requirement.class);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.removeRequirement(...) is called
        petalController.removeRequirement(vendorName, artifactId, version, requirement);
        verify(petalSession).removeRequirement(petal, requirement);
    }

    @Test
    public void testGetCategory() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.getCategory(...) is called
        petalController.getCategory(vendorName, artifactId, version);
        verify(petalSession).getCategory(petal);
    }

    @Test
    public void testSetCategory() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        Category category = new Category();

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        // verify petalSession.getCategory(...) is called
        petalController.setCategory(vendorName, artifactId, version, category);
        verify(petalSession).addCategory(petal, category);
    }

   // @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSetCategoryCausePetalNonExistent() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Category category = new Category();

        // mock => always return targeted petal
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(null);

        // verify petalSession.getCategory(...) is called
        petalController.setCategory(vendorName, artifactId, version, category);
    }

//    @Test
    public void testCreateVendor() throws EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        String vendorDescription = "Software company started by the core team who created JOnAS";

        // verify vendorSession.addVendor(...) is called
        petalController.createVendor(vendorName, vendorDescription);
        verify(vendorSession).addVendor(vendorName, vendorDescription);
    }
}