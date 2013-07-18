package com.peergreen.store.ldap.parser.handler;

import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 * Interface defining call-backs for node creation events.
 * 
 * @param <T> type wrapped on nodes
 */
public interface ILdapHandler {

    /**
     * Method to generate a piece of JPQL query from
     *  the generated tree using LDAP parser.
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    String toQueryElement();
    
    /**
     * Method called when an unary node is created.
     * 
     * @param created unary node
     */
    void onUnaryNodeCreation(UnaryNode node);
    
    /**
     * Method called when a binary node is created.
     * 
     * @param created binary node
     */
    void onBinaryNodeCreation(BinaryNode node);
    
    /**
     * Method called when an n-ary node is created.
     * 
     * @param created n-ary node
     */
    void onNaryNodeCreation(NaryNode node);
}