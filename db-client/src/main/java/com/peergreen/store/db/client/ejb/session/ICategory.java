package com.peergreen.store.db.client.ejb.session;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Petal;

public interface ICategory {

    Category addCategory(int categoryId, String name, String version);
    void deleteCategory(int categoryId);
    Category findCategory(int categoryId);
    Collection<Petal> collectPetals();
    Category addPetal(Petal petal);
    Category removePetal(Petal petal);
}
