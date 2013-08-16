package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.enumeration.NaryOperators;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.store.ldap.parser.node.NaryNode;


/**
 * 
 *JPQL Client for handle NaryNode and generate a piece of JPQL query 
 *
 */
public class JPQLClientNaryNode implements ILdapHandler {
    private EntityManager entityManager;
    private JpaContext<Capability> jpaContext;
    private String namespace;
    private NaryNode node;

    /**
     * Method to generate query for the node.
     */
    @Override
    public void toQueryElement() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Subquery<Capability> subquery = jpaContext.getParentQuery().subquery(Capability.class);
        Root<Capability> subRoot = subquery.from(Capability.class);
        
        // generate a var args containing all children subqueries
        Collection<IValidatorNode<String>> children = node.getChildrenValidatorNode();
        Predicate[] predicates = new Predicate[children.size()];
        int i = 0;
        for (IValidatorNode<String> n : children) {
            predicates[i] = n.getProperty(Subquery.class).getRestriction();
        }
        
        // create conjunction using right operator
        if (node.getData().equals(NaryOperators.AND.getNaryOperator())) {
            subquery.select(subRoot).where(
                builder.and(
                    builder.equal(subRoot.get("namespace"), namespace),
                    builder.and(predicates)
                )
            );
        } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
            subquery.select(subRoot).where(
                    builder.and(
                        builder.equal(subRoot.get("namespace"), namespace),
                        builder.or(predicates)
                    )
            );
        }

        // store resultant query in node
        this.jpaContext.setGeneratedQuery(subquery);
        this.node.setProperty(JpaContext.class, jpaContext);
    }

    @Override
    public void onUnaryNodeCreation(INodeContext<String> nodeContext) {
        // This is a NaryNode, nothing to do on UnaryNode events.
    }

    @Override
    public void onBinaryNodeCreation(INodeContext<String> nodeContext) {
        // This is a NaryNode, nothing to do on BinaryNode events.
    }

    /**
     * Register this handler on a NaryNode 
     * 
     * @param node The NaryNode created
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onNaryNodeCreation(INodeContext<String> nodeContext) {
        this.node = nodeContext.getProperty(NaryNode.class);
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
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}