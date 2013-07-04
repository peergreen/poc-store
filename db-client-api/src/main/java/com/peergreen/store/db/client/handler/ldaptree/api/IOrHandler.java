package com.peergreen.store.db.client.handler.ldaptree.api;

import com.peergreen.store.ldap.parser.Element;
import com.peergreen.tree.Node;

public interface IOrHandler {
    String toQueryElement(Node<Element> node);

}
