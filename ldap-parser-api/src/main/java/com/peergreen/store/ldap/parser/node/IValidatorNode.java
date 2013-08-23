package com.peergreen.store.ldap.parser.node;

import java.util.Collection;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.Node;


public interface IValidatorNode<T> extends Node<T> {

    /**
     * Method to validate the node.<br />
     * Mainly check cardinality consistency.<br />
     * Throws {@link InvalidLdapFormatException} if node is invalid.
     * 
     * @return {@literal true} if node is valid
     * @throws InvalidLdapFormatException
     */
    boolean validate() throws InvalidLdapFormatException;

    /**
     * Method to retrieve parent node, using IValidatorNode<T> format.
     * 
     * @return parent node
     */
    IValidatorNode<T> getParentValidatorNode();

    /**
     * Method to get children nodes, using IValidatorNode<T> format.
     * 
     * @return list of children nodes, using IValidatorNode<T> format.
     */
    Collection<? extends IValidatorNode<T>> getChildrenValidatorNode();

    /**
     * Method to add a new children to current node, using IValidatorNode<T> format.<br />
     * Throws {@link InvalidLdapFormatException} if operation does not respect node validity.
     * 
     * @param child child to add
     * @throws InvalidLdapFormatException
     */
    void addChildValidatorNode(IValidatorNode<T> child) throws InvalidLdapFormatException;

    /**
     * Method to retrieve a property.
     * 
     * @param propClass property class
     * @return property
     */
    <Prop> Prop getProperty(Class<Prop> propClass);

}