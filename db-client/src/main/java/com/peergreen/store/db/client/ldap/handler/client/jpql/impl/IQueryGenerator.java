package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.node.IValidatorNode;

public interface IQueryGenerator {

    Collection<Capability> getQuery(INodeContext<? extends IValidatorNode<String>> nodeContext, boolean negated);
    
}