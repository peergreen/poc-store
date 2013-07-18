package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 *JPQL Client for handle BinaryNode and generate a piece of JPQL query 
 */
public class JPQLClientBinaryNode implements ILdapHandler {
    private String alias;
    private BinaryNode node;
    
    public JPQLClientBinaryNode() {
        this.alias = "cap";
    }
    
    /**
     * Method to generate the piece of JPQL for the node.
     * 
     * @return corresponding piece of JPQL query or {@literal empty String} if operator not supported.
     */
    @Override
    public String toQueryElement() {
        String query = "";

        query += alias + "." + node.getLeftOperand().getData() + node.getData() + "\'" + node.getRightOperand().getData() + "\'";
        
        return query;
    }

    @Override
    public void onUnaryNodeCreation(UnaryNode node) {
        // This is a BinaryNode, nothing to do on UnaryNode events.
    }
    
    /**
     * Register this handler on a binaryNode 
     * 
     * @param node The BinaryNode created
     */
    @Override
    public void onBinaryNodeCreation(BinaryNode node) {
        node.setHandler(this);
        this.node = node;
    }

    @Override
    public void onNaryNodeCreation(NaryNode node) {
        // This is a BinaryNode, nothing to do on NaryNode events.
    }

}
