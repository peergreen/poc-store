package com.peergreen.store.db.client.ejb.entity.api;


import java.util.List;

/**
 * Interface defining an entity bean representing the user
 */

public interface IUser {
    
    /**
     * @return the password
     */
    String getPassword();
    
    
    /**
     * @param password the password to set
     */
    void setPassword(String password) ;
    
    
    /**
     * @return the email
     */
    String getEmail();
    
    
    /**
     * @param email the email to set
     */
    void setEmail(String email);
    
    
    /**
     * @return the groupSet
     */
    List<IGroup> getGroupSet();
    
    
    /**
     * @param groupSet the groupSet to set
     */
    void setGroupSet(List<IGroup> groupSet) ;

}
