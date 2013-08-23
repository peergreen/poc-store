package com.peergreen.store.ldap.parser;

import com.peergreen.store.ldap.parser.node.IValidatorNode;


public interface INodeContext<T extends IValidatorNode<String>> {
    
    /**
     * Method to retrieve a property from the Map.<br />
     * If no element corresponding, method also search in parent Map (LdapFilter).
     * 
     * @param propClass property class
     * @return property corresponding property, {@literal null} otherwise
     */
    <Prop> Prop getProperty(Class<Prop> propClass);
    
    /**
     * Method to add a new property to the Map.
     * 
     * @param propClass property class
     * @param prop property
     */
    <Prop> void setProperty(Class<Prop> propClass, Prop property);
    
    /**
     * Method to retrieve associated node.
     * 
     * @return associated node
     */
    T getNode();
    
    /**
     * Method to retrieve associated parser.
     * 
     * @return associated parser
     */
    ILdapParser getParser();
    
}