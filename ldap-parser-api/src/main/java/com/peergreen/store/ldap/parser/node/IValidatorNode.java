package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.Node;

public interface IValidatorNode<T> extends Node<T> {
    boolean validate();
    void setParent(Node<T> parentNode);
}