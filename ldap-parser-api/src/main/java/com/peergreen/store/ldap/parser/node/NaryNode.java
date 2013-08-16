package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class NaryNode extends ValidatorNodeHelper {

    public NaryNode(String data) {
        super(data);
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
    	if (getChildren().size() > 1) {
    		return true;
    	} else {
    		throw new InvalidLdapFormatException("Invalid n-ary node. At least two children expected.");
    	}
    }
    
    /**
     * Method to add a new children to current node, using IValidatorNode<T> format.<br />
     * Throws {@link InvalidLdapFormatException} if operation does not respect node validity.
     * 
     * @param child child to add
     * @throws InvalidLdapFormatException
     */
    @Override
    public void addChildValidatorNode(IValidatorNode<String> child) {
        super.addChild(child);
    }
}