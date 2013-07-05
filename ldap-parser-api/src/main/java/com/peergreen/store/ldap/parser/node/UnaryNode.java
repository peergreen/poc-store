package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class UnaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

    private ILdapHandler<T> handler;
    
    private Node<T> child;
    
    public UnaryNode(T data) {
        super(data);
    }

    @Override
    public boolean validate() {
        return getChildren().size() == 1;
    }
    
    @Override
    public ILdapHandler<T> getHandler() {
        return handler;
    }
    
    @Override
    public void setHandler(ILdapHandler<T> handler) {
        this.handler = handler;
    }
    
    public Node<T> getChild() {
        return this.child;
    }
    
    public void setChild(Node<T> child) {
        this.child = child;
    }
    
    @Override
    public void setParent(Node<T> parentNode) {
        super.setParent(parentNode);
    }
}