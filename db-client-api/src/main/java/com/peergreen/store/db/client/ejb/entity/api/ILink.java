package com.peergreen.store.db.client.ejb.entity.api;

/**
 * Interface defining an entity bean representing a link between two stores
 */
public interface ILink {

    /**
     * @return the linkId
     */
    public int getLinkId();


    /**
     * @param linkId the linkId to set
     */
    public void setLinkId(int linkId);


    /**
     * @return the url
     */
    public String getUrl();


    /**
     * @param url the url to set
     */
    public void setUrl(String url);


    /**
     * @return the description
     */
    public String getDescription();


    /**
     * @param description the description to set
     */
    public void setDescription(String description);
}
