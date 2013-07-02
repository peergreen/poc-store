package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.node.SimpleNode;

public class BinaryNode<T> extends SimpleNode<T> {
    
    public BinaryNode(T data) {
        super(data);
    }

    public boolean validateNode() {
        return getChildren().size() == 2;
    }
}
