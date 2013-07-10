package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.node.SimpleNode;

public class NaryNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

	private IValidatorNode<T> parent;

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