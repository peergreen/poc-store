package com.peergreen.store.ldap.parser;

import com.peergreen.tree.Node;

/**
 * Interface providing a method to parse a LDAP filter to a tree.
 * 
 * @author Guillaume Dupraz Canard
 */
public interface ILdapParser {

    /**
     * Method to parse a LDAP filter to a tree.<br />
     * Automatically check if output tree is valid.
     * 
     * @param filter LDAP filter
     * @return tree constructed from LDAP filter parsing.
     * @throws InvalidLdapFormatException
     */
    Node<String> parse(String filter) throws InvalidLdapFormatException;

}