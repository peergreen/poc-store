package com.peergreen.store.ldap.parser.impl.node;

import com.peergreen.store.ldap.parser.node.IValidatorNode;

public interface IWritableValidatorNode extends IValidatorNode<String> {

    /**
     * Method to retrieve parent node, using IWritableValidatorNode<T> format.
     * 
     * @return parent node
     */
    IWritableValidatorNode getParentValidatorNode();

    /**
     * Method to set parent node, using IWritableValidatorNode format.
     * 
     * @param parentNode parent node to set
     */
    void setParentValidatorNode(IWritableValidatorNode parentNode);

    /**
     * Method to set (or replace) a property.
     * 
     * @param propClass property class
     * @param property  property
     */
    <Prop> void setProperty(Class<Prop> propClass, Prop property);

}
