package com.peergreen.store.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashSet;

import junit.framework.Assert;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.impl.DefaultStoreManagement;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionLink;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultStoreManagementTestCase {

    private DefaultStoreManagement storeManagement;
    private ArgumentCaptor<String> stringCaptor;
    @Mock
    private IPetalsPersistence petalsPersistence;
    @Mock
    private ISessionGroup groupSession;
    @Mock
    private ISessionLink linkSession;
    @Mock
    private ISessionPetal petalSession;
    @Mock
    private ISessionUser userSession;
    @Mock
    private ISessionVendor vendorSession;
    @Mock
    private ISessionCategory categorySession;


    @BeforeMethod
    public void oneTimeSetUp() {
        storeManagement = new DefaultStoreManagement();
        MockitoAnnotations.initMocks(this);
        stringCaptor = ArgumentCaptor.forClass(String.class);
        storeManagement.bindCategorySession(categorySession);
        storeManagement.bindPetalsPersistence(petalsPersistence);
        storeManagement.bindGroupSession(groupSession);
        storeManagement.bindLinkSession(linkSession);
        storeManagement.bindPetalSession(petalSession);
        storeManagement.bindUserSession(userSession);
        storeManagement.bindVendorSession(vendorSession);
    }

    @Test
    public void testAddLink() throws EntityAlreadyExistsException {
        String url = "https://store.peergreen.com";
        String description = "Peergreen central store";

        storeManagement.addLink(url, description);
        verify(linkSession).addLink(url, description);
    }
    
    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void testAddLinkAlreadyExistent() throws EntityAlreadyExistsException {
        String url = "https://store.peergreen.com";
        String description = "Peergreen central store";

        when(linkSession.addLink(url, description)).thenThrow(new EntityAlreadyExistsException());
        storeManagement.addLink(url, description);
        verify(linkSession).addLink(url, description);
    }

    @Test
    void testRemoveLink() {
        String url = "https://store.peergreen.com";
        storeManagement.removeLink(url);

        verify(linkSession).deleteLink(url);
    }

    @Test
    public void testCollectLinks() {
        storeManagement.collectLinks();
        verify(linkSession).collectLinks();
    }

    @Test
    public void testCollectPetals() {
        storeManagement.collectPetals();
        verify(petalSession).collectPetals();
    }

    @Test
    public void testCollectPetalsFromLocal() {
        storeManagement.collectPetalsFromLocal();
        verify(petalSession).collectPetalsFromLocal();
    }

    @Test
    public void testCollectPetalsFromStaging() {
        storeManagement.collectPetalsFromStaging();
        verify(petalSession).collectPetalsFromStaging();
    }

    @Test
    public void testCollectPetalsFromRemote() {
        storeManagement.collectPetalsFromRemote();
        verify(petalSession).collectPetalsFromRemote();
    }

    @Test
    public void testCollectUsers() {
        storeManagement.collectUsers();
        verify(userSession).collectUsers();
    }

    @Test
    public void testCollectGroups() {
        storeManagement.collectGroups();
        verify(groupSession).collectGroups();
    }

    public void testSubmitPetal() throws NoEntityFoundException, EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        File binary = new File("/home/toto/petal.jar");
        Category category = new Category();
        category.setCategoryName("Bundle");
        HashSet<Requirement> requirements = new HashSet<>();
        HashSet<Capability> capabilities = new HashSet<>();
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);

        storeManagement.submitPetal(vendorName, artifactId, version, "", "Bundle", requirements, capabilities, binary);
        verify(petalsPersistence).addToStaging(vendorName, artifactId, version, binary);
        verify(petalSession).addPetal(vendor, artifactId, version, "", category, capabilities, requirements, Origin.STAGING);
    }
    
    
    public void testSubmitPetalAlreadyExistent() throws EntityAlreadyExistsException, NoEntityFoundException {
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        File binary = new File("/home/toto/petal.jar");
        Category category = new Category();
        category.setCategoryName("Bundle");
        HashSet<Requirement> requirements = new HashSet<>();
        HashSet<Capability> capabilities = new HashSet<>();
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.addPetal(vendor, artifactId, version, "",
                category, capabilities, requirements, Origin.STAGING))
                .thenThrow(new EntityAlreadyExistsException());

        storeManagement.submitPetal(vendorName, artifactId, version, "", "Bundle" , requirements, capabilities, binary);
        verify(petalsPersistence).addToStaging(vendorName, artifactId, version, binary);
        verify(petalSession).addPetal(vendor, artifactId, version, "", category, capabilities, requirements, Origin.STAGING);
    }
    
    public void testSubmitPetalWithAttributeInexistent() throws EntityAlreadyExistsException, NoEntityFoundException {
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        File binary = new File("/home/toto/petal.jar");
        Category category = new Category();
        category.setCategoryName("Bundle");
        HashSet<Requirement> requirements = new HashSet<>();
        HashSet<Capability> capabilities = new HashSet<>();
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalSession.addPetal(vendor, artifactId, version, "",
                category, capabilities, requirements, Origin.STAGING))
                .thenThrow(new NoEntityFoundException());

        storeManagement.submitPetal(vendorName, artifactId, version, "", "Bundle", requirements, capabilities, binary);
        verify(petalsPersistence).addToStaging(vendorName, artifactId, version, binary);
        verify(petalSession).addPetal(vendor, artifactId, version, "", category, capabilities, requirements, Origin.STAGING);
    }

    @Test
    public void testValidatePetal() throws NoEntityFoundException {
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        File binary = new File("/home/toto/petal.jar");

        // mock => always return right vendor
        //      => always return the same binary
        //      => always return the same petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalsPersistence.getPetalFromStaging(vendorName, artifactId, version)).thenReturn(binary);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        storeManagement.validatePetal(vendorName, artifactId, version);
        verify(petalsPersistence).getPetalFromStaging(vendorName, artifactId, version);
        verify(petalsPersistence).addToLocal(vendorName, artifactId, version, binary);
        verify(petalSession).updateOrigin(petal, Origin.LOCAL);
    }
    
    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testValidatePetalInexistent() throws NoEntityFoundException {
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        Petal petal = new Petal();
        File binary = new File("/home/toto/petal.jar");

        // mock => always return right vendor
        //      => always return the same binary
        //      => always return the same petal
        when(vendorSession.findVendor(vendorName)).thenReturn(vendor);
        when(petalsPersistence.getPetalFromStaging(vendorName, artifactId, version)).thenReturn(binary);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);
        when(petalSession.updateOrigin(petal, Origin.LOCAL)).thenThrow(new NoEntityFoundException());

        storeManagement.validatePetal(vendorName, artifactId, version);
        verify(petalsPersistence).getPetalFromStaging(vendorName, artifactId, version);
        verify(petalsPersistence).addToLocal(vendorName, artifactId, version, binary);
        verify(petalSession).updateOrigin(petal, Origin.LOCAL);
    }

    @Test
    public void shouldAddcategory() throws EntityAlreadyExistsException {
        String name = "Persistence";

        storeManagement.createCategory(name);

        verify(categorySession).addCategory(stringCaptor.capture());
        Assert.assertEquals(name, stringCaptor.getValue());
    }
    
    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void testAddCategoryAlreadyExistent() throws EntityAlreadyExistsException {
        String name = "Persistence";
        when(categorySession.addCategory(name)).thenThrow(new EntityAlreadyExistsException());

        storeManagement.createCategory(name);

        
        verify(categorySession).addCategory(stringCaptor.capture());
        Assert.assertEquals(name, stringCaptor.getValue());
    }

    @Test
    public void shouldRemoveCategory() throws EntityAlreadyExistsException {
        String name = "Persistence";

        storeManagement.removeCategory(name);
        verify(categorySession).deleteCategory(name);
        
//        verify(categorySession).deleteCategory(stringCaptor.capture());
//        Assert.assertEquals(name, stringCaptor.getValue());
    }

    @Test
    public void shouldCollectCategories() {
        storeManagement.collectCategories();
        verify(categorySession).collectCategories();
    }
}