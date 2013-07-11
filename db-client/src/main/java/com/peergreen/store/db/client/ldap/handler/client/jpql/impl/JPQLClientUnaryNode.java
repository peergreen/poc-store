package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;
import com.peergreen.tree.Node;

/**
 * JPQL Client for handle UnaryNode and generate a piece of JPQL query 
 *
 */
public class JPQLClientUnaryNode implements ILdapHandler<String> {
    /**
     * This handler is used when a UnaryNode is created so
     * we generate the piece of JPQL corresponding using the only child
     * of the node
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    @Override
    public String toQueryElement(Node<String> node) throws NullPointerException {

        Node<String> child = ((UnaryNode<String>) node).getChild();
        ILdapHandler<String> handler = ((IValidatorNode<String>) child).getHandler();
        if(handler == null){
            throw new NullPointerException("No handler for the child of the Unarynode");
        }
        else{
            return "NOT " + handler.toQueryElement(child);
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
