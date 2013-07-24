package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class BinaryNode extends ValidatorNodeHelper {
    
	private IValidatorNode<String> leftOperand;
	private IValidatorNode<String> rightOperand;
	
    public BinaryNode(String data) {
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
    
    @Override
    public void addChildValidatorNode(IValidatorNode<String> child) {
        super.addChild(child);
    }
    
    public IValidatorNode<String> getLeftOperand() {
        return leftOperand;
    }
    
    public void setLeftOperand(IValidatorNode<String> leftOperand) {
        // remove previous left operand
        if (leftOperand != null) {
            getChildren().remove(leftOperand);
        }
        this.leftOperand = leftOperand;
        addChildValidatorNode(leftOperand);
    }

    public IValidatorNode<String> getRightOperand() {
        return rightOperand;
    }
    
    public void setRightOperand(IValidatorNode<String> rightOperand) {
        // remove previous right operand
        if (rightOperand != null) {
            getChildren().remove(rightOperand);
        }
        this.rightOperand = rightOperand;
        addChildValidatorNode(rightOperand);
    }
}