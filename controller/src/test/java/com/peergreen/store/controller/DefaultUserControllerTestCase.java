package com.peergreen.store.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.controller.impl.DefaultUserController;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;
import com.peergreen.store.db.client.exception.NoEntityFoundException;

public class DefaultUserControllerTestCase {

    @Mock
    private ISessionUser userSession;
    @Mock
    private User oldUser;

    private DefaultUserController userController;
    private static final String PSEUDO = "toto";

    @BeforeClass
    public void oneTimeSetUp() {
        MockitoAnnotations.initMocks(this);
        userController = new DefaultUserController(userSession);
    }

    @Test
    public void getUser() {
        User u = new User();
        u.setPseudo(PSEUDO);
        u.setPassword("1234");
        u.setEmail("toto@turc.fr");
        doReturn(u).when(userSession).findUserByPseudo(PSEUDO);

        User u2 = userSession.findUserByPseudo(PSEUDO);
        Assert.assertTrue(u2 != null && u2.getPseudo().equals(PSEUDO));
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

    @Test
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
}