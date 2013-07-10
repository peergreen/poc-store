package com.peergreen.store.db.client.handler.ldaptree.api;

import com.peergreen.tree.Node;

public interface ILdapHandler {
    /**
     * Method to handle an operation of the tree
     * and returns a part of a query.
     * 
     * @param node The node represent the operation to handle 
     * 
     * @return A part of a query 
     */
    String toQueryElement(Node<String> node);
}