package com.peergreen.store.demo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.controller.IStoreManagment;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionVendor;
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
    private ISessionCategory categorySession;
    @Requires
    private ISessionPetal petalSession;
    @Requires
    private ISessionVendor vendorSession;

    @Validate
    public void main() {
        System.out.println("Running");

        storeManagement.addLink("https://store.peergreen.com/community", "Store Community");

        Vendor vendor = vendorSession.addVendor("Peergreen",
                "Peergreen is a software company started by the core team" +
                "who created JOnAS, the Open Application Server used for" +
                "critical production processes");

        Category category = categorySession.addCategory("Bundle");
        
        Set<Capability> capabilities = new HashSet<>();
        Set<Requirement> requirements = new HashSet<>();
        
        petalSession.addPetal(vendor, "Store", "0.1.0-beta", "Store module for Peergreen Platform",
                category, capabilities, requirements, Origin.LOCAL);
        
//        Collection<Petal> petalsList = storeManagement.collectPetalsFromLocal();
//        System.out.println("There are "+petalsList.size()+" petal(s) in local repository");
    }
}
