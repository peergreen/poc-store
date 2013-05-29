package com.peergreen.db.ejb.session;

import com.peergreen.db.ejb.entity.Link;

public interface ILink {

    Link addLink(int linkId, String url, String description);
    void deleteLink(int linkId);
    Link findLink(int linkId);

}