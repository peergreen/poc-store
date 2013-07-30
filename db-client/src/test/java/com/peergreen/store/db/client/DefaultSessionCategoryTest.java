package com.peergreen.store.db.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionCategoryTest {

    private DefaultSessionCategory sessionCategory;
    private String queryString;
    private String queryString2;
    private String categoryName; 

    @Mock
    private EntityManager entityManager;
    @Mock 
    private Category mockcategory;
    @Mock
    private Petal petal ;
    @Mock
    private Set<Petal> petals;
    @Mock 
    private Query query;
    @Mock
    private ISessionPetal sessionPetal;

    ArgumentCaptor<Category> category;
    ArgumentCaptor<String> name;
    ArgumentCaptor<Petal> petalArgument;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionCategory = new DefaultSessionCategory();
        sessionCategory.setEntityManager(entityManager);   
        sessionCategory.setSessionPetal(sessionPetal);

        category = ArgumentCaptor.forClass(Category.class);
        name = ArgumentCaptor.forClass(String.class);
        petalArgument  = ArgumentCaptor.forClass(Petal.class);

        queryString = "Category.findAll";
        queryString2 = "CategoryByName";
        categoryName = "Bundle";

        when(sessionPetal.findPetal(any(Vendor.class), anyString(), anyString())).thenReturn(petal);
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        when(mockcategory.getPetals()).thenReturn(petals);


    }

    @Test
    public void shouldAddCategoryNonExistent() throws EntityAlreadyExistsException {
        //Given : The category doesn't exist in the database 
        when(sessionCategory.findCategory(anyString())).thenReturn(null);
        //When
        sessionCategory.addCategory(categoryName);
        //Then
        verify(entityManager).persist(category.capture());
        Assert.assertEquals(categoryName, category.getValue().getCategoryName());
        Assert.assertTrue(category.getValue().getPetals().isEmpty());
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddCategoryCauseAlreadyExist() throws EntityAlreadyExistsException {
        //Given : The category already exists in the database. 
        when(sessionCategory.findCategory(anyString())).thenReturn(mockcategory);
        //When
        sessionCategory.addCategory(categoryName);
        sessionCategory.addCategory(categoryName);
        //Then throw a new EntityAlreadyExistsException
    }

    @Test
    public void shouldFindCategory() {

        //When
        sessionCategory.findCategory(categoryName);
        //Then
        verify(entityManager).createNamedQuery(name.capture());
        Assert.assertEquals(queryString2, name.getValue());
        verify(query).setParameter(anyString(), name.capture());
        Assert.assertEquals(categoryName, name.getValue());
        verify(query).getSingleResult();
    }

    @Test
    public void shouldDeleteCategory() {
        //Given : The category exists in the database 
        when(query.getSingleResult()).thenReturn(mockcategory);
        //When
        sessionCategory.deleteCategory(categoryName);
        //Then
        verify(entityManager).remove(category.capture());
        Assert.assertSame(mockcategory,category.getValue() );
    }

    @Test
    public void shouldAddPetal() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(mockcategory);

        //When
        sessionCategory.addPetal(mockcategory, petal);

        //Then
        verify(mockcategory).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertSame(petal,petalArgument.getValue());
        verify(entityManager).merge(mockcategory);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseAddingPetalToCategoryInexistent() throws NoEntityFoundException{
        //Given : The category doesn't exist in the database
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCategory.addPetal(mockcategory, petal);
        //Then throw a new NoEntityFoundException

    }
    @Test
    public void shouldCollectPetals() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(mockcategory);
        //When
        sessionCategory.collectPetals(categoryName);
        //Then
        verify(mockcategory).getPetals();
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseCollectPetalsFromCategoryInexistent() throws NoEntityFoundException{
        //Given : The category doesn't exist in the database
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCategory.collectPetals(categoryName);
        //Then throw a new NoEntityFoundException

    }

    @Test
    public void shouldRemovePetal() throws NoEntityFoundException {
        //Given
        when(query.getSingleResult()).thenReturn(mockcategory);
        //when
        sessionCategory.removePetal(mockcategory, petal);

        //then
        verify(mockcategory).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertSame(petal, petalArgument.getValue());
        verify(entityManager).merge(mockcategory);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseRemovePetalFromCategoryInexistent() throws NoEntityFoundException{
        //Given : The category doesn't exist in the database
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCategory.removePetal(mockcategory, petal);
        //Then throw a new NoEntityFoundException

    }

    @Test
    public void shouldCollectCategories() {
                //when
        sessionCategory.collectCategories();
        //then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }
}