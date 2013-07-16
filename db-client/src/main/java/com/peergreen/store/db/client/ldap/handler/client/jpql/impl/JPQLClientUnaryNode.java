package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 * JPQL Client to handle UnaryNode and generate a piece of JPQL query.
 */
public class JPQLClientUnaryNode implements ILdapHandler<String> {
    private UnaryNode<String> node;
    
    /**
     * Constructor with initialization.
     * 
     * @param node node associated to the JPQL handler.
     */
    public JPQLClientUnaryNode(UnaryNode<String> node) {
        this.node = node;
    }
    
    /**
     * Method to generate the piece of JPQL for the node.
     * 
     * @return corresponding piece of JPQL query or {@literal empty String} if operator not supported.
     */
    @Override
    public String toQueryElement() {
        if (node.getData().equals("!")) {
            return "NOT " + node.getChild().getHandler().toQueryElement();
        } else {
            return "";
        }
    }
    
    /**
     * Register this handler on an UnaryNode 
     * 
     * @param node The unaryNode created
     */
    @Override
    public void onUnaryNodeCreation(UnaryNode<String> node) {
        node.setHandler(this);
    }

    @Override
    public void onBinaryNodeCreation(BinaryNode<String> node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNaryNodeCreation(NaryNode<String> node) {
        // TODO Auto-generated method stub

    }


}
