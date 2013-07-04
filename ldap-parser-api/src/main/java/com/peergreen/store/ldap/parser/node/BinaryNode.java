package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class BinaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {
    
    public BinaryNode(T data) {
        super(data);
    }
    
    public boolean validate() {
        return getChildren().size() == 2;
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