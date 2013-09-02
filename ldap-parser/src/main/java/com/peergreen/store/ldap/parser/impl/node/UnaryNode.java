package com.peergreen.store.ldap.parser.impl.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


public class UnaryNode extends ValidatorNodeHelper implements IWritableUnaryNode {

    private IValidatorNode<String> child;

    public UnaryNode(String data) {
        super(data);
        child = null;
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
        if (child != null) {
            return true;
        } else {
            throw new InvalidLdapFormatException("Invalid unary node. One and only one child expected.");
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
    public void addChildValidatorNode(IValidatorNode<String> child) throws InvalidLdapFormatException {
        if (this.child == null) {
            super.addChildValidatorNode(child);
            this.child = child;
        } else {
            throw new InvalidLdapFormatException("UnaryNode cannot have several children.");
        }
    }

    /**
     * Method to retrieve node child.
     * 
     * @return child
     */
    public IValidatorNode<String> getChild() {
        return this.child;
    }

    /**
     * Method to set node child.
     * 
     * @param child child to set
     */
    public void setChild(IValidatorNode<String> child) {
        if (child != null) {
            getChildren().remove(child);
        }
        this.child = child;
    }
    
    @Override
    public boolean equals(Object o) {
        return (System.identityHashCode(o) == System.identityHashCode(this));
    }
}