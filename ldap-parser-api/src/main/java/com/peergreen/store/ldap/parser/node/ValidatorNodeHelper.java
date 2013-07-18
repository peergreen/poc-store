package com.peergreen.store.ldap.parser.node;

import java.util.ArrayList;
import java.util.List;

import com.peergreen.store.ldap.parser.enumeration.Operators;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper extends SimpleNode<String> implements IValidatorNode<String> {

    private IValidatorNode<String> parent;
    private List<IValidatorNode<String>> children;
    private ILdapHandler handler;
    
    public ValidatorNodeHelper(String data) {
        super(data);
        parent = null;
        children = new ArrayList<>();
        handler = null;
    }
    
    @Override
    public IValidatorNode<String> getParentValidatorNode() {
        return this.parent;
    }

    @Override
    public void setParent(IValidatorNode<String> parentNode) {
        super.setParent(parentNode);
        this.parent = parentNode;
    }
    
    @Override
    public List<IValidatorNode<String>> getChildrenValidatorNode() {
        return children;
    }
    
    @Override
    public void addChild(IValidatorNode<String> child) {
        super.addChild(child);
        children.add(child);
    }

    @Override
    public ILdapHandler getHandler() {
        return handler;
    }

    @Override
    public void setHandler(ILdapHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public void visit() {
        System.out.println(getData());
        
        for (IValidatorNode<String> n : getChildrenValidatorNode()) {
            // recursive call
            n.visit();
            // ask for lazy generation
            if (Operators.isOperator(n.getData())) {
                n.getHandler().toQueryElement();
            }
        }
    }
}