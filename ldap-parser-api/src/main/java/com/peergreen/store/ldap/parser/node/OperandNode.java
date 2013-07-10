package com.peergreen.store.ldap.parser.node;

import com.peergreen.tree.node.SimpleNode;

public class OperandNode<T> extends SimpleNode<T> implements IValidatorNode<T> {

	private IValidatorNode<T> parent;
	
    public OperandNode(T data) {
        super(data);
    }

    @Override
    public boolean validate() {
        return getData() != null;
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