package com.peergreen.store.db.client.ejb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Bean representing in the database a link between two stores
 */
@Entity
@Table(name="Links")
@SequenceGenerator(name="idLinkSeq", initialValue=1, allocationSize=50)
public class Link {
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idLinkSeq")
    private int linkId;

    @Id
    private String url; 

    private String description;

    /**
     * Method to retrieve the link's id
     * 
     * @return the Id of the link instance
     */
    public int getLinkId() {
        return linkId;
    }

    /**
     * Method to retrieve the link's url 
     * 
     * @return the url of the link instance 
     */
    public String getUrl() {
        return url;
    }

    /**
     * Method to set the link's url 
     * 
     * @param url the url of link to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Method to retrieve the link's description 
     * 
     * @return the description of the link instance
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set a description to the link instance 
     * 
     * @param description the description of link to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
