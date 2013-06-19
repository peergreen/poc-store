package com.peergreen.store.db.client.ejb.key.primary;

import java.io.Serializable;

public class LinkId implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3041115086896953178L;

    private int linkId;
    private String url;

    public LinkId(){

    }

    public LinkId(int linkid, String linkUrl){
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
                &&(this.getUrl().equalsIgnoreCase(lId.getUrl()))){
            b = true;
            
        }
       return b;
    }

    public int hashCode() {
        return this.getUrl().hashCode() + getId();
    }

}
