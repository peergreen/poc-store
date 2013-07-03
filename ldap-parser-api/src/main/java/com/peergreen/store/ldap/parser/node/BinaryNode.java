package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class BinaryNode<T> extends SimpleNode<T> {
    
    private Node<T> leftOperand;
    private Node<T> rightOperand;
    
    public BinaryNode(T data) {
        super(data);
    }

    public boolean validateNode() {
        return getChildren().size() == 2;
    }

    public Node<T> getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(Node<T> leftOperand) {
        this.leftOperand = leftOperand;
    }

    public Node<T> getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(Node<T> rightOperand) {
        this.rightOperand = rightOperand;
    }
}
