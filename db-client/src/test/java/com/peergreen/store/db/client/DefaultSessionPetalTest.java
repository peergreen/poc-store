package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import junit.framework.Assert;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionPetal;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;
import com.peergreen.store.db.client.ejb.session.api.ISessionCapability;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionGroup;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
import com.peergreen.store.db.client.enumeration.Origin;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionPetalTest {

    private DefaultSessionPetal sessionPetal;

    private ArgumentCaptor<String> stringArgumentCaptor;
    private ArgumentCaptor<Petal> petalArgumentCaptor;
    private ArgumentCaptor<PetalId> idArgumentCaptor;
    private ArgumentCaptor<Origin> originArgumentCaptor;
    private ArgumentCaptor<Group> groupArgumentCaptor;
    private ArgumentCaptor<Category> catArgumentCaptor;
    private ArgumentCaptor<Capability> capArgumentCaptor;
    private ArgumentCaptor<Requirement> reqArgumentCaptor;

    private String artifactId;
    private String version;
    private String description;
    private Origin origin;
    private String queryString;

    @Mock
    private EntityManager entityManager;
    @Mock
    private Vendor vendor;
    @Mock
    private Category category;
    @Mock
    private Group mockgroup;
    @Mock
    private Petal mockpetal;
    @Mock
    private User mockuser;
    @Mock
    private Set<Group> groups;
    @Mock
    private Set<User> users;
    @Mock
    private Set<Capability> capabilities;
    @Mock
    private Set<Requirement> requirements;
    @Mock
    private Query query;
    @Mock
    private Capability mockcapability;
    @Mock
    private Requirement mockrequirement;
    @Mock
    private Iterator<Capability> itC;
    @Mock
    private Iterator<Requirement> itR;
    @Mock
    private ISessionVendor sessionVendor;
    @Mock
    private ISessionCategory sessionCategory;
    @Mock
    private ISessionCapability sessionCapability;
    @Mock
    private ISessionRequirement sessionRequirement;
    @Mock
    private ISessionGroup sessionGroup;


    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);

        sessionPetal = new DefaultSessionPetal();
        sessionPetal.setEntityManager(entityManager);
        sessionPetal.setSessionCapability(sessionCapability);
        sessionPetal.setSessionCategory(sessionCategory);
        sessionPetal.setSessionGroup(sessionGroup);;
        sessionPetal.setSessionRequirement(sessionRequirement);
        sessionPetal.setSessionVendor(sessionVendor);

        petalArgumentCaptor  = ArgumentCaptor.forClass(Petal.class);
        stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        idArgumentCaptor = ArgumentCaptor.forClass(PetalId.class);
        originArgumentCaptor = ArgumentCaptor.forClass(Origin.class);
        groupArgumentCaptor = ArgumentCaptor.forClass(Group.class);
        catArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        capArgumentCaptor = ArgumentCaptor.forClass(Capability.class);
        reqArgumentCaptor = ArgumentCaptor.forClass(Requirement.class);


        artifactId = "pg";
        version = "1.0.3";
        description = " new petal ";
        origin = Origin.REMOTE;
        
        when(mockpetal.getArtifactId()).thenReturn(artifactId);
        when(mockpetal.getVendor()).thenReturn(vendor);
        when(mockpetal.getVersion()).thenReturn(version);
        when(mockpetal.getCapabilities()).thenReturn(capabilities);
        when(mockpetal.getRequirements()).thenReturn(requirements);
        when(mockpetal.getCategory()).thenReturn(category);
        when(category.getCategoryName()).thenReturn("Bundle");
        
        when(sessionCapability.findCapability(anyString(), anyString())).thenReturn(mockcapability);
        when(capabilities.iterator()).thenReturn(itC);
        
        when(sessionRequirement.findRequirement(anyString())).thenReturn(mockrequirement);
        when(requirements.iterator()).thenReturn(itR);
        
        when(sessionCategory.findCategory(anyString())).thenReturn(category);
        when(sessionGroup.findGroup(anyString())).thenReturn(mockgroup);
        when(sessionVendor.findVendor(anyString())).thenReturn(vendor);
                
    }

    @Test
    public void shouldAddPetal() throws NoEntityFoundException, EntityAlreadyExistsException, InstantiationException, IllegalAccessException {
        //Given : A petal with 1 requirement and 1 capability 
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(null);
        when(sessionGroup.findGroup("Administrator")).thenReturn(mockgroup);

        
//        when(itC.hasNext()).thenReturn(true,false);
//        when(itR.hasNext()).thenReturn(true,false);
        
        Set<Capability> capabilities = new HashSet<Capability>();
        capabilities.add(mockcapability);

        Set<Requirement> requirements = new HashSet<Requirement>();
        requirements.add(mockrequirement);

        //when
        sessionPetal.addPetal(vendor, artifactId, version, description, category, capabilities, requirements, origin);
        //then
        verify(entityManager).persist(petalArgumentCaptor.capture());     
       
        Assert.assertEquals(vendor, petalArgumentCaptor.getValue().getVendor());
        Assert.assertEquals(artifactId, petalArgumentCaptor.getValue().getArtifactId());
        Assert.assertEquals(version, petalArgumentCaptor.getValue().getVersion());
        Assert.assertEquals(description, petalArgumentCaptor.getValue().getDescription());
        Assert.assertEquals(category, petalArgumentCaptor.getValue().getCategory());
        Assert.assertEquals(capabilities, petalArgumentCaptor.getValue().getCapabilities());
        Assert.assertEquals(requirements, petalArgumentCaptor.getValue().getRequirements());
        Assert.assertEquals(origin, petalArgumentCaptor.getValue().getOrigin());
        Assert.assertTrue(petalArgumentCaptor.getValue().getGroups().contains(mockgroup));
        
       // verify(sessionGroup).addPetal(mockgroup,petalArgumentCaptor.capture());


    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenAddCauseGroupAdministratorDoesNotExist() throws NoEntityFoundException, EntityAlreadyExistsException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(null);
        when(sessionGroup.findGroup("Administrator")).thenReturn(null);

        //when
        sessionPetal.addPetal(vendor, artifactId, version, description, category, capabilities, requirements, origin);

    }

    @Test
    public void shouldFindPetal() {
        //Given
        when(vendor.getVendorName()).thenReturn("PG");
        //when
        sessionPetal.findPetal(vendor, artifactId, version);
        //then
        verify(entityManager).find(eq(Petal.class), idArgumentCaptor.capture());
        Assert.assertEquals(vendor, idArgumentCaptor.getValue().getVendor());
        Assert.assertEquals(artifactId, idArgumentCaptor.getValue().getArtifactId());
        Assert.assertEquals(version, idArgumentCaptor.getValue().getVersion());
    }

    @Test
    public void shouldCollectGroupWhichCanAccessToIt() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        //when
        sessionPetal.collectGroups(mockpetal);
        //then
        verify(mockpetal).getGroups();
    }

    @Test
    public void shouldCollectCapabilitiesProvided() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        //when
        sessionPetal.collectCapabilities(mockpetal);
        //then
        verify(mockpetal).getCapabilities();
    }

    @Test
    public void shouldCollectRequirementsNeeded() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        //when
        sessionPetal.collectRequirements(mockpetal);

        //then
        verify(mockpetal).getRequirements();
    }

    @Test
    public void shouldModifyDescription() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        //Given
        String newDescription = " A new description"; 
        //when
        sessionPetal.updateDescription(mockpetal, newDescription);
        
        //then
        verify(mockpetal).setDescription(stringArgumentCaptor.capture());
        Assert.assertEquals(newDescription, stringArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }

    @Test
    public void shouldModifyOrigin() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        Origin newOrigin = Origin.LOCAL; 
        //when
            sessionPetal.updateOrigin(mockpetal, newOrigin);
        
        //then
        verify(mockpetal).setOrigin(originArgumentCaptor.capture());
        Assert.assertEquals(newOrigin, originArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }

    @Test
    public void shouldDeletePetal() {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);
        //when
        sessionPetal.deletePetal(mockpetal);
        //then
        verify(entityManager).remove(petalArgumentCaptor.capture());
        Assert.assertSame(mockpetal,petalArgumentCaptor.getValue());

    }


    @Test
    public void shouldGiveAccessToPetalForGroup() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        when(mockpetal.getGroups()).thenReturn(groups);
        //when
        sessionPetal.giveAccesToGroup(mockpetal, mockgroup);

        //then
        verify(mockpetal).getGroups();
        verify(groups).add(groupArgumentCaptor.capture());
        Assert.assertSame(mockgroup, groupArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }

    @Test
    public void shouldRemoveAccessToPetalForGroup() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        when(mockpetal.getGroups()).thenReturn(groups);
        //when
        sessionPetal.removeAccesToGroup(mockpetal, mockgroup);

        //then
        verify(mockpetal).getGroups();
        verify(groups).remove(groupArgumentCaptor.capture());
        Assert.assertSame(mockgroup, groupArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }

    @Test
    public void shouldAddCategory() throws NoEntityFoundException {
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);
        //when
        sessionPetal.addCategory(mockpetal, category);
        //then
        verify(mockpetal).setCategory(catArgumentCaptor.capture());
        Assert.assertSame(category, catArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }

    @Test
    public void shouldGetCategory() throws NoEntityFoundException {

        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        //when
        sessionPetal.getCategory(mockpetal);

        //then
        verify(mockpetal).getCategory();
    }

    @Test
    public void shouldAddCapability() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);
        //when
        sessionPetal.addCapability(mockpetal, mockcapability);

        //then 
        verify(mockpetal).getCapabilities();
        verify(capabilities).add(capArgumentCaptor.capture());
        Assert.assertSame(mockcapability, capArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }


    @Test
    public void shouldRemoveCapability() throws NoEntityFoundException {
        //Given
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);
        when(mockpetal.getCapabilities()).thenReturn(capabilities);
        //when
        sessionPetal.removeCapability(mockpetal, mockcapability);

        //then 
        verify(mockpetal).getCapabilities();
        verify(capabilities).remove(capArgumentCaptor.capture());
        Assert.assertSame(mockcapability, capArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }


    @Test
    public void shouldAddRequirement() throws NoEntityFoundException {

        //Given
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);
        when(mockpetal.getRequirements()).thenReturn(requirements);
        //when
        sessionPetal.addRequirement(mockpetal, mockrequirement);

        //then 
        verify(mockpetal).getRequirements();
        verify(requirements).add(reqArgumentCaptor.capture());
        Assert.assertSame(mockrequirement, reqArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }

    @Test
    public void shouldRemoveRequirement() throws NoEntityFoundException {

        //Given
        when(entityManager.find(eq(Petal.class), anyObject())).thenReturn(mockpetal);

        when(mockpetal.getRequirements()).thenReturn(requirements);
        //when
        sessionPetal.removeRequirement(mockpetal, mockrequirement);

        //then 
        verify(mockpetal).getRequirements();
        verify(requirements).remove(reqArgumentCaptor.capture());
        Assert.assertSame(mockrequirement, reqArgumentCaptor.getValue());
        verify(entityManager).merge(mockpetal);
    }


    @Test
    public void shouldCollectPetals() {
        queryString = "Petal.findAll";
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        //when
        sessionPetal.collectPetals();
        //then
        verify(entityManager).createNamedQuery(stringArgumentCaptor.capture());
        Assert.assertEquals(queryString, stringArgumentCaptor.getValue());
        verify(query).getResultList();
    }

    @Test
    public void shouldcollectPetalsFromLocal() {
        queryString = "select p from Petal p where p.origin = :origin";
        when(entityManager.createQuery(anyString())).thenReturn(query);
        //when
        sessionPetal.collectPetalsFromLocal();
        //then
        verify(entityManager).createQuery(stringArgumentCaptor.capture());
        Assert.assertEquals(queryString, stringArgumentCaptor.getValue());
        verify(query).setParameter(anyString(), originArgumentCaptor.capture());
        Assert.assertEquals(Origin.LOCAL, originArgumentCaptor.getValue());
        verify(query).getResultList();
    }

    @Test
    public void shouldcollectPetalsFromRemote() {
        queryString = "select p from Petal p where p.origin = :origin";
        when(entityManager.createQuery(anyString())).thenReturn(query);
        //when
        sessionPetal.collectPetalsFromRemote();
        //then
        verify(entityManager).createQuery(stringArgumentCaptor.capture());
        Assert.assertEquals(queryString, stringArgumentCaptor.getValue());
        verify(query).setParameter(anyString(), originArgumentCaptor.capture());
        Assert.assertEquals(Origin.REMOTE, originArgumentCaptor.getValue());
        verify(query).getResultList();
    }

    @Test
    public void shouldcollectPetalsFromStagging() {
        queryString = "select p from Petal p where p.origin = :origin";
        when(entityManager.createQuery(anyString())).thenReturn(query);
        //when
        sessionPetal.collectPetalsFromStaging();
        //then
        verify(entityManager).createQuery(stringArgumentCaptor.capture());
        Assert.assertEquals(queryString, stringArgumentCaptor.getValue());
        verify(query).setParameter(anyString(), originArgumentCaptor.capture());
        Assert.assertEquals(Origin.STAGING, originArgumentCaptor.getValue());
        verify(query).getResultList();
    }

}
