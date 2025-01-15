/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.cookbook.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Gama
 */
@Entity
@Table(name = "food_x_dietary")
@NamedQueries({
    @NamedQuery(name = "FoodXDietary.findAll", query = "SELECT f FROM FoodXDietary f"),
    @NamedQuery(name = "FoodXDietary.findById", query = "SELECT f FROM FoodXDietary f WHERE f.id = :id"),
    @NamedQuery(name = "FoodXDietary.findByDietaryId", query = "SELECT f FROM FoodXDietary f WHERE f.dietaryId = :dietaryId"),
    @NamedQuery(name = "FoodXDietary.findByFoodId", query = "SELECT f FROM FoodXDietary f WHERE f.foodId = :foodId")})
public class FoodXDietary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dietary_id")
    private Integer dietaryId;
    @Column(name = "food_id")
    private Integer foodId;

    public FoodXDietary() {
    }

    public FoodXDietary(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDietaryId() {
        return dietaryId;
    }

    public void setDietaryId(Integer dietaryId) {
        this.dietaryId = dietaryId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoodXDietary)) {
            return false;
        }
        FoodXDietary other = (FoodXDietary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.FoodXDietary[ id=" + id + " ]";
    }
    
}
