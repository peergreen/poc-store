package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.enumeration.BinaryOperators;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;


/**
 *JPQL Client for handle BinaryNode and generate a piece of JPQL query 
 */
public class JPQLClientBinaryNode implements ILdapHandler {
    private EntityManager entityManager;
    private JpaContext<Capability> jpaContext;
    private String namespace;
    private BinaryNode node;
    
    public JPQLClientBinaryNode() {
        jpaContext = new JpaContext<>();
    }
    
    /**
     * Method to generate query for the node.
     */
    @Override
    public void toQueryElement() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Subquery<Capability> subquery = jpaContext.getParentQuery().subquery(Capability.class);
        Root<Capability> subRoot = subquery.from(Capability.class);
        
        if (node.getData().equals(BinaryOperators.EQUAL.getBinaryOperator())) {
            subquery.select(subRoot).where(
                builder.and(
                    builder.equal(subRoot.get("namespace"), namespace),
                    builder.equal(subRoot.get(node.getLeftOperand().getData()), node.getRightOperand().getData())
                )
            );
        } else if (node.getData().equals(BinaryOperators.APPROX.getBinaryOperator())) {
            Expression<String> leftOperand = subRoot.get(node.getLeftOperand().getData());
            subquery.select(subRoot).where(
                builder.and(
                    builder.equal(subRoot.get("namespace"), namespace),
                    builder.like(leftOperand, node.getRightOperand().getData())
                )
            );
        } else if (node.getData().equals(BinaryOperators.GREATER_THAN_EQUAL.getBinaryOperator())) {
            Expression<String> leftOperand = subRoot.get(node.getLeftOperand().getData());
            subquery.select(subRoot).where(
                builder.and(
                    builder.equal(subRoot.get("namespace"), namespace),
                    builder.greaterThanOrEqualTo(leftOperand, node.getRightOperand().getData())
                )
            );
        } else if (node.getData().equals(BinaryOperators.LESSER_THAN_EQUAL.getBinaryOperator())) {
            Expression<String> leftOperand = subRoot.get(node.getLeftOperand().getData());
            subquery.select(subRoot).where(
                builder.and(
                    builder.equal(subRoot.get("namespace"), namespace),
                    builder.lessThanOrEqualTo(leftOperand, node.getRightOperand().getData())
                )
            );
        }
        
        // store resultant query in node
        this.jpaContext.setGeneratedQuery(subquery);
        this.node.setProperty(JpaContext.class, jpaContext);
    }

    @Override
    public void onUnaryNodeCreation(INodeContext<String> nodeContext) {
        // This is a BinaryNode, nothing to do on UnaryNode events.
    }
    
    /**
     * Register this handler on a binaryNode 
     * 
     * @param node The BinaryNode created
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onBinaryNodeCreation(INodeContext<String> nodeContext) {
        this.node = nodeContext.getProperty(BinaryNode.class);
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
    public void onNaryNodeCreation(INodeContext<String> nodeContext) {
        // This is a BinaryNode, nothing to do on NaryNode events.
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
