package com.peergreen.store.ldap.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.peergreen.store.ldap.parser.ILdapParser;
import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


public class DefaultNodeContext<T> implements INodeContext<T> {

    private IValidatorNode<T> node;
    private Map<Class<?>, Object> properties;
    private ILdapParser parser;

    public DefaultNodeContext() {
        this.properties = new HashMap<>();
    }

    /**
     * Method to retrieve associated node.
     * 
     * @return associated node
     */
    @Override
    public IValidatorNode<T> getNode() {
        return this.node;
    }

    /**
     * Method to set associated node.
     * 
     * @param node associated node to set
     */
    public void setNode(IValidatorNode<T> node) {
        this.node = node;
    }

    /**
     * Method to add a new property to the Map.
     * 
     * @param propClass property class
     * @param prop property
     */
    @Override
    public <Prop> void setProperty(Class<Prop> propClass, Prop property) {
        if (property == null) {
            return;
        }
        this.properties.put(propClass, property);    
    }

    /**
     * Method to retrieve a property from the Map.<br />
     * If no element corresponding, method also search in parent Map (LdapFilter).
     * 
     * @param propClass property class
     * @return property corresponding property, {@literal null} otherwise
     */
    @Override
    public <Prop> Prop getProperty(Class<Prop> propClass) {
        Prop elem = propClass.cast(this.properties.get(propClass));
        
        // if no corresponding element, search in parent properties Map
        if (elem == null) {
            elem = propClass.cast(parser.getProperty(propClass));
        }
        
        return elem;
    }
    
    /**
     * Method to set parser reference.
     * 
     * @param parser parser reference to set
     */
    public void setParser(ILdapParser parser) {
        this.parser = parser;
    }
}
