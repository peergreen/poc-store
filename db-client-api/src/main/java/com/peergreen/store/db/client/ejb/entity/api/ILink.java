package com.peergreen.store.db.client.ejb.entity.api;

/**
 * Interface defining an entity bean representing a link between two stores
 */
public interface ILink {

    /**
     * Method to retrieve the link's url 
     * 
     * @return the url of the link instance 
     */
    String getUrl();


    /**
     * Method to set the link's url 
     * 
     * @param the url of link to set
     */
    void setUrl(String url);


    /**
     * Method to retrieve the link's description 
     * 
     * @return the description of the link instance
     */
    String getDescription();


    /**
     * Method to set a description to the link instance 
     * 
     * @param the description of link to set
     */
    void setDescription(String description);
}
