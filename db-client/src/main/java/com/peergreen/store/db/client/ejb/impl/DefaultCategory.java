package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;

@Stateless
public class DefaultCategory implements ISessionCategory{

    @Override
    public com.peergreen.store.db.client.ejb.entity.api.ICategory addCategory(String name,
            String version) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCategory(int categoryId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public com.peergreen.store.db.client.ejb.entity.api.ICategory findCategory(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPetal> collectPetals(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.peergreen.store.db.client.ejb.entity.api.ICategory addPetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.peergreen.store.db.client.ejb.entity.api.ICategory removePetal(IPetal petal) {
        // TODO Auto-generated method stub
        return null;
    }

}
