package com.peergreen.store.db.client.handler.ldaptree.impl;

import java.util.List;

import com.peergreen.store.db.client.handler.ldaptree.api.IOrHandler;
import com.peergreen.store.ldap.parser.Element;
import com.peergreen.tree.Node;

public class OrHandler implements IOrHandler {

    @Override
    public String toQueryElement(Node<Element> node) {
        List<Node<Element>> children = node.getChildren();

        Node<Element> childL = children.get(0);
        Node<Element> childR = children.get(1);

        String childLString = "c." + childL.getData().getContent();
        String childRString = '\'' + childR.getData().getContent() + '\'';

        return childLString.concat("" + "or " +childRString);
    }

}
