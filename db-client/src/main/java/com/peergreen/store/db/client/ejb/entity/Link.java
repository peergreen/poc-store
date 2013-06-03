package com.peergreen.store.db.client.ejb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.entity.api.ILink;

/**
 * Entity Bean representing in the database a link between two stores
 */
@Entity
@SequenceGenerator(name="idLinkSeq", initialValue=1, allocationSize=50)
public class Link implements ILink {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idLinkSeq")
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
