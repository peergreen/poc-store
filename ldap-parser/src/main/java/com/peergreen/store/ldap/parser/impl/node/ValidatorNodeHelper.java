package com.peergreen.store.ldap.parser.impl.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper extends SimpleNode<String> implements IWritableValidatorNode {

    private IWritableValidatorNode parentValidator;
    private Set<IValidatorNode<String>> childrenValidator;
    private Map<Class<?>, Object> properties;

    public ValidatorNodeHelper(String data) {
        super(data);
        parentValidator = null;
        childrenValidator = new HashSet<>();
        properties = new HashMap<>();
    }

    /**
     * Method to retrieve parent node, using IValidatorNode<T> format.
     * 
     * @return parent node
     */
    @Override
    public IWritableValidatorNode getParentValidatorNode() {
        return this.parentValidator;
    }

    /**
     * Method to set parent node, using IValidatorNode<T> format.
     * 
     * @param parentNode parent node to set
     */
    @Override
    public void setParentValidatorNode(IWritableValidatorNode parentNode) {
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
     * Method to add a new children to current node, using IValidatorNode<T> format.
     * 
     * @param child child to add
     */
    @Override
    public void addChildValidatorNode(IValidatorNode<String> child) {
        childrenValidator.add(child);
        super.addChild(child);
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