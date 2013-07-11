package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;
import com.peergreen.tree.Node;

/**
 *JPQL Client for handle BinaryNode and generate a piece of JPQL query 
 */
public class JPQLClientBinaryNode implements ILdapHandler<String> {

    /**
     * This handler is used when a binaryNode is created so 
     * we can get from it, his two child to generate the piece of JPQL corresponding
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    @Override
    public String toQueryElement(Node<String> node) {
        
         // The operation of this node 
        String op = node.getData();
        Node<String> childLeft = ((BinaryNode<String>) node).getLeftOperand();
        Node<String> childRight = ((BinaryNode<String>) node).getRightOperand();
        
        String attribute = "c.".concat(childLeft.getData());
        String value = '\'' + childRight.getData() + '\'' ;
        return attribute + op  + value ;
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
