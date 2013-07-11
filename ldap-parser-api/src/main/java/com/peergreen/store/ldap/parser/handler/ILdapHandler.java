package com.peergreen.store.ldap.parser.handler;

import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;
import com.peergreen.tree.Node;


/**
 * Interface defining call-backs for node creation events.
 * 
 * @param <T> type wrapped on nodes
 */
public interface ILdapHandler<T> {

    /**
     * Method to generate a piece of JPQL query from
     *  the generated tree using LDAP parser.
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    String toQueryElement(Node<String> node);
    
    /**
     * Method called when an unary node is created.
     * 
     * @param created node
     */
    void onUnaryNodeCreation(UnaryNode<T> node);
    
    /**
     * Method called when a binary node is created.
     * 
     * @param created node
     */
    void onBinaryNodeCreation(BinaryNode<T> node);
    
    /**
     * Method called when an n-ary node is created.
     * 
     * @param created node
     */
    void onNaryNodeCreation(NaryNode<T> node);
}