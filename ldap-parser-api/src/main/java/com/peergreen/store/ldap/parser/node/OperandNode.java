package com.peergreen.store.ldap.parser.node;


public class OperandNode<T> extends ValidatorNodeHelper<T> {

    public OperandNode(T data) {
        super(data);
    }

    @Override
    public boolean validate() {
        return getData() != null;
    }
}