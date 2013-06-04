package com.peergreen.store.db.client.ejb.session.api;

import com.peergreen.store.db.client.ejb.entity.Link;

public interface ISessionLink {

    Link addLink(String url, String description);
    void deleteLink(int linkId);
    Link findLink(int linkId);

}