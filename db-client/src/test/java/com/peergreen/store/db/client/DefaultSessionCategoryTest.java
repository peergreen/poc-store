package com.peergreen.store.db.client;

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
import com.peergreen.store.db.client.ejb.impl.DefaultSessionCategory;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionCategoryTest {

    private DefaultSessionCategory sessionCategory;
    private String queryString;
    private String queryString2;

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

    ArgumentCaptor<Category> category;
    ArgumentCaptor<String> name;
    ArgumentCaptor<Petal> petalArgument;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionCategory = new DefaultSessionCategory();
        sessionCategory.setEntityManager(entityManager);       
        category = ArgumentCaptor.forClass(Category.class);
        name = ArgumentCaptor.forClass(String.class);
        petalArgument  = ArgumentCaptor.forClass(Petal.class);
        queryString = "Category.findAll";
        queryString2 = "CategoryByName";
    }

//    @Test
    public void shouldAddCategoryNonExistent() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionCategory.findCategory(anyString())).thenReturn(null);
        String categoryName = "totoService";
        //When
        sessionCategory.addCategory(categoryName);
        //Then
        verify(entityManager).persist(category.capture());
        Assert.assertEquals(categoryName, category.getValue().getCategoryName());
        Assert.assertTrue(category.getValue().getPetals().isEmpty());
    }
    
//    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddCategoryCauseAlreadyExist() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionCategory.findCategory(anyString())).thenReturn(mockcategory);
        String categoryName = "totoService";
        //When
        sessionCategory.addCategory(categoryName);
        sessionCategory.addCategory(categoryName);
    }

//    @Test
    public void shouldFindCategoryExisting() {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        String categoryName = "totoService";
        //When
        sessionCategory.findCategory(categoryName);
        //Then
        verify(entityManager).createNamedQuery(name.capture());
        Assert.assertEquals(queryString2, name.getValue());
        verify(query).setParameter(anyString(), name.capture());
        Assert.assertEquals(categoryName, name.getValue());
        verify(query).getSingleResult();
    }

//    @Test
    public void shouldDeleteCategory() {
        //Given
        String categoryName = "totoService";
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockcategory);
        //When
        sessionCategory.deleteCategory(categoryName);
        //Then
        verify(entityManager).remove(category.capture());
        Assert.assertSame(mockcategory,category.getValue() );
    }
    
//    @Test
    public void shouldAddPetal() {
        //Given
        when(mockcategory.getPetals()).thenReturn(petals);
        //When
        sessionCategory.addPetal(mockcategory, petal);
        //Then
        verify(mockcategory).getPetals();
        verify(petals).add(petalArgument.capture());
        Assert.assertSame(petal,petalArgument.getValue());
        verify(mockcategory).setPetals(petals);
        verify(entityManager).merge(mockcategory);
    }

//    @Test
    public void shouldCollectPetals() throws NoEntityFoundException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockcategory);
        //When
        sessionCategory.collectPetals("totoService");
        //Then
        verify(mockcategory).getPetals();
     }
    
//    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionWhenCollectPetalsCauseEntityNotExisting() throws NoEntityFoundException {
        //Given
        String categoryName = "totoService";
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionCategory.collectPetals(categoryName);
    }

//    @Test
    public void shouldRemovePetal() {
        //Given
        when(mockcategory.getPetals()).thenReturn(petals);
        //when
        sessionCategory.removePetal(mockcategory, petal);
        //then
        verify(mockcategory).getPetals();
        verify(petals).remove(petalArgument.capture());
        Assert.assertSame(petal, petalArgument.getValue());
        verify(entityManager).merge(mockcategory);
    }

//    @Test
    public void shouldCollectCategories() {
        //given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        //when
        sessionCategory.collectCategories();
        //then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();
    }
}