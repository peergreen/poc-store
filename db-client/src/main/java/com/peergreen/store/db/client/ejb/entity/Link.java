package com.peergreen.store.db.client.ejb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Link {
    @Id
    private int linkId;
    private String url; 
    private String description;

    /**
     * @return the linkId
     */
    public int getLinkId() {
        return linkId;
    }

    /**
     * @param linkId the linkId to set
     */
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
