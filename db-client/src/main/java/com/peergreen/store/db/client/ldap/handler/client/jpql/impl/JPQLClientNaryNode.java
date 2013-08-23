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
import com.peergreen.store.ldap.parser.node.IBinaryNode;
import com.peergreen.store.ldap.parser.node.INaryNode;
import com.peergreen.store.ldap.parser.node.IUnaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


/**
 * 
 *JPQL Client for handle NaryNode and generate a piece of JPQL query 
 *
 */
public class JPQLClientNaryNode implements ILdapHandler, IQueryGenerator {
    private EntityManager entityManager;

    /**
     * Method called when all node children have been created.<br />
     * In this implementation, generate CriteriaQuery corresponding to the node content.
     * 
     * @param nodeContext node context
     */
    @Override
    public void afterCreatingAllChildren(INodeContext<? extends IValidatorNode<String>> nodeContext) {
        
    }

    @Override
    public void onUnaryNodeCreation(INodeContext<? extends IUnaryNode> nodeContext) {
        // This is a NaryNode, nothing to do on UnaryNode events.
    }

    @Override
    public void onBinaryNodeCreation(INodeContext<? extends IBinaryNode> nodeContext) {
        // This is a NaryNode, nothing to do on BinaryNode events.
    }

    /**
     * Register this handler on a NaryNode 
     * 
     * @param node The NaryNode created
     */
    @Override
    public void onNaryNodeCreation(INodeContext<? extends INaryNode> nodeContext) {
        nodeContext.setProperty(INaryNode.class, nodeContext.getNode());

        // build node JPAContext
        JpaContext<Capability> jpaContext = new JpaContext<>();
        jpaContext.setHandler(this);
        jpaContext.setNodeContext(nodeContext);
        nodeContext.setProperty(JpaContext.class, jpaContext);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Subquery<Capability> getQuery(INodeContext<? extends IValidatorNode<String>> nodeContext, boolean negated) {
        INaryNode node = nodeContext.getProperty(INaryNode.class);

        @SuppressWarnings("unchecked")
        JpaContext<Capability> jpaContext = nodeContext.getProperty(JpaContext.class);
        
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Subquery<Capability> subquery = jpaContext.getParentQuery().subquery(Capability.class);
        jpaContext.setSubquery(subquery);
        Root<Capability> subRoot = subquery.from(Capability.class);

        // generate a var args containing all children subqueries
        Collection<? extends IValidatorNode<String>> children = node.getChildrenValidatorNode();
        Predicate[] predicates = new Predicate[children.size()];
        
        @SuppressWarnings("rawtypes")
        Subquery[] subqueries = new Subquery[children.size()];
        
        int i = 0;
        for (IValidatorNode<String> n : children) {
            predicates[i] = n.getProperty(JpaContext.class).getGeneratedQuery().getRestriction();
            
            Predicate p = n.getProperty(JpaContext.class).getGeneratedQuery().getRestriction();
            Subquery<Capability> sub = jpaContext.getParentQuery().subquery(Capability.class);
            Root<Capability> sRoot = sub.from(Capability.class);
            sub.select(sRoot).where(p);
            
            subqueries[i] = sub;
            i++;
        }
        
        if (jpaContext.isNegated()) {
            // create conjunction using right operator
            if (node.getData().equals(NaryOperators.AND.getNaryOperator())) {
                subquery.select(subRoot).where(
                    builder.or(predicates)
                );
            } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
                subquery.select(subRoot).where(
                    builder.and(predicates)
                );
            }
        } else {
            // create conjunction using right operator
            if (node.getData().equals(NaryOperators.AND.getNaryOperator())) {
                subquery.select(subRoot).where(
                    subRoot.in(subqueries)
                );
            } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
                subquery.select(subRoot).where(
                    builder.or(predicates)
                );
            }
        }
        
        return subquery;
    }
}