package com.peergreen.store.ldap.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


public class DefaultNodeContext<T> implements INodeContext<T> {

    private IValidatorNode<T> node;
    private Map<Class<?>, Object> properties;
    
    public DefaultNodeContext() {
        this.properties = new HashMap<>();
    }

    @Override
    public IValidatorNode<T> getNode() {
        return this.node;
    }
    
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
     * Method to retrieve a property from the Map.
     * 
     * @param propClass property class
     * @return property corresponding property, {@literal null} otherwise
     */
    @Override
    public <Prop> Prop getProperty(Class<Prop> propClass) {
        return propClass.cast(this.properties.get(propClass));
    }
}
