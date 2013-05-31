package com.peergreen.store.db.client.ejb.entity.api;

/**
 * Interface defining an entity bean representing a link between two stores
 */
public interface ILink {

    /**
     * @return the linkId
     */
    int getLinkId();


    /**
     * @param linkId the linkId to set
     */
    void setLinkId(int linkId);


    /**
     * @return the url
     */
    String getUrl();


    /**
     * @param url the url to set
     */
    void setUrl(String url);


    /**
     * @return the description
     */
    String getDescription();


    /**
     * @param description the description to set
     */
    void setDescription(String description);
}
