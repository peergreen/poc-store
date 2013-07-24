package com.peergreen.store.ldap.parser.node;


public class OperandNode extends ValidatorNodeHelper {

    public OperandNode(String data) {
        super(data);
    }

    @Override
    public boolean validate() {
        return getData() != null;
    }

    @Override
    public void addChildValidatorNode(IValidatorNode<String> child) {
        super.addChild(child);
    }
}