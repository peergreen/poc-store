package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 * 
 *JPQL Client for handle NaryNode and generate a piece of JPQL query 
 *
 */
public class JPQLClientNaryNode implements ILdapHandler<String> {
    private NaryNode<String> node;
    
    /**
     * Constructor with initialization.
     * 
     * @param node node associated to the JPQL handler.
     */
    public JPQLClientNaryNode(NaryNode<String> node) {
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

        int i = 0;
        for (IValidatorNode<String> n : node.getChildrenValidatorNode()) {
            String req = n.getHandler().toQueryElement();
            
            if (i == 0) {
                query += req;
            } else if ((i % 2) == 0) {
                query += " " + req;
            } else if ((i % 2) == 1) {
                query += " " + node.getData() + " " + req;
            }
            
            i++;
        }

        return query;
    }

    @Override
    public void onUnaryNodeCreation(UnaryNode<String> node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBinaryNodeCreation(BinaryNode<String> node) {
        // TODO Auto-generated method stub

    }
    
    /**
     * Register this handler on a NaryNode 
     * 
     * @param node The NaryNode created
     */
    @Override
    public void onNaryNodeCreation(NaryNode<String> node) {
        node.setHandler(this);
    }

}
