package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.persistence.EntityManager;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.impl.DefaultCategory;

public class DefaultCategoryTest {

    private DefaultCategory sessionCategory;

    @Mock
    private EntityManager entityManager;
    @Mock 
    private Category mockcategory;
    @Mock
    private Petal petal ;
    @Mock
    private Set<Petal> petals;

    ArgumentCaptor<Category> category;
    ArgumentCaptor<String> name;
    ArgumentCaptor<Petal> petalArgument;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionCategory = new DefaultCategory();
        sessionCategory.setEntityManager(entityManager);       
        category = ArgumentCaptor.forClass(Category.class);
        name = ArgumentCaptor.forClass(String.class);
        petalArgument  = ArgumentCaptor.forClass(Petal.class);
    }


    @Test
    public void shouldAddCategory() {
        //Given
        String categoryName = "totoService";
        //When
        sessionCategory.addCategory(categoryName);
        //Then
        verify(entityManager).persist(category.capture());
        Assert.assertEquals(categoryName, category.getValue().getCategoryName());

    }

    @Test
    public void shouldFindCategory(){
        //Given
        String categoryName = "totoService";
        //When
        sessionCategory.findCategory("totoService");
        //Then
        verify(entityManager).find(eq(Category.class),name.capture());
        Assert.assertEquals(categoryName, name.getValue());
    }

    @Test
    public void shouldDeleteCategory(){
        //Given
        String categoryName = "totoService";
        when(entityManager.find(eq(Category.class), anyString())).thenReturn(mockcategory);
        //When
        sessionCategory.deleteCategory(categoryName);
        //Then
        verify(entityManager).find(eq(Category.class),name.capture());
        Assert.assertEquals(categoryName, name.getValue());
        verify(entityManager).remove(category.capture());
        Assert.assertSame(mockcategory,category.getValue() );
       
    }
    @Test
    public void shouldAddPetal(){
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

    @Test
    public void shouldCollectPetals(){
        //Given
        when(entityManager.find(eq(Category.class), anyString())).thenReturn(mockcategory);
      //When
        sessionCategory.collectPetals("totoService");
        //Then
        verify(entityManager).find(eq(Category.class), name.capture());
        Assert.assertEquals("totoService", name.getValue());
        verify(mockcategory).getPetals();

    }

    @Test
    public void shouldRemovePetal(){
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

}
