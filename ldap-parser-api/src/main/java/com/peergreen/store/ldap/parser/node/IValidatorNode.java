package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;

public interface IValidatorNode<T> extends Node<T> {
    boolean validate();
    void setParent(Node<T> parentNode);
    ILdapHandler<T> getHandler();
    void setHandler(ILdapHandler<T> handler);
}