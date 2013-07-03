package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class UnaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

    private Node<T> child;
    
    public UnaryNode(T data) {
        super(data);
    }

    public boolean validate() {
        return getChildren().size() == 1;
    }
    
    public Node<T> getChild() {
        return this.child;
    }
    
    public void setChild(Node<T> child) {
        this.child = child;
    }
}
