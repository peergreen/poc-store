package com.peergreen.store.ldap.parser.node;

import java.util.ArrayList;
import java.util.List;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper extends SimpleNode<String> implements IValidatorNode<String> {

    private IValidatorNode<String> parentValidator;
    private List<IValidatorNode<String>> childrenValidator;
    private ILdapHandler handler;
    private String jpql;
    
    public ValidatorNodeHelper(String data) {
        super(data);
        parentValidator = null;
        childrenValidator = new ArrayList<>();
        handler = null;
        jpql = "";
    }
    
    @Override
    public IValidatorNode<String> getParentValidatorNode() {
        return this.parentValidator;
    }

    @Override
    public void setParentValidatorNode(IValidatorNode<String> parentNode) {
        super.setParent(parentNode);
        this.parentValidator = parentNode;
    }
    
    @Override
    public List<IValidatorNode<String>> getChildrenValidatorNode() {
        return childrenValidator;
    }
    
    @Override
    public ILdapHandler getHandler() {
        return handler;
    }

    @Override
    public void setHandler(ILdapHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public String getJpql() {
        return jpql;
    }

    @Override
    public void setJpql(String jpql) {
        this.jpql = jpql;
    }
}