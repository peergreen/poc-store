package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

public class BinaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {
    
	private IValidatorNode<T> parent;
	
    public BinaryNode(T data) {
        super(data);
    }
    
    public boolean validate() throws InvalidLdapFormatException {
    	if (getChildren().size() == 2) {
    		return getChildren().size() == 2;
    	} else {
    		throw new InvalidLdapFormatException("Invalid binary node. Doesn't have two operands.");
    	}
    }

    public Node<T> getLeftOperand() {
        try {
			if (validate()) {
			    return getChildren().get(0);
			} else {
			    return null;
			}
		} catch (InvalidLdapFormatException e) {
			return null;
		}
    }

    public Node<T> getRightOperand() {
        try {
			if (validate()) {
			    return getChildren().get(1);
			} else {
			    return null;
			}
		} catch (InvalidLdapFormatException e) {
			return null;
		}
    }
    
    @Override
    public void setParent(IValidatorNode<T> parentNode) {
        super.setParent(parentNode);
        this.parent = parentNode;
    }
    
    @Override
    public IValidatorNode<T> getParentValidatorNode() {
    	return this.parent;
    }
}