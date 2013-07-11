package com.peergreen.store.ldap.parser.node;

import java.util.HashSet;
import java.util.Set;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper<T> extends SimpleNode<T> implements IValidatorNode<T> {

    private IValidatorNode<T> parent;
    private Set<ILdapHandler<T>> handlers;
    
    public ValidatorNodeHelper(T data) {
        super(data);
        parent = null;
        handlers = new HashSet<>();
    }

    @Override
    public void setParent(IValidatorNode<T> parentNode) {
        super.setParent(parentNode);
        this.parent = parentNode;
    }
    
    @Override
    public IValidatorNode<T> getParentValidatorNode() {
        return this.parent;
    }

    @Override
    public Set<ILdapHandler<T>> getHandlers() {
        return this.handlers;
    }

    @Override
    public void setHandlers(Set<ILdapHandler<T>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Set<ILdapHandler<T>> addHandler(ILdapHandler<T> handler) {
        handlers.add(handler);
        return handlers;
    }

    @Override
    public Set<ILdapHandler<T>> removeHandler(ILdapHandler<T> handler) {
        handlers.remove(handler);
        return handlers;
    }

}
