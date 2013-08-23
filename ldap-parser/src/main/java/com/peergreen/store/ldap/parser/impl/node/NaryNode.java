package com.peergreen.store.ldap.parser.impl.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class NaryNode extends ValidatorNodeHelper implements IWritableNaryNode {

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
    
    @Override
    public boolean equals(Object o) {
        return (System.identityHashCode(o) == System.identityHashCode(this));
    }
}