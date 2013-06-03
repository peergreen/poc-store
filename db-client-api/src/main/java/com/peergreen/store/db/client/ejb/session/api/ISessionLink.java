package com.peergreen.store.db.client.ejb.session.api;

import com.peergreen.store.db.client.ejb.entity.api.ILink;

public interface ISessionLink {

    ILink addLink(String url, String description);
    void deleteLink(int linkId);
    ILink findLink(int linkId);

}