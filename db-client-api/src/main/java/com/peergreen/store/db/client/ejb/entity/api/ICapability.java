package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;
import java.util.Map;

/**
 *Interface Entity Bean representing capability of a petal
 */

public interface ICapability {

    /**
     * @return the namespace
     */
    public String getNamespace();


    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace);


    /**
     * @return the properties
     */
    public Map<String, String> getProperties() ;


    /**
     * @param properties the properties to set
     */
    public void setProperties(Map<String, String> properties) ;


    /**
     * @return the petals
     */
    public List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    public void setPetals(List<IPetal> petals) ;
}
