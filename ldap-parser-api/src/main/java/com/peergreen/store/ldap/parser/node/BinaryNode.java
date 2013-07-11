package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.Node;


public class BinaryNode<T> extends ValidatorNodeHelper<T> {
    
	private IValidatorNode<T> leftOperand;
	private IValidatorNode<T> rightOperand;
	
    public BinaryNode(T data) {
        super(data);
        leftOperand = null;
        rightOperand = null;
    }
    
    @Override
    public boolean validate() throws InvalidLdapFormatException {
    	if (getChildren().size() == 2) {
    		return getChildren().size() == 2;
    	} else {
    		throw new InvalidLdapFormatException("Invalid binary node. Doesn't have two operands.");
    	}
    }

    public Node<T> getLeftOperand() {
        return leftOperand;
    }
    
    public void setLeftOperand(IValidatorNode<T> leftOperand) {
        // remove previous left operand
        if (leftOperand != null) {
            getChildren().remove(leftOperand);
        }
        this.leftOperand = leftOperand;
    }

    public Node<T> getRightOperand() {
        return rightOperand;
    }
    
    public void setRightOperand(IValidatorNode<T> rightOperand) {
        // remove previous right operand
        if (rightOperand != null) {
            getChildren().remove(rightOperand);
        }
        this.rightOperand = rightOperand;
    }
}