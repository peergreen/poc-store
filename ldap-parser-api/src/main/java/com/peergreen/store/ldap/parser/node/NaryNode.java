package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class NaryNode<T> extends ValidatorNodeHelper<T> {

    public NaryNode(T data) {
        super(data);
    }
    
    public boolean validate() throws InvalidLdapFormatException {
    	if (getChildren().size() > 0) {
    		return true;
    	} else {
    		throw new InvalidLdapFormatException("Invalid n-ary node. At least one child expected.");
    	}
    }
}