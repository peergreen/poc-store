package com.peergreen.store.db.client.ejb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.key.primary.LinkId;

/**
 * Entity Bean representing in the database a link between two stores.
 */
@NamedQueries({
    @NamedQuery(
            name = "Link.findAll",
            query = "select l from Link l"),
            @NamedQuery(
                    name = "LinkByUrl",
                    query = "select l from Link l where l.url = :url"),
                    @NamedQuery(
                            name = "LinkById",
                            query = "select l from Link l where l.linkId = :id")
})
@Entity
@IdClass(LinkId.class)
public class Link {

    /**
     * Generated id of the link.
     */
    @Id
    @SequenceGenerator(name = "idLinkSeq", initialValue = 1,
    allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idLinkSeq")
    private int linkId;

    /**
     * Url of the link.
     */
    @Id
    private String url;

    /**
     * Description of the link.
     */
    private String description;

    /**
     * Default Constructor.
     */
    public Link() {

    }

    /**
     * Constructs a link with the specified parameters.
     * @param url
     * @param description
     */
    public Link(String url, String description) {
        super();
        this.url = url;
        this.description = description;
    }

    /**
     * Method to retrieve the link's id.
     *
     * @return the Id of the link instance
     */
    public int getLinkId() {
        return linkId;
    }

    /**
     * Method to retrieve the link's url.
     *
     * @return the url of the link instance
     */
    public String getUrl() {
        return url;
    }

    /**
     * Method to retrieve the link's description.
     *
     * @return the description of the link instance
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set a description to the link instance.
     *
     * @param description the description of link to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return linkId + "-" + url + ":" + description;
    }

}
