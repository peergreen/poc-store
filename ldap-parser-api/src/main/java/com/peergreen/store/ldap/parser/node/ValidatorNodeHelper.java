package com.peergreen.store.ldap.parser.node;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper extends SimpleNode<String> implements IValidatorNode<String> {

    private IValidatorNode<String> parentValidator;
    private Set<IValidatorNode<String>> childrenValidator;
    private ILdapHandler handler;
    private Map<Class<?>, Object> properties;

    public ValidatorNodeHelper(String data) {
        super(data);
        parentValidator = null;
        childrenValidator = new HashSet<>();
        handler = null;
    }

    /**
     * Method to retrieve parent node, using IValidatorNode<T> format.
     * 
     * @return parent node
     */
    @Override
    public IValidatorNode<String> getParentValidatorNode() {
        return this.parentValidator;
    }

    /**
     * Method to set parent node, using IValidatorNode<T> format.
     * 
     * @param parentNode parent node to set
     */
    @Override
    public void setParentValidatorNode(IValidatorNode<String> parentNode) {
        super.setParent(parentNode);
        this.parentValidator = parentNode;
    }

    /**
     * Method to get children nodes, using IValidatorNode<T> format.
     * 
     * @return list of children nodes, using IValidatorNode<T> format.
     */
    @Override
    public Collection<IValidatorNode<String>> getChildrenValidatorNode() {
        return childrenValidator;
    }

    /**
     * Method to retrieve associated handler.
     * 
     * @return associated handler
     */
    @Override
    public ILdapHandler getHandler() {
        return handler;
    }

    /**
     * Method to set associated handler.
     * 
     * @param handler associated hendler to set
     */
    @Override
    public void setHandler(ILdapHandler handler) {
        this.handler = handler;
    }

    /**
     * Method to set (or replace) a property.
     * 
     * @param propClass property class
     * @param property  property
     */
    @Override
    public <Prop> void setProperty(Class<Prop> propClass, Prop property) {
        if (property == null) {
            return;
        }
        this.properties.put(propClass, property);
    }

    /**
     * Method to retrieve a property.
     * 
     * @param propClass property class
     * @return property
     */
    @Override
    public <Prop> Prop getProperty(Class<Prop> propClass) {
        return propClass.cast(this.properties.get(propClass));
    }
}