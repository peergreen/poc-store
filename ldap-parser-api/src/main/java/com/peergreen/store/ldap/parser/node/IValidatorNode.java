package com.peergreen.store.ldap.parser.node;

import java.util.Set;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;

public interface IValidatorNode<T> extends Node<T> {
    boolean validate() throws InvalidLdapFormatException;
    void setParent(IValidatorNode<T> parentNode);
    IValidatorNode<T> getParentValidatorNode();
    Set<ILdapHandler<T>> getHandlers();
    void setHandlers(Set<ILdapHandler<T>> handlers);
    Set<ILdapHandler<T>> addHandler(ILdapHandler<T> handler);
    Set<ILdapHandler<T>> removeHandler(ILdapHandler<T> handler);
}