package com.peergreen.store.aether.client;

import static org.mockito.Mockito.doReturn;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.peergreen.store.controller.IUserController;
import com.peergreen.store.db.client.ejb.entity.User;

public class UserControllerTestCase {

    @Mock
    private IUserController userController;
    
    private static final String PSEUDO = "toto";
    
    @BeforeClass
    public void oneTimeSetUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getUser() {
        User u = new User();
        u.setPseudo("toto");
        u.setPassword("1234");
        u.setEmail("toto@turc.fr");
        doReturn(u).when(userController).getUser(PSEUDO);
        
        Assert.assertNotNull(userController.getUser(PSEUDO));
    }

}
