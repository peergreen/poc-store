package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.IBinaryNode;
import com.peergreen.store.ldap.parser.node.INaryNode;
import com.peergreen.store.ldap.parser.node.IUnaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


/**
 * JPQL Client to handle UnaryNode and generate a piece of JPQL query.
 */
public class JPQLClientUnaryNode implements ILdapHandler, IQueryGenerator {

    /**
     * Method called when all node children have been created.<br />
     * In this implementation, generate CriteriaQuery corresponding to the node content.
     * 
     * @param nodeContext node context
     */
    @Override
    public void afterCreatingAllChildren(INodeContext<? extends IValidatorNode<String>> nodeContext) {

    }
    
    /**
     * Register this handler on an UnaryNode 
     * 
     * @param node The unaryNode created
     */
    @Override
    public void onUnaryNodeCreation(INodeContext<? extends IUnaryNode> nodeContext) {
        nodeContext.setProperty(IUnaryNode.class, nodeContext.getNode());

        // build node JPAContext
        JpaContext<Capability> jpaContext = new JpaContext<>();
        jpaContext.setHandler(this);
        jpaContext.setNodeContext(nodeContext);
        nodeContext.setProperty(JpaContext.class, jpaContext);
    }

    @Override
    public void onBinaryNodeCreation(INodeContext<? extends IBinaryNode> nodeContext) {
        // This is an UnaryNode, nothing to do on BinaryNode events.
    }

    @Override
    public void onNaryNodeCreation(INodeContext<? extends INaryNode> nodeContext) {
        // This is an UnaryNode, nothing to do on NaryNode events.
    }

    @Override
    public Subquery<Capability> getQuery(INodeContext<? extends IValidatorNode<String>> nodeContext, boolean negated) {
        IUnaryNode node = nodeContext.getProperty(IUnaryNode.class);
        
        @SuppressWarnings("unchecked")
        JpaContext<Capability> childJpaContext = node.getChild().getProperty(JpaContext.class);
        childJpaContext.negate();
        
        @SuppressWarnings("unchecked")
        JpaContext<Capability> jpaContext = node.getProperty(JpaContext.class);

        // use node contained in nodeContext
        Subquery<Capability> subquery = jpaContext.getParentQuery().subquery(Capability.class);
        jpaContext.setSubquery(subquery);
        Root<Capability> subRoot = subquery.from(Capability.class);

        return subquery.select(subRoot).where(childJpaContext.getGeneratedQuery().getRestriction());
    }
}