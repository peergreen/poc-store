package com.peergreen.store.db.client.ejb.entity;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 * Entity Bean representing in the database the user
 */
@NamedQueries({
    @NamedQuery(
            name = "User.findAll",
            query = "select u from User u")
})
@Entity

public class User {

    @Id
    private String pseudo;
    @NotNull
    private String password;

    private String email;

    @ElementCollection  
    @CollectionTable(name = "groupsofuser")
    @ManyToMany(mappedBy="users", fetch = FetchType.EAGER)
    private Set<Group> groupSet;

    public User() {

    }

    public User(String pseudo, String password, String email) {
        super();
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        Set<Group> groups = new HashSet<Group>();
        this.setGroupSet(groups);
    }

    /**
     * Method to get the user's pseudonyme
     * 
     * @return the pseudonyme of the user
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Method to set the user's pseudonyme
     * 
     * @param pseudo the pseudonyme of the user to set
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Method to get the user's password
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to set the user's password
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to get the user's email
     * 
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set the user's email 
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get the groups to which the user belongs
     * 
     * @return A Set containing all the groups to which the user belongs
     */
    public Set<Group> getGroupSet() {
        return groupSet;
    }

    /**
     * Method to add the user to new groups
     * 
     * @param groupSet A Set of new group to which the user is added
     */
    public void setGroupSet(Set<Group> groupSet) {
        this.groupSet = groupSet;
    }


}
