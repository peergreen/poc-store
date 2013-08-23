package com.peergreen.store.ldap.parser.impl.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


public class BinaryNode extends ValidatorNodeHelper implements IWritableBinaryNode {
    
	private IValidatorNode<String> leftOperand;
	private IValidatorNode<String> rightOperand;
	
    public BinaryNode(String data) {
        super(data);
        leftOperand = null;
        rightOperand = null;
    }
    
    /**
     * Method to validate the node.<br />
     * Mainly check cardinality consistency.<br />
     * Throws {@link InvalidLdapFormatException} if node is invalid.
     * 
     * @return {@literal true} if node is valid
     * @throws InvalidLdapFormatException
     */
    @Override
    public boolean validate() throws InvalidLdapFormatException {
    	if (getChildren().size() == 2) {
    		return getChildren().size() == 2;
    	} else {
    		throw new InvalidLdapFormatException("Invalid binary node. Doesn't have two operands.");
    	}
    }
    
    /**
     * Method to retrieve left operand.
     * 
     * @return left operand
     */
    public IValidatorNode<String> getLeftOperand() {
        return leftOperand;
    }
    
    /**
     * Method to set left operand.
     * 
     * @param leftOperand left operand to set
     */
    public void setLeftOperand(IValidatorNode<String> leftOperand) {
        // remove previous left operand
        if (leftOperand != null) {
            getChildren().remove(leftOperand);
        }
        this.leftOperand = leftOperand;
        addChildValidatorNode(leftOperand);
    }

    /**
     * Method to retrieve right operand.
     * 
     * @return right operand
     */
    public IValidatorNode<String> getRightOperand() {
        return rightOperand;
    }
    
    /**
     * Method to set right operand.
     * 
     * @param rightOperand right operand to set
     */
    public void setRightOperand(IValidatorNode<String> rightOperand) {
        // remove previous right operand
        if (rightOperand != null) {
            getChildren().remove(rightOperand);
        }
        this.rightOperand = rightOperand;
        addChildValidatorNode(rightOperand);
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BinaryNode)) {
            return false;
        }
        
        BinaryNode other = (BinaryNode)o;
        
        return (other.leftOperand.equals(leftOperand) &&
                other.rightOperand.equals(rightOperand) &&
                other.getData().equals(getData()));
    }
    
    // TODO: hashCode
}