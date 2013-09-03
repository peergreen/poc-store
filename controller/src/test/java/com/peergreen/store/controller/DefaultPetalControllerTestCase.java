package com.peergreen.store.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.impl.DefaultPetalController;
import com.peergreen.store.controller.util.DependencyRequest;
import com.peergreen.store.controller.util.DependencyResult;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
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
    @Mock
    private ISessionGroup groupSession;

    private DefaultPetalController petalController;

    @BeforeMethod
    public void oneTimeSetUp() {
        petalController = new DefaultPetalController();
        MockitoAnnotations.initMocks(this);
        petalController.bindCapabilitySession(capabilitySession);
        petalController.bindCategorySession(categorySession);
        petalController.bindPetalSession(petalSession);
        petalController.bindRequirementSession(requirementSession);
        petalController.bindVendorSession(vendorSession);
        petalController.bindPetalPersistence(petalPersistence);
        petalController.bindGroupSession(groupSession);
    }

    @Test
    public void shouldGetPetalMetadata() throws NoEntityFoundException{

        //Given
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        Vendor vendor = mock(Vendor.class);
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        Petal petal = mock(Petal.class);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        Category category = mock(Category.class);

        Requirement req1 = mock(Requirement.class);
        Requirement req2 = mock(Requirement.class);
        Set<Requirement> requirements = new HashSet<>();
        requirements.add(req1);
        requirements.add(req2);

        Capability cap1 = mock(Capability.class);
        Capability cap2 = mock(Capability.class);
        Set<Capability> capabilities = new HashSet<>();
        capabilities.add(cap1);
        capabilities.add(cap2);     

        when(petalSession.getCategory(petal)).thenReturn(category);
        when(petalSession.collectCapabilities(petal)).thenReturn(capabilities);
        when(petalSession.collectRequirements(petal)).thenReturn(requirements);

        //When
        Map<String, Object> result = petalController.getPetalMetadata(vendorName, artifactId, version);

        //Then 
        Assert.assertSame(result.get("vendor"), vendor);  
        Assert.assertSame(result.get("artifactId"), artifactId);
        Assert.assertSame(result.get("version"), version);
        Assert.assertSame(result.get("description"), null);
        Assert.assertSame(result.get("category"), category);
        Assert.assertSame(result.get("requirements"), requirements);
        Assert.assertSame(result.get("capabilities"), capabilities);

    }

    @Test
    public void testGetTransitiveDependencies() throws NoEntityFoundException{

        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Vendor vendor = mock(Vendor.class);
        DependencyRequest request = mock(DependencyRequest.class);
        when(request.getVendor()).thenReturn(vendor);
        when(request.getArtifactId()).thenReturn(artifactId);
        when(request.getVersion()).thenReturn(version); 

        Requirement req1 = mock(Requirement.class);
        Requirement req2 = mock(Requirement.class);
        Set<Requirement> requirements = new HashSet<>();
        requirements.add(req1);
        requirements.add(req2);

        Capability cap1 = mock(Capability.class);
        Capability cap2 = mock(Capability.class);
        Set<Capability> capabilities = new HashSet<>();
        capabilities.add(cap1);
        capabilities.add(cap2);  
        Set<Capability> capabilities2 = new HashSet<>();


        Petal petal = mock(Petal.class);
        Petal petal1 = mock(Petal.class);
        Petal petal2 = mock(Petal.class);
        Set<Petal> petals = new HashSet<>();
        petals.add(petal1);
        petals.add(petal2);
        Set<Petal> petalsEmpty = new HashSet<>();

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petal.getRequirements()).thenReturn(requirements);

        when(requirementSession.findCapabilities(req1)).thenReturn(capabilities);
        when(requirementSession.findCapabilities(req2)).thenReturn(capabilities2);

        when(cap1.getPetals()).thenReturn(petals);
        when(cap2.getPetals()).thenReturn(petalsEmpty);

        //When 
        DependencyResult result = petalController.getTransitiveDependencies(request);
        //Then 
        verify(requirementSession,times(2)).findCapabilities(any(Requirement.class));
        Assert.assertSame(petals, result.getResolvedRequirements().get(req1));

        //  Assert.assertTrue(result.getUnresolvedRequirements().contains(req2));
        Assert.assertTrue(result.getUnresolvedRequirements().contains(req1));

    }


    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenGetPetalMetadata() throws NoEntityFoundException{

        //Given
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        Vendor vendor = mock(Vendor.class);
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        Petal petal = mock(Petal.class);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);


        when(petalSession.getCategory(petal)).thenThrow(new NoEntityFoundException());


        //When
        petalController.getPetalMetadata(vendorName, artifactId, version);



    }
    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddvendorIfAlreadyExists() throws EntityAlreadyExistsException{

        //Given 
        String name = "Peergreen";
        String description = "The vendor";

        when(vendorSession.addVendor(name, description)).thenThrow(new EntityAlreadyExistsException());

        //when
        petalController.createVendor(name, description);
    }

    @Test
    public void shouldCollectAllVendor(){

        //Given
        Vendor v1 = mock(Vendor.class);
        Collection<Vendor> vendors = new HashSet<>();
        vendors.add(v1);
        when(vendorSession.collectVendors()).thenReturn(vendors);       
        //When
        Collection<Vendor> result = petalController.collectVendors(); 
        //Then 
        Assert.assertEquals(vendors, result);
    }

    @Test
    public void shouldCollectAllVendorBis(){

        //Given
        Vendor v1 = mock(Vendor.class);
        Collection<Vendor> vendors = new HashSet<>();
        vendors.add(v1);
        when(vendorSession.collectVendors()).thenReturn(vendors);       
        //When
        Collection<Vendor> result = petalController.collectVendors(); 
        //Then 
        Assert.assertEquals(vendors, result);
    }

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
        when(petalPersistence.getPetal(vendorName, artifactId, version)).thenReturn(binary);

        // verify persistence.getPetal is called
        petalController.getPetal(vendorName, artifactId, version);

        verify(petalSession).findPetal(vendor, artifactId, version);
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
        verify(petalPersistence).addToLocal(vendorName, artifactId, version, petal);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundException() throws NoEntityFoundException, EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        // create vendor entity instance
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);

        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        String description = "petal for TomCat"; 
        Category category = mock(Category.class);
        Origin origin = Origin.LOCAL; 
        File petal = new File("/home/toto/petal.jar");

        Requirement req1 = mock(Requirement.class);
        Requirement req2 = mock(Requirement.class);
        Set<Requirement> requirements = new HashSet<>();
        requirements.add(req1);
        requirements.add(req2);

        Capability cap1 = mock(Capability.class);
        Capability cap2 = mock(Capability.class);
        Set<Capability> capabilities = new HashSet<>();
        capabilities.add(cap1);
        capabilities.add(cap2);     

        // nothing to mock, void return
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.addPetal(vendor, artifactId, version, description,
                category, capabilities, requirements, origin))
                .thenThrow(new NoEntityFoundException());
        // verify petalPersitence.addToLocal is called
        petalController.addPetal(vendorName, artifactId,
                version, description, category,
                requirements,
                capabilities,
                Origin.LOCAL,
                petal);
        verify(petalPersistence).addToLocal(vendorName, artifactId, version, petal);
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowEntityAlreadyExistsException() throws NoEntityFoundException, EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        // create vendor entity instance
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);

        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        String description = "petal for TomCat"; 
        Category category = mock(Category.class);
        Origin origin = Origin.LOCAL; 
        File petal = new File("/home/toto/petal.jar");

        Requirement req1 = mock(Requirement.class);
        Requirement req2 = mock(Requirement.class);
        Set<Requirement> requirements = new HashSet<>();
        requirements.add(req1);
        requirements.add(req2);

        Capability cap1 = mock(Capability.class);
        Capability cap2 = mock(Capability.class);
        Set<Capability> capabilities = new HashSet<>();
        capabilities.add(cap1);
        capabilities.add(cap2);     

        // nothing to mock, void return
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.addPetal(vendor, artifactId, version, description,
                category, capabilities, requirements, origin))
                .thenThrow(new EntityAlreadyExistsException());
        // verify petalPersitence.addToLocal is called
        petalController.addPetal(vendorName, artifactId,
                version, description, category,
                requirements,
                capabilities,
                Origin.LOCAL,
                petal);
        verify(petalPersistence).addToLocal(vendorName, artifactId, version, petal);
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
        group1.setGroupname("Administrator");
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
        verify(groupSession).removePetal(any(Group.class), eq(petal));
    }

    @Test
    public void shouldReturnNullWhenRemovePetal() throws  NoEntityFoundException{
        // manage petal part
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // manage group part
        Group group1 = new Group();
        group1.setGroupname("Administrator");
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
        when(groupSession.removePetal(group2, petal)).thenThrow(new NoEntityFoundException());

        // verify petalSession.removeAccesToGroup is called
        // as many times there are allowed groups - 1 (admin group)
        Petal result = petalController.removePetal(vendorName, artifactId, version);

        //Then
        Assert.assertNull(result);
    }


    @Test
    public void testCreateCapability() throws EntityAlreadyExistsException {
        String capabilityName = "my capability";
        String namespace = "service";
        String version = "1.0";
        Set<Property> properties = new HashSet<>();

        // verify capabilitySession.addCapability(...) is called
        petalController.createCapability(capabilityName,version, namespace, properties);
        verify(capabilitySession).addCapability(capabilityName,version, namespace, properties);
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionForCreateCapability() throws EntityAlreadyExistsException {
        String capabilityName = "my capability";
        String namespace = "service";
        String version = "1.0";
        Set<Property> properties = new HashSet<>();

        when(capabilitySession.addCapability(capabilityName, version, namespace, properties)).thenThrow(new EntityAlreadyExistsException());
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

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenColletCapabilitiesForPetalInexistant() throws NoEntityFoundException{

        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();


        // mock petalSession.collectCapabilites behavior
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectCapabilities(petal)).thenThrow(new NoEntityFoundException());

        // verify petalSession.collectCapabilites(...) is called
        petalController.collectCapabilities(vendorName, artifactId, version);
        verify(petalSession).collectCapabilities(petal);
    }


    @Test
    public void testAddCapability() throws NoEntityFoundException {
        // capability attributes
        String name = "JPA";
        String versionCap = "2.0";
        String namespace = "Provider";
        Set<Property> props = new HashSet<>();
        Capability capability = new Capability(name, versionCap, "provider", props);
        // petal
        Petal petal = new Petal();
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => return the specified petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(capabilitySession.findCapability(name, versionCap, namespace)).thenReturn(capability);
        // verify capabilitySession.addCapability(...) is called
        petalController.addCapability(vendorName, artifactId, version, name, versionCap, namespace);
        verify(petalSession).addCapability(petal, capability);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionForAddCapabilityToPetalNonExistent() throws NoEntityFoundException {
        // capability attributes
        String name = "JPA";
        String versionCap = "2.0";
        String namespace = "Provider";
        Set<Property> props = new HashSet<>();
        Capability capability = new Capability(name, versionCap, "provider", props);
        // petal
        Petal petal = new Petal();
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => return the specified petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(capabilitySession.findCapability(name, versionCap, namespace)).thenReturn(capability);

        when(petalSession.addCapability(petal, capability)).thenThrow(new NoEntityFoundException());
        // verify capabilitySession.addCapability(...) is called
        petalController.addCapability(vendorName, artifactId, version, name, versionCap, namespace);
        verify(petalSession).addCapability(petal, capability);
    }

    @Test
    public void testRemoveCapability() throws NoEntityFoundException {
        Petal petal = new Petal();
        String name = "JPA";
        String versionCap = "2.0";
        String namespace = "Provider";
        Set<Property> props = new HashSet<>();
        Capability capability = new Capability(name, versionCap, "provider", props);

        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => always return target petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(capabilitySession.findCapability(name, versionCap, namespace)).thenReturn(capability);
        // verify petalSession.remove
        petalController.removeCapability(vendorName, artifactId, version,name, versionCap, namespace);
        verify(petalSession).removeCapability(petal, capability);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionForRemoveCapabilityFromPetalNonExistent() throws NoEntityFoundException {
        Petal petal = new Petal();
        String name = "JPA";
        String versionCap = "2.0";
        String namespace = "Provider";
        Set<Property> props = new HashSet<>();
        Capability capability = new Capability(name, versionCap, "provider", props);

        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";

        // mock => always return target petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(capabilitySession.findCapability(name, versionCap, namespace)).thenReturn(capability);
        when(petalSession.removeCapability(petal, capability)).thenThrow(new NoEntityFoundException());
        // verify petalSession.remove
        petalController.removeCapability(vendorName, artifactId, version, name, versionCap, namespace);
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

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowEntityAlreadyExistExceptionForRequirement() throws EntityAlreadyExistsException {
        String requirementName = "requirement";
        String namespace = "service";
        String filter = "version=2.0";

        when(requirementSession.addRequirement(requirementName, namespace, filter)).thenThrow(new EntityAlreadyExistsException());
        petalController.createRequirement(requirementName,namespace, filter);

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

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectRequirementsForPetalInexistant() throws NoEntityFoundException{

        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();


        // mock petalSession.collectCapabilites behavior
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.collectRequirements(petal)).thenThrow(new NoEntityFoundException());

        // verify petalSession.collectCapabilites(...) is called
        petalController.collectRequirements(vendorName, artifactId, version);
        verify(petalSession).collectRequirements(petal);
    }

    @Test
    public void testAddRequirement() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        String name = "Provider";
        Requirement requirement = mock(Requirement.class);
        when(requirement.getRequirementName()).thenReturn(name);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(requirementSession.findRequirement(name)).thenReturn(requirement);
        // verify petalSession.addRequirement(...) is called
        petalController.addRequirement(vendorName, artifactId, version, name);
        verify(petalSession).addRequirement(petal, requirement);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenAddRequirementToPetalInexistant() throws NoEntityFoundException{

        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        String name = "Provider";
        Requirement requirement = mock(Requirement.class);
        when(requirement.getRequirementName()).thenReturn(name);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(requirementSession.findRequirement(name)).thenReturn(requirement);

        when(petalSession.addRequirement(petal, requirement)).thenThrow(new NoEntityFoundException());
        // verify petalSession.addRequirement(...) is called
        petalController.addRequirement(vendorName, artifactId, version, name);
        verify(petalSession).addRequirement(petal, requirement);
    }


    @Test
    public void testRemoveRequirement() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        String name = "Provider";
        Requirement requirement = mock(Requirement.class);
        when(requirement.getRequirementName()).thenReturn(name);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(requirementSession.findRequirement(name)).thenReturn(requirement);
        // verify petalSession.removeRequirement(...) is called
        petalController.removeRequirement(vendorName, artifactId, version, name);
        verify(petalSession).removeRequirement(petal, requirement);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenRemoveRequirementFromPetalInexistant() throws NoEntityFoundException{

        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        String name = "Provider";
        Requirement requirement = mock(Requirement.class);
        when(requirement.getRequirementName()).thenReturn(name);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(requirementSession.findRequirement(name)).thenReturn(requirement);
        when(petalSession.removeRequirement(petal, requirement)).thenThrow(new NoEntityFoundException());
        // verify petalSession.removeRequirement(...) is called
        petalController.removeRequirement(vendorName, artifactId, version, name);
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

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenGetCategoryForPetalInexistent() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.getCategory(petal)).thenThrow(new NoEntityFoundException());
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
        String name = "Bundle";
        Category category = new Category();
        category.setCategoryName(name);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(categorySession.findCategory(name)).thenReturn(category);
        // verify petalSession.getCategory(...) is called
        petalController.setCategory(vendorName, artifactId, version, name);
        verify(petalSession).addCategory(petal, category);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenSetCategoryCausePetalNonExistent() throws NoEntityFoundException {
        Vendor vendor = new Vendor();
        String vendorName = "Peergreen";
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        String name = "Bundle";
        Category category = new Category();
        category.setCategoryName(name);

        // mock => always return targeted petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(categorySession.findCategory(name)).thenReturn(category);
        when(petalSession.addCategory(petal, category)).thenThrow(new NoEntityFoundException());
        // verify petalSession.getCategory(...) is called
        petalController.setCategory(vendorName, artifactId, version, name);
        verify(petalSession).addCategory(petal, category);
    }

    @Test
    public void testCreateVendor() throws EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        String vendorDescription = "Software company started by the core team who created JOnAS";

        // verify vendorSession.addVendor(...) is called
        petalController.createVendor(vendorName, vendorDescription);
        verify(vendorSession).addVendor(vendorName, vendorDescription);
    }

    @Test
    public void testCollectPetalsByCapability() throws NoEntityFoundException{
        String name = "JPA";
        String version ="2.1.0";
        String namespace = "Provider";
        Petal p1 = mock(Petal.class);
        Set<Petal> list = new HashSet<>();
        list.add(p1);
        when(capabilitySession.collectPetals(name, version, namespace)).thenReturn(list);
        //when
        Collection<Petal> result = petalController.getPetalsForCapability(name, version, namespace);
        //Then
        verify(capabilitySession).collectPetals(name, version, namespace);
        Assert.assertEquals(list, result);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsByCapabilityInexistent() throws NoEntityFoundException{
        String name = "JPA";
        String version ="2.1.0";
        String namespace = "Provider";

        when(capabilitySession.collectPetals(name, version, namespace)).thenThrow(new NoEntityFoundException());
        //when
        petalController.getPetalsForCapability(name, version, namespace);
        //Then
        verify(capabilitySession).collectPetals(name, version, namespace);
    }

    @Test
    public void testCollectPetalsByRequirements() throws NoEntityFoundException{
        String name = "JPA";

        Petal p1 = mock(Petal.class);
        Set<Petal> list = new HashSet<>();
        list.add(p1);
        when(requirementSession.collectPetals(name)).thenReturn(list);
        //when
        Collection<Petal> result = petalController.getPetalsForRequirement(name);
        //Then
        verify(requirementSession).collectPetals(name);
        Assert.assertEquals(list, result);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsByRequirementInexistent() throws NoEntityFoundException{
        String name = "JPA";

        when(requirementSession.collectPetals(name)).thenThrow(new NoEntityFoundException());
        //when
        petalController.getPetalsForRequirement(name);
        //Then
        verify(requirementSession).collectPetals(name);
    }
}