package com.peergreen.store.db.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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

public class DefaultLinkTest {

    private DefaultLink sessionLink;
    private String url;
    private String description;
    private String queryString;
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
    }

    @Test
    public void shouldAddLink() {

        //when
        sessionLink.addLink(url, description);
        //Then
        verify(entityManager).persist(linkArgument.capture());
        Assert.assertEquals(url, linkArgument.getValue().getUrl());
        Assert.assertEquals(description, linkArgument.getValue().getDescription());

    }

    @Test
    public void shouldDeleteLink() {
        //Given
        when(entityManager.find(eq(Link.class), anyString())).thenReturn(mocklink);
        //when
        sessionLink.deleteLink(url);
        //Then
        verify(entityManager).find(eq(Link.class),value.capture());
        Assert.assertEquals(url, value.getValue());
        verify(entityManager).remove(mocklink);

    }

    @Test
    public void shouldFindLink() {
        //when
        sessionLink.findLink(url);
        //Then
        verify(entityManager).find(eq(Link.class),value.capture());
        Assert.assertEquals(url, value.getValue());
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
}
