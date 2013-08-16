package com.peergreen.store.ldap.parser.handler;

import com.peergreen.store.ldap.parser.INodeContext;


/**
 * Interface defining call-backs for node creation events.
 * 
 * @param <T> type wrapped on nodes
 */
public interface ILdapHandler {

    /**
     * Method to generate query for node.
     */
    void toQueryElement();
    
    /**
     * Method called when an unary node is created.
     * 
     * @param created unary node
     */
    void onUnaryNodeCreation(INodeContext<String> nodeContext);
    
    /**
     * Method called when a binary node is created.
     * 
     * @param created binary node
     */
    void onBinaryNodeCreation(INodeContext<String> nodeContext);
    
    /**
     * Method called when an n-ary node is created.
     * 
     * @param created n-ary node
     */
    void onNaryNodeCreation(INodeContext<String> nodeContext);
}