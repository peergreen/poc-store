package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class BinaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {
    
    private ILdapHandler<T> handler;
    
    public BinaryNode(T data) {
        super(data);
    }
    
    @Override
    public boolean validate() {
        return getChildren().size() == 2;
    }

    @Override
    public ILdapHandler<T> getHandler() {
        return handler;
    }

    @Override
    public void setHandler(ILdapHandler<T> handler) {
        this.handler = handler;
    }

    public Node<T> getLeftOperand() {
        if (validate()) {
            return getChildren().get(0);
        } else {
            return null;
        }
    }

    public Node<T> getRightOperand() {
        if (validate()) {
            return getChildren().get(1);
        } else {
            return null;
        }
    }
    
    @Override
    public void setParent(Node<T> parentNode) {
        super.setParent(parentNode);
    }
}