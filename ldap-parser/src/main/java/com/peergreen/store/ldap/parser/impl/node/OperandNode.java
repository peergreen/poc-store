package com.peergreen.store.ldap.parser.impl.node;

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
}