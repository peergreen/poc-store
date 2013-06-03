package com.peergreen.store.db.client.ejb.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

/**
 * Entity Bean representing in the database the user
 */
@Entity
public class User implements IUser {

    @Id
    private String pseudo;
    private String password;
    private String email;
    @JoinTable(name = "USERS_GROUPS_MAP",
            joinColumns = {@JoinColumn(name = "personnePseudo", referencedColumnName = "pseudo")},
            inverseJoinColumns = {@JoinColumn(name = "groupName", referencedColumnName = "groupname")})
    private List<IGroup> groupSet;

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the groupSet
     */
    public List<IGroup> getGroupSet() {
        return groupSet;
    }

    /**
     * @param groupSet the groupSet to set
     */
    public void setGroupSet(List<IGroup> groupSet) {
        this.groupSet = groupSet;
    } 

}
