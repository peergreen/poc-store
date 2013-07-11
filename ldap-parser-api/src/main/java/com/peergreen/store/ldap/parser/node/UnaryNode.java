package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.Node;


public class UnaryNode<T> extends ValidatorNodeHelper<T> {

    private Node<T> child;
    
    public UnaryNode(T data) {
        super(data);
    }

    public boolean validate() throws InvalidLdapFormatException {
        if (getChildren().size() == 1) {
        	return true;
        } else {
        	throw new InvalidLdapFormatException("Invalid unary node. One and only one child expected.");
        }
    }
    
    public Node<T> getChild() {
        return this.child;
    }
    
    public void setChild(Node<T> child) {
        this.child = child;
    }
}