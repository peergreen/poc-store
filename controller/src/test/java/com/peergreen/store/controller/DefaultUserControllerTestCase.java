package com.peergreen.store.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.controller.impl.DefaultUserController;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultUserControllerTestCase {

    @Mock
    private ISessionUser userSession;
    @Mock
    private User oldUser;

    private DefaultUserController userController;
    private static final String PSEUDO = "toto";

    @BeforeMethod
    public void oneTimeSetUp() {
        MockitoAnnotations.initMocks(this);
        userController = new DefaultUserController();
        userController.bindUserSession(userSession);
    }

    @Test
    public void testAddUser() throws EntityAlreadyExistsException{
        String pseudo = "Toto";
        String password = "pwd";
        String email = "my@mail.com";

        userController.addUser(pseudo, password, email);

        verify(userSession).addUser(pseudo, password, email);
    }

    @Test(expectedExceptions = EntityAlreadyExistsException.class)
    public void testAddUserAlreadyExistent() throws EntityAlreadyExistsException{
        String password = "pwd";
        String email = "my@mail.com";

        when(userSession.addUser(PSEUDO, password, email)).thenThrow(new EntityAlreadyExistsException());
        userController.addUser(PSEUDO, password, email);

        verify(userSession).addUser(PSEUDO, password, email);
    }

    @Test
    public void testGetMetadata() throws NoEntityFoundException{

        User u = mock(User.class);
        when(userSession.findUserByPseudo(PSEUDO)).thenReturn(u);
        //when
        userController.getUserMetadata(PSEUDO);
        //Then
        verify(userSession).findUserByPseudo(PSEUDO);
        verify(u).getPassword();
        verify(u).getEmail();

    }

    @Test
    public void testGetMetadataForPetalInexistent() throws NoEntityFoundException{

        when(userSession.findUserByPseudo(PSEUDO)).thenReturn(null);
        //when
        Map<String, String> result = userController.getUserMetadata(PSEUDO);
        //Then
        verify(userSession).findUserByPseudo(PSEUDO);
        Assert.assertNull(result);

    }

    @Test
    public void getUser() {
        User u = new User();
        u.setPseudo(PSEUDO);
        u.setPassword("1234");
        u.setEmail("toto@turc.fr");
        doReturn(u).when(userSession).findUserByPseudo(PSEUDO);

        userController.getUser(PSEUDO);
        verify(userSession).findUserByPseudo(PSEUDO);
    }

    @Test
    public void getNotExistingUser() {
        doReturn(null).when(userSession).findUserByPseudo(PSEUDO);

        Assert.assertNull(userSession.findUserByPseudo(PSEUDO));
    }

    @Test
    public void removeUser() {
        userController.removeUser(PSEUDO);
        verify(userSession).removeUserbyPseudo(PSEUDO);
    }

    // @Test(expectedExceptions = IllegalArgumentException.class) 
    public void shouldThrowExceptionCauseUserNonExistent() {
        doReturn(null).when(userSession).findUserByPseudo("pseudo");
        userController.removeUser("pseudo");
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void shouldUpdateUser() throws NoEntityFoundException {
        String password = "pwd";
        String email = "my@mail.com";
        oldUser.setPseudo(PSEUDO);
        oldUser = userSession.findUserByPseudo(PSEUDO);

        userController.updateUser(PSEUDO, password, email);
        when(userSession.findUserByPseudo(PSEUDO)).thenReturn(oldUser);
        verify(userSession).updatePassword(oldUser, password);
        verify(userSession).updateMail(oldUser, email);
    } 

    @Test
    public void testCollectGroups() throws NoEntityFoundException{

        userController.collectGroups(PSEUDO);
        verify(userSession).collectGroups(PSEUDO);
    }

    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testCollectGroupsForUserInexistent() throws NoEntityFoundException{

        when(userSession.collectGroups(PSEUDO)).thenThrow(new NoEntityFoundException());
        userController.collectGroups(PSEUDO);
        verify(userSession).collectGroups(PSEUDO);
    }


    @Test
    public void testCollectPetals() throws NoEntityFoundException{

        userController.collectPetals(PSEUDO);
        verify(userSession).collectPetals(PSEUDO);
    }


    @Test(expectedExceptions = NoEntityFoundException.class)
    public void testCollectPetalsForUserInexistent() throws NoEntityFoundException{

        when(userSession.collectPetals(PSEUDO)).thenThrow(new NoEntityFoundException());
        userController.collectPetals(PSEUDO);
        verify(userSession).collectPetals(PSEUDO);
    }


    @Test
    public void testUpdateUser() throws NoEntityFoundException {
        User u = mock(User.class);

        String email = "mailupdated@pg.com";
        String password = "newpwd";
        when(userSession.findUserByPseudo(PSEUDO)).thenReturn(u);
        userController.updateUser(PSEUDO, password, email);

        verify(userSession).updateMail(u, email); 

    }










}