package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 * JPQL Client to handle UnaryNode and generate a piece of JPQL query.
 */
public class JPQLClientUnaryNode implements ILdapHandler {
    private EntityManager entityManager;
    private JpaContext<Capability> jpaContext;
    private String namespace;
    private UnaryNode node;
    
    /**
     * Method to generate query for the node.
     */
    @Override
    public void toQueryElement() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Subquery<Capability> subquery = this.jpaContext.getParentQuery().subquery(Capability.class);
        Root<Capability> subRoot = subquery.from(Capability.class);
        
        subquery.select(subRoot).where(
            builder.and(
                builder.equal(subRoot.get("namespace"), namespace),
                builder.not(this.node.getProperty(JpaContext.class).getGeneratedQuery().getRestriction())
            )
        );
        
        // store resultant query in node
        this.jpaContext.setGeneratedQuery(subquery);
        this.node.setProperty(JpaContext.class, jpaContext);
    }
    
    /**
     * Register this handler on an UnaryNode 
     * 
     * @param node The unaryNode created
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onUnaryNodeCreation(INodeContext<String> nodeContext) {
        this.node = nodeContext.getProperty(UnaryNode.class);
        this.node.setHandler(this);
        
        // check if node is root
        if (nodeContext.getProperty(Boolean.class)) {
            // to remove warning, create a wrapping class without generic type
            this.jpaContext.setParentQuery(nodeContext.getProperty(CriteriaQuery.class));
        } else {
            // to remove warning, create a wrapping class without generic type
            this.jpaContext.setParentQuery(nodeContext.getProperty(Subquery.class));
        }
        
        this.namespace = nodeContext.getProperty(String.class);
    }

    @Override
    public void onBinaryNodeCreation(INodeContext<String> nodeContext) {
        // This is an UnaryNode, nothing to do on BinaryNode events.
    }

    @Override
    public void onNaryNodeCreation(INodeContext<String> nodeContext) {
     // This is an UnaryNode, nothing to do on NaryNode events.
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}