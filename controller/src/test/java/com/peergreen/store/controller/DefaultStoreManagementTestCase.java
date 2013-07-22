package com.peergreen.store.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.impl.DefaultStoreManagement;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;
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


    @BeforeClass
    public void oneTimeSetUp() {
        storeManagement = new DefaultStoreManagement();
        MockitoAnnotations.initMocks(this);
        stringCaptor = ArgumentCaptor.forClass(String.class);
        storeManagement.bindPetalsPersistence(petalsPersistence);
        storeManagement.bindGroupSession(groupSession);
        storeManagement.bindLinkSession(linkSession);
        storeManagement.bindPetalSession(petalSession);
        storeManagement.bindUserSession(userSession);
    }

    @Test
    public void testAddLink() throws EntityAlreadyExistsException {
        String url = "https://store.peergreen.com";
        String description = "Peergreen central store";

        storeManagement.addLink(url, description);
        verify(linkSession).addLink(url, description);
    }

    //  @Test(expectedExceptions = EntityExistsException.class)
    public void shouldThrowEntityExistsException() {

        String url = "https://store.peergreen.com";
        String description = "Peergreen central store";

        storeManagement.addLink(url, description);
        storeManagement.addLink(url, description);
    }

    @Test
    void testRemoveLink() {
        String url = "https://store.peergreen.com";
        storeManagement.removeLink(url);

        verify(linkSession).deleteLink(url);
    }

    // @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {

        String url = "https://store.peergreen.com";
        String description = "Peergreen central store";

        storeManagement.addLink(url, description);
        storeManagement.addLink(url, description);
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

   // @Test
    // TODO handle exception
    public void testCollectPetalsForUser() throws NoEntityFoundException {
        User user = new User();
        user.setPseudo("Robert");
        Group group1 = new Group();
        group1.setGroupname("admin");
        Group group2 = new Group();
        group2.setGroupname("Web Devs");
        Set<Group> groups = new HashSet<>();
        groups.add(group1);
        groups.add(group2);
        user.setGroupSet(groups);

        Set<Petal> petals = new HashSet<>();
        Petal petal1 = new Petal();
        Petal petal2 = new Petal();
        petals.add(petal1);
        petals.add(petal2);

        group1.setPetals(petals);
        group2.setPetals(petals);

        // mock => always return user's groups
        when(userSession.collectGroups(user.getPseudo())).thenReturn(groups);


        storeManagement.collectPetalsForUser(user.getPseudo());
        verify(groupSession, times(2)).collectPetals(any(String.class));
        verify(userSession).findUserByPseudo("Robert");
    }

    // @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionCauseUserNonExistent() {

        when(userSession.findUserByPseudo("Robert")).thenReturn(null);

        storeManagement.collectPetalsForUser("Robert");

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

    @Test
    public void testSubmitPetal() throws NoEntityFoundException, EntityAlreadyExistsException {
        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        File binary = new File("/home/toto/petal.jar");
        Category category = new Category();
        HashSet<Requirement> requirements = new HashSet<>();
        HashSet<Capability> capabilities = new HashSet<>();

        storeManagement.submitPetal(vendor, artifactId, version, "", category, requirements, capabilities, binary);
        verify(petalSession).addPetal(vendor, artifactId, version, "", category, capabilities, requirements, Origin.STAGING);
        /* optional: can used collectPetals instead
        verify(groupSession).addGroup("admin");
         */
        verify(petalsPersistence).addToStaging(vendor, artifactId, version, binary);
    }

    // @Test(expectedExceptions = EntityExistsException.class)
    public void shouldThrowEntityExistsExceptionWhenSubmitPetal() {

        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        File binary = new File("/home/toto/petal.jar");
        Category category = new Category();
        HashSet<Requirement> requirements = new HashSet<>();
        HashSet<Capability> capabilities = new HashSet<>();

        storeManagement.submitPetal(vendor, artifactId, version, "", category, requirements, capabilities, binary);
        storeManagement.submitPetal(vendor, artifactId, version, "", category, requirements, capabilities, binary);

    }

    @Test
    public void testValidatePetal() {
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
        when(petalsPersistence.getPetalFromStaging(vendor, artifactId, version)).thenReturn(binary);
        when(petalSession.findPetal(vendor, artifactId, version)).thenReturn(petal);

        storeManagement.validatePetal(vendor, artifactId, version);
        verify(petalsPersistence).getPetalFromStaging(vendor, artifactId, version);
        verify(petalsPersistence).addToLocal(vendor, artifactId, version, binary);
        verify(petalSession).updateOrigin(petal, Origin.LOCAL);

    }

    //@Test(expectedExceptions = EntityExistsException.class)
    public void shouldThrowEntityExistsExceptionWhenValidatePetal() {

        String vendorName = "Peergreen";
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        String artifactId = "Tomcat HTTP service";
        String version = "7.0.39";
        File binary = new File("/home/toto/petal.jar");
        Category category = new Category();
        HashSet<Requirement> requirements = new HashSet<>();
        HashSet<Capability> capabilities = new HashSet<>();

        storeManagement.submitPetal(vendor, artifactId, version, "", category, requirements, capabilities, binary);
        storeManagement.submitPetal(vendor, artifactId, version, "", category, requirements, capabilities, binary);

    }

    // @Test
    public void shouldAddcategory() throws EntityAlreadyExistsException {
        String name = "Persistence";

        storeManagement.addCategory(name);

        verify(categorySession).addCategory(stringCaptor.capture());
        Assert.assertEquals(name, stringCaptor.getValue());
    }

//     @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowEntityExistExceptionForCategory() throws EntityAlreadyExistsException {
        String name = "Persistence";

        storeManagement.addCategory(name);
        storeManagement.addCategory(name);

        verify(categorySession).addCategory(stringCaptor.capture());
    }

    // @Test
    public void shouldRemovecategory() throws EntityAlreadyExistsException {

        String name = "Persistence";

        storeManagement.addCategory(name);

        verify(categorySession).deleteCategory(stringCaptor.capture());
        Assert.assertEquals(name, stringCaptor.getValue());
    }

    //  @Test
    public void shouldCollectCategories() {
        storeManagement.collectCategories();
        verify(categorySession).collectCategories();
    }
}