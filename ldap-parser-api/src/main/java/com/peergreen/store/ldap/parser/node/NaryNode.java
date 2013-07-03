package com.peergreen.store.ldap.parser.node;

import java.util.List;

import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class NaryNode<T> extends SimpleNode<T> {

    private List<Node<T>> children;
    
    public NaryNode(T data) {
        super(data);
    }

    public boolean validateNode() {
        return getChildren().size() > 0;
    }

    @Override
    public List<Node<T>> getChildren() {
        return this.children;
    }

}
