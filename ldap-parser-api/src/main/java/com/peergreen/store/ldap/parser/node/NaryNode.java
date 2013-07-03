package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.node.SimpleNode;

public class NaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

    public NaryNode(T data) {
        super(data);
    }
    
    public boolean validate() {
        return getChildren().size() > 0;
    }
}