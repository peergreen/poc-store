package com.peergreen.store.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.controller.impl.DefaultUserController;
import com.peergreen.store.db.client.ejb.entity.User;
import com.peergreen.store.db.client.ejb.session.api.ISessionUser;

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
        doNothing().when(userSession).removeUserbyPseudo(PSEUDO);
        userController.removeUser(PSEUDO);
        verify(userSession).removeUserbyPseudo(PSEUDO);
    }

   // @Test(expectedExceptions = IllegalArgumentException.class) 
    public void shouldThrowExceptionCauseUserNonExistent() {
        doReturn(null).when(userSession).findUserByPseudo("pseudo");
        userController.removeUser("pseudo");
    }

    @Test
    public void shouldModifyUserPassword () {
        String password = "pwd";
        userController.modifyUserPassword(oldUser, password );
        verify(userSession).updatePassword(oldUser, password);
    }
    
    @Test
    public void shouldModifyUserMail () {
        String mail = "admin@pg.com";
        userController.modifyUserPassword(oldUser, mail );
        verify(userSession).updatePassword(oldUser, mail);
    }




}
