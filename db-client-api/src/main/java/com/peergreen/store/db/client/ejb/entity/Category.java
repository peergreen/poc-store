package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.peergreen.store.db.client.ejb.key.primary.CategoryId;

/**
 * Entity Bean representing the category of a petal  
 */
@NamedQueries({
    @NamedQuery(
            name = "Category.findAll",
            query = "select cat from Category cat"),
            @NamedQuery(
                    name ="CategoryByName",
                    query ="select cat from Category cat where cat.categoryName = :name")
})
@Entity
@IdClass(CategoryId.class)
public class Category{

    @Id
    @Column(name ="name")
    private String categoryName;

    @Id
    @SequenceGenerator(name="idCategorySeq", initialValue=1, allocationSize=50)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idCategorySeq")
    private int categoryId;

    @OneToMany(mappedBy="category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Petal> petals;


    public Category() {

    }

    public Category(String categoryName) {
        super();
        this.categoryName = categoryName;
        Set<Petal> petals = new HashSet<Petal>(); 
        this.setPetals(petals);
    }

    /**
     * Method to get Id of a category
     * @return the category's id
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Method to retrieve the name of the category instance
     * 
     * @return the category's name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Method to give the name of the category instance 
     * 
     * @param categoryname the category's name to set
     */
    public void setCategoryName(String categoryname) {
        this.categoryName = categoryname;
    }

    /**
     * Method for retrieve all the petals that belongs to this category
     * 
     * @return Set containing petals that belongs to this category
     */
    public Set<Petal> getPetals() {
        return petals;
    }

    /**
     * Method to add petals to the Set of petals that belongs to this category
     * 
     * @param petals Set containing the petals to set
     *//**
     * @param petals the petals to set
     */
    public void setPetals(Set<Petal> petals) {
        this.petals = petals;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        String s = categoryId + "-" + categoryName;
        return s;
    }

}
