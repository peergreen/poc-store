package com.peergreen.store.db.client.ejb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    
    String name;
    String value;
    
    @OneToOne
    Capability capability;
    
    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public long getId() {
        return id;
    }

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }
    
}
