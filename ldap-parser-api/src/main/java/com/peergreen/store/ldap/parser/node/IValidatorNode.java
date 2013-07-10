package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.Node;

public interface IValidatorNode<T> extends Node<T> {
    boolean validate() throws InvalidLdapFormatException;
    void setParent(IValidatorNode<T> parentNode);
    IValidatorNode<T> getParentValidatorNode();
}