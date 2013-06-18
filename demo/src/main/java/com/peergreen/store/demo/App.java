package com.peergreen.store.demo;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import com.peergreen.store.aether.client.IPetalsPersistence;
import com.peergreen.store.controller.IStoreManagment;

@Component
@Instantiate
@Provides
public class App {
    
    @Requires
    private IStoreManagment storeManagement;
    
    @Validate
    public void main() {
        System.out.println("Running");
    }
}
