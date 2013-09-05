package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Property;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.enumeration.BinaryOperators;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.IBinaryNode;
import com.peergreen.store.ldap.parser.node.INaryNode;
import com.peergreen.store.ldap.parser.node.IUnaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


/**
 *JPQL Client for handle BinaryNode and generate a piece of JPQL query 
 */
public class JPQLClientBinaryNode implements ILdapHandler, IQueryGenerator {
    private static Log logger = LogFactory.getLog(JPQLClientBinaryNode.class);
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
        // This is a BinaryNode, nothing to do on UnaryNode events.
    }

    /**
     * Register this handler on a binaryNode 
     * 
     * @param node The BinaryNode created
     */
    @Override
    public void onBinaryNodeCreation(INodeContext<? extends IBinaryNode> nodeContext) {
        nodeContext.setProperty(IBinaryNode.class, nodeContext.getNode());

        // build node JPAContext
        JpaContext<Capability> jpaContext = new JpaContext<>();
        jpaContext.setHandler(this);
        jpaContext.setNodeContext(nodeContext);
        nodeContext.setProperty(JpaContext.class, jpaContext);
    }

    @Override
    public void onNaryNodeCreation(INodeContext<? extends INaryNode> nodeContext) {
        // This is a BinaryNode, nothing to do on NaryNode events.
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<Capability> getQuery(INodeContext<? extends IValidatorNode<String>> nodeContext, boolean negated) {
        IBinaryNode node = nodeContext.getProperty(IBinaryNode.class);

        @SuppressWarnings("unchecked")
        JpaContext<Capability> jpaContext = nodeContext.getProperty(JpaContext.class);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        // create result subquery
        /*
        Subquery<Capability> subquery = jpaContext.getParentQuery().subquery(Capability.class);
        jpaContext.setSubquery(subquery);
        Root<Capability> root = subquery.from(Capability.class);
         */

        Metamodel m = entityManager.getMetamodel();
        EntityType<Capability> capMetaModel = m.entity(Capability.class);

        CriteriaQuery<Capability> query = builder.createQuery(Capability.class);
        Root<Capability> queryRoot = query.from(Capability.class);
        Join<Capability, Property> queryJoin = queryRoot.join(capMetaModel.getSet("properties", Property.class));

        // create subqueries for left and right
        Subquery<Capability> subqueryLeft = query.subquery(Capability.class);
        Root<Capability> subRootLeft = subqueryLeft.from(Capability.class);
        Subquery<Capability> subqueryRight = query.subquery(Capability.class);
        Root<Capability> subRootRight = subqueryRight.from(Capability.class);

        Join<Capability, Property> subPropLeft = subqueryLeft.correlate(queryJoin);
        Join<Capability, Property> subPropRight = subqueryRight.correlate(queryJoin);

        // test if column exists in database
        CriteriaQuery<Capability> testQuery = builder.createQuery(Capability.class);
        Root<Capability> cap = testQuery.from(Capability.class);
        Join<Capability, Property> p = cap.join(capMetaModel.getSet("properties", Property.class));

        boolean exists = true;
        try {
            testQuery.select(cap).where(builder.equal(p.get("name"), node.getLeftOperand().getData()));
        } catch (IllegalArgumentException e) {
            exists = false;
            logger.debug("Non existent property name.", e);
        }

        // if column doesn't exist, 
        if (!exists) {
            subqueryLeft.select(subRootLeft).where(builder.not(cap.in(subqueryLeft)));
            return new HashSet<>();
        }

        subqueryLeft.select(subRootLeft).where(
                builder.equal(
                        subPropLeft.get("name"),
                        node.getLeftOperand().getData()
                        )
                );

        // else if column exists, create more complete query
        if (nodeContext.getProperty(JpaContext.class).isNegated()) {
            if (node.getData().equals(BinaryOperators.EQUAL.getBinaryOperator())) {
                subqueryRight.select(subRootRight).where(
                        builder.notEqual(
                                subPropRight.get("value"),
                                node.getRightOperand().getData()
                                )
                        );
            } else if (node.getData().equals(BinaryOperators.APPROX.getBinaryOperator())) {
                Expression<String> rightOperand = subPropLeft.get("value");
                subqueryRight.select(subRootRight).where(
                        builder.notLike(
                                rightOperand,
                                node.getRightOperand().getData().replace('*', '%')
                                )
                        );
            } else if (node.getData().equals(BinaryOperators.GREATER_THAN_EQUAL.getBinaryOperator())) {
                Expression<String> rightOperand = subPropLeft.get("value");
                subqueryRight.select(subRootRight).where(
                        builder.lessThan(
                                rightOperand,
                                node.getRightOperand().getData()
                                )
                        );
            } else if (node.getData().equals(BinaryOperators.LESSER_THAN_EQUAL.getBinaryOperator())) {
                Expression<String> rightOperand = subPropLeft.get("value");
                subqueryRight.select(subRootRight).where(
                        builder.greaterThan(
                                rightOperand,
                                node.getRightOperand().getData()
                                )
                        );
            }
        } else {
            if (node.getData().equals(BinaryOperators.EQUAL.getBinaryOperator())) {
                subqueryRight.select(subRootRight).where(
                        builder.equal(
                                subPropRight.get("value"),
                                node.getRightOperand().getData()
                                )
                        );
            } else if (node.getData().equals(BinaryOperators.APPROX.getBinaryOperator())) {
                Expression<String> rightOperand = subPropRight.get("value");
                subqueryRight.select(subRootRight).where(
                        builder.like(
                                rightOperand,
                                node.getRightOperand().getData().replace('*', '%')
                                )
                        );
            } else if (node.getData().equals(BinaryOperators.GREATER_THAN_EQUAL.getBinaryOperator())) {
                Expression<String> rightOperand = subPropRight.get("value");
                subqueryRight.select(subRootRight).where(
                        builder.greaterThanOrEqualTo(
                                rightOperand,
                                node.getRightOperand().getData()
                                )
                        );
            } else if (node.getData().equals(BinaryOperators.LESSER_THAN_EQUAL.getBinaryOperator())) {
                Expression<String> rightOperand = subPropRight.get("value");
                subqueryRight.select(subRootRight).where(
                        builder.lessThanOrEqualTo(
                                rightOperand,
                                node.getRightOperand().getData()
                                )
                        );
            }
        }

        String namespace = jpaContext.getNodeContext().getProperty(String.class);
        System.out.println("namespace: " + namespace);

        query.select(queryRoot).where(
                builder.and(
                        builder.equal(queryRoot.get("namespace"), namespace),
                        subqueryLeft.getRestriction(),
                        subqueryRight.getRestriction()
                        )
                );
        Set<Capability> caps = new HashSet<>(entityManager.createQuery(query).getResultList());

        // assemble left and right queries to form result query
        //        return subquery.select(root).where(
        //            builder.and(
        //                subqueryLeft.getRestriction(),
        //                subqueryRight.getRestriction()
        //            )
        //        );
        return caps;
    }
}
