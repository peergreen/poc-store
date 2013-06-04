package com.peergreen.store.db.client.ejb.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;


/**
 * Entity Bean representing in the database the capability of a petal
 */
@Entity
@SequenceGenerator(name="idCapabilitySeq", initialValue=1, allocationSize=50)
public class Capability implements ICapability {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCapabilitySeq")
	private int capabilityId;

	private String namespace;

	private Map<String, String> properties;

	@JoinTable(name = "CAPABILITY_PETAL_MAP",
			joinColumns = {@JoinColumn(name = "capabilityId", referencedColumnName = "capabilityId")},
			inverseJoinColumns = {@JoinColumn(name = "petalId", referencedColumnName = "petalId")})
	private Set<IPetal> petals;

	/**
	 * Method to get the id of the capability instance
	 * 
	 * @return the id of the capability
	 */
	public int getCapabilityId() {
		return this.capabilityId;
	}

	/**
	 * Method to get the namespace of the capability instance
	 * 
	 * @return the namespace of the capability
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * Method to set the id of the capability instance
	 * 
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * Method for retrieve the properties of the capability instance
	 * 
	 * @return Map containing all the properties of the capability
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Method for set the properties of the capability instance
	 * 
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * Method to retrieve the petals which provides this capability instance
	 * 
	 * @return Set containing petals 
	 */
	public Set<IPetal> getPetals() {
		return petals;
	}

	/**
	 * Method for add others petals to the Set of petals which provides
	 * this capability instance
	 * 
	 * @param petals Set containing petals to set
	 */
	public void setPetals(Set<IPetal> petals) {
		this.petals = petals;
	}


}
