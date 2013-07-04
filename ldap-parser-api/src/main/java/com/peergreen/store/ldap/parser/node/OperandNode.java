package com.peergreen.store.ldap.parser.node;

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
    public void setParent(Node<T> parentNode) {
        super.setParent(parentNode);
    }
}