package com.peergreen.store.ldap.parser.handler;

import com.peergreen.store.ldap.parser.Element;
import com.peergreen.tree.Node;

public interface ILdapHandler {

    /**
     * Method to generate a piece of JPQL query from
     *  the generated tree using LDAP parser.
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    String toQueryElement(Node<Element> node);
    
    /**
     * Method called when an unary node is created.
     */
    void onUnaryNodeCreation();
    
    /**
     * Method called when a binary node is created.
     */
    void onBinaryNodeCreation();
    
    /**
     * Method called when an n-ary node is created.
     */
    void onNaryNodeCreation();
}