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
    String getNamespace();


    /**
     * @param namespace the namespace to set
     */
    void setNamespace(String namespace);


    /**
     * @return the properties
     */
    Map<String, String> getProperties() ;


    /**
     * @param properties the properties to set
     */
    void setProperties(Map<String, String> properties) ;


    /**
     * @return the petals
     */
    List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    void setPetals(List<IPetal> petals) ;
}
