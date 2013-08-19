package com.peergreen.store.db.client.ejb.session;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Link;
import com.peergreen.store.db.client.ejb.impl.DefaultSessionLink;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultSessionLinkTest {

    private DefaultSessionLink sessionLink;
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
        sessionLink = new DefaultSessionLink();
        sessionLink.setEntityManager(entityManager);

        linkArgument = ArgumentCaptor.forClass(Link.class);
        value = ArgumentCaptor.forClass(String.class);

        url = "urlname";
        description = "link ";
        queryString="Link.findAll";
        queryString2 = "LinkByUrl";

        when(entityManager.createNamedQuery(anyString())).thenReturn(query);

    }

    @Test
    public void shouldAddLink() throws EntityAlreadyExistsException {
        //Given
        when(query.getSingleResult()).thenReturn(null);
        //when
        sessionLink.addLink(url, description);
        //Then
        verify(entityManager).persist(linkArgument.capture());
        Assert.assertEquals(url, linkArgument.getValue().getUrl());
        Assert.assertEquals(description, linkArgument.getValue().getDescription());

    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddLinkCauseAlreadyExist() throws EntityAlreadyExistsException {
        //Given: The link already exist in the database
        when(query.getSingleResult()).thenReturn(mocklink);

        //When
        sessionLink.addLink(url, description);

        //Then throw a new EntityAlreadyExistsException
    }

    @Test
    public void shouldDeleteLink() {
        //Given
        when(query.getSingleResult()).thenReturn(mocklink);
        //when
        sessionLink.deleteLink(url);
        //Then
        verify(entityManager).remove(mocklink);
    }


    @Test
    public void shouldFindLink() {
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
    public void shouldFindLink2() {

        when(query.getSingleResult()).thenThrow(new NoResultException());

        //when
       Link result = sessionLink.findLink(url);

        //Then
        verify(entityManager).createNamedQuery(value.capture());
        Assert.assertEquals(queryString2, value.getValue());
        verify(query).setParameter(anyString(), value.capture());
        Assert.assertEquals(url, value.getValue());
        verify(query).getSingleResult();
        Assert.assertSame(null, result);

    }

    @Test
    public void shouldCollectLinks() {
        //when
        sessionLink.collectLinks();
        //Then
        verify(entityManager).createNamedQuery(queryString);
        verify(query).getResultList();

    }

    @Test
    public void shouldModifyLinkDescription() throws NoEntityFoundException {
        //Given
        String newDescription = "new link";
        when(query.getSingleResult()).thenReturn(mocklink);

        //when
        sessionLink.updateDescription(mocklink, newDescription);

        //then
        verify(mocklink).setDescription(value.capture());
        Assert.assertEquals(newDescription, value.getValue());
        verify(entityManager).merge(mocklink);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldThrowExceptionCauseUpdatingLinkInexistent() throws NoEntityFoundException {
        //Given: The link doesn't exist in the database
        when(query.getSingleResult()).thenReturn(null);
        String newDescription = "new link";
        //When
        sessionLink.updateDescription(mocklink, newDescription);

        //Then throw a new NoEntityFoundException
    }
}
