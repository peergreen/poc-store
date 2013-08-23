package com.peergreen.store.ldap.parser.node;


public interface IBinaryNode extends IValidatorNode<String> {
    
    /**
     * Method to retrieve left operand.
     * 
     * @return left operand
     */
    IValidatorNode<String> getLeftOperand();

    /**
     * Method to retrieve right operand.
     * 
     * @return right operand
     */
    public IValidatorNode<String> getRightOperand();
    
}