package com.peergreen.store.db.client.ejb.entity.api;


import java.util.List;

/**
 * Interface defining an entity bean representing the user
 */

public interface IUser {

    /**
     * @return the pseudo
     */
    public String getPseudo();
   
    
    /**
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo);
    
    
    /**
     * @return the password
     */
    public String getPassword();
    
    
    /**
     * @param password the password to set
     */
    public void setPassword(String password) ;
    
    
    /**
     * @return the email
     */
    public String getEmail();
    
    
    /**
     * @param email the email to set
     */
    public void setEmail(String email);
    
    
    /**
     * @return the groupSet
     */
    public List<IGroup> getGroupSet();
    
    
    /**
     * @param groupSet the groupSet to set
     */
    public void setGroupSet(List<IGroup> groupSet) ;


}
