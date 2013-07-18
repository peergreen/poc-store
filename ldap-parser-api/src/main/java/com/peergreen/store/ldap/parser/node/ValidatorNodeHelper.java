package com.peergreen.store.ldap.parser.node;

import java.util.ArrayList;
import java.util.List;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper extends SimpleNode<String> implements IValidatorNode<String> {

    private IValidatorNode<String> parent;
    private List<IValidatorNode<String>> children;
    private ILdapHandler handler;
    private String jpql;
    
    public ValidatorNodeHelper(String data) {
        super(data);
        parent = null;
        children = new ArrayList<>();
        handler = null;
        jpql = "";
    }
    
    @Override
    public IValidatorNode<String> getParentValidatorNode() {
        return this.parent;
    }

    @Override
    public void setParent(IValidatorNode<String> parentNode) {
        super.setParent(parentNode);
        this.parent = parentNode;
    }
    
    @Override
    public List<IValidatorNode<String>> getChildrenValidatorNode() {
        return children;
    }
    
    @Override
    public void addChild(IValidatorNode<String> child) {
        super.addChild(child);
        children.add(child);
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