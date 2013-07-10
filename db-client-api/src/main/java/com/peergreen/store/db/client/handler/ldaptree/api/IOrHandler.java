package com.peergreen.store.db.client.handler.ldaptree.api;

import com.peergreen.tree.Node;

public interface IOrHandler {
    String toQueryElement(Node<String> node);
}