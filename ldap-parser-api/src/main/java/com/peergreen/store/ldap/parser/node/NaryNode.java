package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class NaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

    private ILdapHandler<T> handler;

    public NaryNode(T data) {
        super(data);
    }

    @Override
    public boolean validate() {
        return getChildren().size() > 0;
    }

    @Override
    public ILdapHandler<T> getHandler() {
        return handler;
    }

    @Override
    public void setHandler(ILdapHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public void setParent(Node<T> parentNode) {
        super.setParent(parentNode);
    }
}