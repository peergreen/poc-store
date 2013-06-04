package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;

@Stateless
public class DefaultCategory implements ISessionCategory{

    @Override
    public Category addCategory(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCategory(int categoryId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Category findCategory(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Category addPetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Category removePetal(Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

}
