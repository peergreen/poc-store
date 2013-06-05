package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.session.api.ISessionCategory;

@Stateless
public class DefaultCategory implements ISessionCategory{

    @Override
    public Category addCategory(String categoryName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCategory(String categoryName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Category findCategory(String categoryName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Petal> collectPetals(String categoryName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Category addPetal(Category category, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Category removePetal(Category category, Petal petal) {
        // TODO Auto-generated method stub
        return null;
    }

}
