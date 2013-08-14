package com.peergreen.store.ldap.parser;

import com.peergreen.store.ldap.parser.node.IValidatorNode;

public interface INodeContext<T> {
    <Prop> void setProperty(Class<Prop> propClass, Prop property);
    <Prop> Prop getProperty(Class<Prop> propClass);
    IValidatorNode<T> getNode();
}