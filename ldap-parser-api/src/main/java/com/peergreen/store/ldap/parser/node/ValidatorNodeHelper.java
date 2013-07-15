package com.peergreen.store.ldap.parser.node;

import java.util.List;

import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper<T> extends SimpleNode<T> implements IValidatorNode<T> {

    private IValidatorNode<T> parent;
    private List<IValidatorNode<T>> children;
    private String jpql;
    
    public ValidatorNodeHelper(T data) {
        super(data);
        parent = null;
        jpql = null;
    }
    
    @Override
    public IValidatorNode<T> getParentValidatorNode() {
        return this.parent;
    }

    @Override
    public void setParent(IValidatorNode<T> parentNode) {
        super.setParent(parentNode);
        this.parent = parentNode;
    }
    
    @Override
    public List<IValidatorNode<T>> getChildrenValidatorNode() {
        return children;
    }
    
    @Override
    public void addChild(IValidatorNode<T> child) {
        super.addChild(child);
        children.add(child);
    }

    @Override
    public String getJPQL() {
        return jpql;
    }

    @Override
    public void setJPQL(String jpql) {
        this.jpql = jpql;
    }
}