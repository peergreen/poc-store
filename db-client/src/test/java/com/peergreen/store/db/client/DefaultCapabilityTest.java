package com.peergreen.store.db.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.impl.DefaultCapability;


public class DefaultCapabilityTest {

    private Capability capability;
    private Map<String, Capability> capabilities;
    private DefaultCapability sessionCapability;
    Properties properties;

    @Mock
    private EntityManager entityManager;  
    @Mock 
    private Capability mockcapability;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionCapability = new DefaultCapability();
        sessionCapability.setEntityManager(entityManager);
        properties = new Properties();

        this.capabilities = new HashMap<String, Capability>();

        doAnswer(new Answer<Capability>() {
            @Override
            public Capability answer(InvocationOnMock invocation) throws Throwable {
                // TODO Auto-generated method stub
                Object[] args = invocation.getArguments();
                capability = (Capability) args[0];
                String key = capability.getcapabilityName();
                capabilities.put(key, capability);
                return capability; 
            }
        }).when(entityManager).persist(any(Capability.class));

        doAnswer(new Answer<Capability>() {
            @Override
            public Capability answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String key = (String) args[1];
                if(capabilities.containsKey(key) == true){
                    return capabilities.get(key);}
                else
                {
                    return null;
                }
            }
        }).when(entityManager).find(eq(Capability.class) ,anyString());

        doAnswer(new Answer<Capability>() {
            @Override
            public Capability answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                capability = (Capability) args[0];
                String key = capability.getcapabilityName();
                capabilities.put(key, capability);
                return capabilities.get(key);
            }
        }).when(entityManager).merge(any(Capability.class));
        
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                capability = (Capability) args[0];
                String key = capability.getcapabilityName();
                capabilities.remove(key);
                return null;
            }
        }).when(entityManager).remove(any(Capability.class));
    }

    @AfterMethod
    public void tearDown()
    {

    }

    @Test
    /**
     * Test to check that adding the same capability in database causes updating the list
     *  of petals in the first instance of capability and not the creation of a second instance
     */
    public void shouldAddCapability() {
        // Given two petals which give the same capability
        Petal petal1 = new Petal();
        Petal petal2 = new Petal();
        Set<Petal> petals=new HashSet<Petal>();
        petals.add(petal1);
        petals.add(petal2);


        //When we add it 

       Capability capability1= sessionCapability.addCapability("capabilityName", "namespace", properties, petal1);
        Capability capability2 = sessionCapability.addCapability("capabilityName", "namespace", properties, petal2);

        //Then the two instance of the capabilities are the same and have a collection of two petals in attribute  
        Assert.assertSame(capability1, capability2);
        Assert.assertEquals(sessionCapability.collectPetals("capabilityName"), petals);

    }

    @Test
    /**
     * Test to check if the search of a capability non existent returns null 
     */
    public void shouldFindCapability(){
        //Given a capability
        Capability capability;
        // When trying to find a capability non existent
        capability = sessionCapability.findCapability("capabilityName");
        //Then 
        Assert.assertEquals(capability,null);

    }

    @Test
    /**
     *Test to check if the delete feature works well  
     */
    public void shouldRemoveCapability(){
        //Given a petal that provided a capability
        Petal petal1 = new Petal();

        //When adding the capability, and remove it after 
        sessionCapability.addCapability("capabilityName", "namespace", properties, petal1);
        sessionCapability.deleteCapability("capabilityName");

        //Then it's not found in the database
        Assert.assertEquals(sessionCapability.findCapability("capabilityName"),null);

    }

    @Test
    /**
     *Test to check if the feature to add petal to a capability works well  
     */
    public void shouldAddPetalToCapability()
    {
        //Given petals that provided a capability existent in the database
        Petal petal1 = new Petal();
        Petal petal2 = new Petal();
        Petal petal3 = new Petal();
        Set<Petal> petals=new HashSet<Petal>();
        petals.add(petal1);
        petals.add(petal2);
        when(mockcapability.getPetals()).thenReturn(petals);
        when(mockcapability.getcapabilityName()).thenReturn("Mock");

        //When adding a new petal 
        mockcapability= sessionCapability.addPetal(mockcapability, petal3);
       petals.add(petal3);

        //Then  
        Assert.assertEquals(sessionCapability.collectPetals(mockcapability.getcapabilityName()),petals);
    }

    @Test
    /**
     *Test to check if the feature to remove petal from a capability works well  
     */
    public void shouldRemovePetalFromCapability()
    {
        //Given 
        Petal petal1 = new Petal();
        Petal petal2 = new Petal();
        Petal petal3 = new Petal();

        Set<Petal> petals=new HashSet<Petal>();
        petals.add(petal1);
        petals.add(petal2);

        Set<Petal> petals2=new HashSet<Petal>();
        petals2.add(petal1);
        petals2.add(petal2);
        petals2.add(petal3);

        when(mockcapability.getPetals()).thenReturn(petals2);
        when(mockcapability.getcapabilityName()).thenReturn("Mock");

        //When
        mockcapability = sessionCapability.removePetal(mockcapability, petal3);

        //Then 
        Assert.assertEquals(sessionCapability.collectPetals("Mock"),petals);


    }

    @Test
    /**
     * Test to check if the feature to collect petals which provides a capability works well
     */
    public void shouldCollectPetals(){
        //Given 
        Petal petal1 = new Petal();
        Petal petal2 = new Petal();
        Petal petal3 = new Petal();

        Set<Petal> petals=new HashSet<Petal>();
        petals.add(petal1);
        petals.add(petal2);
        petals.add(petal3);

        //When
        sessionCapability.addCapability("capabilityName", "namespace", properties, petal1);
        sessionCapability.addCapability("capabilityName", "namespace", properties, petal2);
        sessionCapability.addCapability("capabilityName", "namespace", properties, petal3);

        //Then
        Assert.assertEquals(sessionCapability.collectPetals("capabilityName"),petals);


    }
}
