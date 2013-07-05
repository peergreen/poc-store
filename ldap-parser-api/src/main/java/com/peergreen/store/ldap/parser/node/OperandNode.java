package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class OperandNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

    public OperandNode(T data) {
        super(data);
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public ILdapHandler<T> getHandler() {
        return null;
    }

    @Override
    public void setHandler(ILdapHandler<T> handler) {
        // no handler, nothing to do
    }

    @Override
    public void setParent(Node<T> parentNode) {
        super.setParent(parentNode);
    }
}