package com.peergreen.store.db.client.ejb.session.api;

import com.peergreen.store.db.client.ejb.entity.api.Link;

public interface ILink {

    Link addLink(int linkId, String url, String description);
    void deleteLink(int linkId);
    Link findLink(int linkId);

}