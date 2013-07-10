package com.peergreen.store.ldap.parser.handler;

import com.peergreen.tree.Node;

public interface ILdapHandler {

    /**
     * Method to generate a piece of JPQL query from
     *  the generated tree using LDAP parser.
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    String toQueryElement(Node<String> node);
}