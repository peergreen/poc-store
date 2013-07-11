package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper<T> extends SimpleNode<T> implements IValidatorNode<T> {

    private IValidatorNode<T> parent;
    private ILdapHandler<T> handler;
    
    public ValidatorNodeHelper(T data) {
        super(data);
        parent = null;
        handler = null;
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
    public ILdapHandler<T> getHandler() {
        return this.handler;
    }

    @Override
    public void setHandler(ILdapHandler<T> handler) {
        this.handler = handler;
    }
}