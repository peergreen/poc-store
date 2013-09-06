package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

/**
 * Composite primary key of a link.

 */
public class LinkId implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = 3041115086896953178L;

    /**
     * Generated id of the link.
     */
    private int linkId;

    /**
     * Url of the link.
     */
    private String url;

    /**
     * Default Constructor.
     */
    public LinkId() {

    }

    /**
     * Constructs with the specified parameters.
     * @param linkid
     * @param linkUrl
     */
    public LinkId(int linkid, String linkUrl) {
        this.linkId = linkid;
        this.url = linkUrl;
    }
    /**
     * @return the id
     */
    public int getId() {
        return linkId;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.linkId = id;
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
     * Method for comparing an object with an instance of petalId
     * 
     * @param obj the object to compare to the petalId instance
     * @return the comparison result
     */
    public boolean equals(Object obj) {      
        Boolean b = false;
        LinkId lId = (LinkId)obj;
        if((obj instanceof LinkId)
                &&(this.getId() == lId.getId())
                &&(this.getUrl().equalsIgnoreCase(lId.getUrl()))) {
            b = true;

        }
        return b;
    }

    public int hashCode() {
        return this.getUrl().hashCode() + getId();
    }

}
