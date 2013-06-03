package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;

public interface ISessionCategory {

    ICategory addCategory(int categoryId, String name, String version);
    void deleteCategory(int categoryId);
    ICategory findCategory(int categoryId);
    Collection<IPetal> collectPetals(int categoryId);
    ICategory addPetal(IPetal petal);
    ICategory removePetal(IPetal petal);
}
