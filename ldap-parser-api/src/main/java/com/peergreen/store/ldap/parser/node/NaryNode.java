package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.node.SimpleNode;

public class NaryNode<T> extends SimpleNode<T> {

    public NaryNode(T data) {
        super(data);
    }

    public boolean validateNode() {
        return getChildren().size() > 0;
    }
}
