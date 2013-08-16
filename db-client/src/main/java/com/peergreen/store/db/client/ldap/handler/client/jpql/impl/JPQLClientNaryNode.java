package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import javax.persistence.EntityManager;

import com.peergreen.store.ldap.parser.INodeContext;
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
    private String namespace;
    private NaryNode node;

    /**
     * Method to generate the piece of JPQL for the node.
     * 
     * @return corresponding piece of JPQL query or {@literal empty String} if operator not supported.
     */
    @Override
    public String toQueryElement() {
        String query = "";

        int i = 0;
        for (IValidatorNode<String> n : node.getChildrenValidatorNode()) {
            String req = n.getJpql();

            if (i == 0) {
                query += "(" + req;
            } else if ((i % 2) == 0) {
                if (i == node.getChildrenValidatorNode().size() - 1) {
                    query += " " + req + ")";
                } else {
                    query += " " + req;
                }
            } else if ((i % 2) != 0) {
                // handle operators
                String op = "";
                if (node.getData().equals("&")) {
                    op = "AND";
                } else if (node.getData().equals("|")) {
                    op = "OR";
                }

                if (i == node.getChildrenValidatorNode().size() - 1) {
                    query += " "  + op + " " + req + ")";
                } else {
                    query += " " + op + " " + req;
                }
            }

            i++;
        }

        node.setJpql(query);

        return query;
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
    @Override
    public void onNaryNodeCreation(INodeContext<String> nodeContext) {
        this.node = nodeContext.getProperty(NaryNode.class);
        this.node.setHandler(this);
    }
    
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}