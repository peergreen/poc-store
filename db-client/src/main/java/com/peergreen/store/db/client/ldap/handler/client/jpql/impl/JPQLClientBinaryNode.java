package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import javax.persistence.EntityManager;

import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;


/**
 *JPQL Client for handle BinaryNode and generate a piece of JPQL query 
 */
public class JPQLClientBinaryNode implements ILdapHandler {
    private EntityManager entityManager;
    private String namespace;
    private BinaryNode node;
    
    /**
     * Method to generate the piece of JPQL for the node.
     * 
     * @return corresponding piece of JPQL query or {@literal empty String} if operator not supported.
     */
    @Override
    public String toQueryElement() {
        String query = "";
        // TODO: JPQL style
//        String innerRequest = "SELECT c FROM Capability c WHERE c.namespace=\'" + namespace +
//                "\' AND KEY(" + mapAlias + ")=\'" + node.getLeftOperand() + "\'";
//      query += "((" + innerRequest + ")" + node.getData() + node.getRightOperand() + ")";

//        query += alias + "." + node.getLeftOperand().getData() + node.getData() + "\'" + node.getRightOperand().getData() + "\'";
//        node.setJpql(query);
        
        // TODO: Criteria style
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        Subquery<Capability> subquery = mainQuery.subquery(Capability.class);
        
        return query;
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
    @Override
    public void onBinaryNodeCreation(INodeContext<String> nodeContext) {
        this.node = nodeContext.getProperty(BinaryNode.class);
        this.node.setHandler(this);
    }

    @Override
    public void onNaryNodeCreation(INodeContext<String> nodeContext) {
        // This is a BinaryNode, nothing to do on NaryNode events.
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
