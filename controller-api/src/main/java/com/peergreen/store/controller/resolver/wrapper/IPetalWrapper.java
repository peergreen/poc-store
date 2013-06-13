package com.peergreen.store.controller.resolver.wrapper;

import org.osgi.resource.Resource;

import com.peergreen.store.db.client.ejb.entity.Petal;

public interface IPetalWrapper extends Resource {

    /**
     * Method to initialize attributes with an existing Petal instance.
     * @param p petal
     */
    void initFromPetal(Petal p);
    
}
