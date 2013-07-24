package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class UnaryNode extends ValidatorNodeHelper {

    private IValidatorNode<String> child;

    public UnaryNode(String data) {
        super(data);
        child = null;
    }

    public boolean validate() throws InvalidLdapFormatException {
        if (child != null) {
            return true;
        } else {
            throw new InvalidLdapFormatException("Invalid unary node. One and only one child expected.");
        }
    }

    @Override
    public void addChildValidatorNode(IValidatorNode<String> child) throws InvalidLdapFormatException {
        if (this.child == null) {
            super.addChild(child);
            this.child = child;
        } else {
            throw new InvalidLdapFormatException("UnaryNode cannot have several children.");
        }
    }

    public IValidatorNode<String> getChild() {
        return this.child;
    }

    public void setChild(IValidatorNode<String> child) {
        if (child != null) {
            getChildren().remove(child);
        }
        this.child = child;
    }
}