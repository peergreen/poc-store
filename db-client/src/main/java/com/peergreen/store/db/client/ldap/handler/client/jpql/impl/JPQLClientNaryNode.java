package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Capability> getQuery(INodeContext<? extends IValidatorNode<String>> nodeContext, boolean negated) {
        INaryNode node = nodeContext.getProperty(INaryNode.class);

        JpaContext<Capability> jpaContext = nodeContext.getProperty(JpaContext.class);

        /*
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Subquery<Capability> subquery = jpaContext.getParentQuery().subquery(Capability.class);
        jpaContext.setSubquery(subquery);
        Root<Capability> suqueryRoot = subquery.from(Capability.class);
        @SuppressWarnings("unchecked")
        Join<Capability, Property> subqueryJoin = subquery.correlate(jpaContext.getNodeContext().getProperty(Join.class));

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
            predicates[i] = sub.getRestriction();
            i++;
        }

        if (jpaContext.isNegated()) {
            // create conjunction using right operator
            if (node.getData().equals(NaryOperators.AND.getNaryOperator())) {
                subquery.select(suqueryRoot).where(
                    builder.or(predicates)
                );
            } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
                subquery.select(suqueryRoot).where(
                    builder.and(predicates)
                );
            }
        } else {
            // create conjunction using right operator
            if (node.getData().equals(NaryOperators.AND.getNaryOperator())) {
                subquery.select(suqueryRoot).where(
                    subqueryJoin.in(builder.and(predicates))
                );
            } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
                subquery.select(suqueryRoot).where(
                    builder.or(predicates)
                );
            }
        }
         */

        IValidatorNode<String> currentNode = jpaContext.getNodeContext().getNode();

        Set<Collection<Capability>>  childrenQueryResults = new HashSet<>();
        for (IValidatorNode<String> c : currentNode.getChildrenValidatorNode()) {
            childrenQueryResults.add(c.getProperty(JpaContext.class).getGeneratedQuery());
        }
        
        Set<Capability> all = new HashSet<>();
        for (Collection<Capability> coll : childrenQueryResults) {
            all.addAll(coll);
        }

        Set<Capability> res = new HashSet<>();
        if (jpaContext.isNegated()) {
            if (currentNode.getData().equals(NaryOperators.AND.getNaryOperator())) {
                // OR
                res.addAll(all);
            } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
                // AND
                for (Capability c : all) {
                    boolean allContains = true;
                    for (Collection<Capability> coll : childrenQueryResults) {
                        if (!coll.contains(c)) {
                            allContains = false;
                            break;
                        }
                    }

                    if (allContains) {
                        res.add(c);
                    }
                }
            }
        } else {
            if (currentNode.getData().equals(NaryOperators.AND.getNaryOperator())) {
                // AND
                for (Capability c : all) {
                    boolean allContains = true;
                    for (Collection<Capability> coll : childrenQueryResults) {
                        if (!coll.contains(c)) {
                            allContains = false;
                            break;
                        }
                    }

                    if (allContains) {
                        res.add(c);
                    }
                }
            } else if (node.getData().equals(NaryOperators.OR.getNaryOperator())) {
                // OR
                res.addAll(all);
            }
        }

        return res;
        //        return subquery;
    }
}