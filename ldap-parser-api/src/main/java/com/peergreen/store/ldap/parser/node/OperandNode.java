package com.peergreen.store.ldap.parser.node;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;


public class OperandNode extends ValidatorNodeHelper {

    public OperandNode(String data) {
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
    public boolean validate() {
        return getData() != null;
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