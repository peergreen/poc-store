package com.peergreen.store.ldap.parser.element;

import com.peergreen.tree.Node;

public class UnaryElement implements IElement {

    @Override
    public String getContent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validate(Node<IElement> node) {
        return (node.getChildren().size() == 0 || node.getChildren().size() == 1);
    }

}
