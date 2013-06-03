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
	
	@Override
	public int getCapabilityId() {
		// TODO Auto-generated method stub
		return this.capabilityId;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * @return the petals
	 */
	public Set<IPetal> getPetals() {
		return petals;
	}

	/**
	 * @param petals the petals to set
	 */
	public void setPetals(Set<IPetal> petals) {
		this.petals = petals;
	}

	
}
