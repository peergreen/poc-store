package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import java.util.Collection;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Subquery;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


/**
 * @author user2
 *
 * @param <T>
 */
public class JpaContext<T> {
    private boolean negated;
    private AbstractQuery<T> subquery;
    private INodeContext<? extends IValidatorNode<String>> nodeContext;
    private IQueryGenerator handler;
    
    public void negate() {
        negated = !negated;
        Collection<? extends IValidatorNode<String>> children = nodeContext.getNode().getChildrenValidatorNode();
        for (IValidatorNode<String> c : children) {
        	@SuppressWarnings("unchecked")
			JpaContext<Capability> jpaContext = c.getProperty(JpaContext.class);
        	if (jpaContext != null) {
        		jpaContext.negate();
        	}
        }
    }
    
    @SuppressWarnings("unchecked")
    public AbstractQuery<T> getParentQuery() {
        if (nodeContext.getProperty(Boolean.class)) {
            // retrieve main query (CriteriaQuery)
            return nodeContext.getParser().getProperty(CriteriaQuery.class);
        } else {
            // retrieve parent subquery
            return nodeContext.getNode().getParentValidatorNode().getProperty(JpaContext.class).getSubquery();
        }
    }
    
    public AbstractQuery<T> getSubquery() {
        return this.subquery;
    }
    
    public void setSubquery(AbstractQuery<T> subquery) {
        this.subquery = subquery;
    }
    
    public Subquery<Capability> getGeneratedQuery() {
        return handler.getQuery(nodeContext, negated);
    }
    
    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public INodeContext<? extends IValidatorNode<String>> getNodeContext() {
        return nodeContext;
    }

    public void setNodeContext(INodeContext<? extends IValidatorNode<String>> nodeContext) {
        this.nodeContext = nodeContext;
    }

    public IQueryGenerator getHandler() {
        return handler;
    }

    public void setHandler(IQueryGenerator handler) {
        this.handler = handler;
    }
    
}