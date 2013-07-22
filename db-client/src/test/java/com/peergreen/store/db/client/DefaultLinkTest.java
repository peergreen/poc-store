package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.impl.DefaultLink;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;

public class DefaultLinkTest {

    private DefaultLink sessionLink;
    private String url;
    private String description;
    private String queryString;
    private String queryString2;
    private ArgumentCaptor<Link> linkArgument;
    private ArgumentCaptor<String> value;

    @Mock
    private EntityManager entityManager;
    @Mock
    private Link mocklink;
    @Mock
    private Query query;


    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        sessionLink = new DefaultLink();
        sessionLink.setEntityManager(entityManager);
        linkArgument = ArgumentCaptor.forClass(Link.class);
        value = ArgumentCaptor.forClass(String.class);
        url = "urlname";
        description = "link ";
        queryString="Link.findAll";
        queryString2 = "LinkByUrl";

    }

    @Test
    public void shouldAddLink() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionLink.findLink(anyString())).thenReturn(null);
        //when
        sessionLink.addLink(url, description);
        //Then
        verify(entityManager).persist(linkArgument.capture());
        Assert.assertEquals(url, linkArgument.getValue().getUrl());
        Assert.assertEquals(description, linkArgument.getValue().getDescription());

    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddLinkCauseAlreadyExist() throws EntityAlreadyExistsException {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionLink.findLink(anyString())).thenReturn(mocklink);

        //When
        sessionLink.addLink(url, description);

    }

    @Test
    public void shouldDeleteLink() {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(sessionLink.findLink(anyString())).thenReturn(mocklink);
        //when
        sessionLink.deleteLink(url);
        //Then
        verify(entityManager).remove(mocklink);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteCauseEntityNotExisting(){
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);
        //When
        sessionLink.deleteLink(url);

    }

    @Test
    public void shouldFindLink() {
        //Given
        when(entityManager.createNamedQuery(queryString2)).thenReturn(query);
        //when
        sessionLink.findLink(url);
        //Then
        verify(entityManager).createNamedQuery(value.capture());
        Assert.assertEquals(queryString2, value.getValue());
        verify(query).setParameter(anyString(), value.capture());
        Assert.assertEquals(url, value.getValue());
        verify(query).getSingleResult();
    }

    @Test
    public void shouldCollectLinks() {
        //Given
        when(entityManager.createNamedQuery(anyString())).thenReturn(query);
        //when
        sessionLink.collectLinks();
        //Then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();

    }

    @Test
    public void shouldModifyLinkDescription() {
        //Given
        String newDescription = "new link";
        //when
        sessionLink.updateDescription(mocklink, newDescription);
        //then
        verify(mocklink).setDescription(value.capture());
        Assert.assertEquals(newDescription, value.getValue());
        verify(entityManager).merge(mocklink);
    }
}
