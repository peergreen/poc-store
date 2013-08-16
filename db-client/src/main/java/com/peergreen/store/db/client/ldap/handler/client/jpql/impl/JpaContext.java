package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import javax.persistence.criteria.AbstractQuery;


public class JpaContext<T> {
    private AbstractQuery<T> parentQuery;
    private AbstractQuery<T> generatedQuery;
    
    public AbstractQuery<T> getParentQuery() {
        return parentQuery;
    }
    
    public void setParentQuery(AbstractQuery<T> parentQuery) {
        this.parentQuery = parentQuery;
    }
    
    public AbstractQuery<T> getGeneratedQuery() {
        return generatedQuery;
    }
    
    public void setGeneratedQuery(AbstractQuery<T> generatedQuery) {
        this.generatedQuery = generatedQuery;
    }
}