package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface ISessionCategory {

    Category addCategory(String name, String version);
    void deleteCategory(int categoryId);
    Category findCategory(int categoryId);
    Collection<Petal> collectPetals(int categoryId);
    Category addPetal(Petal petal);
    Category removePetal(Petal petal);
    
}
