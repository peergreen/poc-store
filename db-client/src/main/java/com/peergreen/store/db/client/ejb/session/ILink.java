package com.peergreen.store.db.client.ejb.session;

import com.peergreen.store.db.client.ejb.entity.Link;

public interface ILink {

    Link addLink(int linkId, String url, String description);
    void deleteLink(int linkId);
    Link findLink(int linkId);

}