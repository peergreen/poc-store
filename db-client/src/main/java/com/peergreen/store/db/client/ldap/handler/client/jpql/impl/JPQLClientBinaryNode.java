package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 *JPQL Client for handle BinaryNode and generate a piece of JPQL query 
 */
public class JPQLClientBinaryNode implements ILdapHandler<String> {
    private BinaryNode<String> node;
    
    /**
     * Constructor with initialization.
     * 
     * @param node node associated to the JPQL handler.
     */
    public JPQLClientBinaryNode(BinaryNode<String> node) {
        this.node = node;
    }
    
    /**
     * Method to generate the piece of JPQL for the node.
     * 
     * @return corresponding piece of JPQL query or {@literal empty String} if operator not supported.
     */
    @Override
    public String toQueryElement() {
        String query = "";

        query += node.getLeftOperand() + " " + node.getData() + " " + node.getRightOperand();
        
        return query;
    }

    @Override
    public void onUnaryNodeCreation(UnaryNode<String> node) {
        // TODO Auto-generated method stub

    }
    /**
     * Register this handler on a binaryNode 
     * 
     * @param node The BinaryNode created
     */
    @Override
    public void onBinaryNodeCreation(BinaryNode<String> node) {
        node.setHandler(this);
    }

    @Override
    public void onNaryNodeCreation(NaryNode<String> node) {
        // TODO Auto-generated method stub

    }

}
