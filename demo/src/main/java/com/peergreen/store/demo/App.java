package com.peergreen.store.demo;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import com.peergreen.store.controller.IGroupController;
import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.controller.IStoreManagment;
import com.peergreen.store.controller.IUserController;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.enumeration.Origin;

@Component
@Instantiate
@Provides
public class App {

    @Requires
    private IStoreManagment storeManagement;
    @Requires
    private IPetalController petalController;
    @Requires
    private IGroupController groupController;
    @Requires
    private IUserController userController;

    @Validate
    public void main() {
        System.out.println("Running");

        userController.addUser("Administrator", "pwd", "tut@hotmail.com");
        
        groupController.addGroup("Administrateur");
        
        storeManagement.addLink("https://store.peergreen.com/community", "Store Community");

        Category category = storeManagement.addCategory("Bundle");

        Set<Capability> capabilities = new HashSet<>();
        Set<Requirement> requirements = new HashSet<>();
        
        Vendor vendor = petalController.createVendor("Peergreen",
                "Peergreen is a software company started by the core team" +
                "who created JOnAS, the Open Application Server used for" +
                "critical production processes");
        
        File petalBinary = new File("C:\\Users\\user2\\.m2\\repository\\com\\peergreen\\" +
        		"store\\controller\\1.0-SNAPSHOT\\controller-1.0-SNAPSHOT.jar");
        
        petalController.addPetal(vendor, "Store", "0.1.0-beta", "Apps Store for Peergreen Platform",
                category, requirements, capabilities, Origin.LOCAL, petalBinary);
        
        Collection<Petal> petalsList = storeManagement.collectPetalsFromLocal();
        System.out.println("There are "+petalsList.size()+" petal(s) in local repository");
    }
}
