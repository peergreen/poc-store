package com.peergreen.store.ldap.parser.node;

import java.util.List;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;


public interface IValidatorNode<T> extends Node<T> {
    boolean validate() throws InvalidLdapFormatException;
    void setParent(IValidatorNode<T> parentNode);
    IValidatorNode<T> getParentValidatorNode();
    List<IValidatorNode<T>> getChildrenValidatorNode();
    void addChild(IValidatorNode<T> child);
    ILdapHandler getHandler();
    void setHandler(ILdapHandler handler);
    String getJpql();
    void setJpql(String jpql);
}