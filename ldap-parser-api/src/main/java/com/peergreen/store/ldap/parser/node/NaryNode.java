package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class NaryNode extends ValidatorNodeHelper {

    public NaryNode(String data) {
        super(data);
    }
    
    public boolean validate() throws InvalidLdapFormatException {
    	if (getChildren().size() > 1) {
    		return true;
    	} else {
    		throw new InvalidLdapFormatException("Invalid n-ary node. At least two children expected.");
    	}
    }
}