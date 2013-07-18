package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class UnaryNode extends ValidatorNodeHelper {

    private IValidatorNode<String> child;
    
    public UnaryNode(String data) {
        super(data);
    }

    public boolean validate() throws InvalidLdapFormatException {
        if (getChildren().size() == 1) {
        	return true;
        } else {
        	throw new InvalidLdapFormatException("Invalid unary node. One and only one child expected.");
        }
    }
    
    public IValidatorNode<String> getChild() {
        return this.child;
    }
    
    public void setChild(IValidatorNode<String> child) {
        this.child = child;
    }
}