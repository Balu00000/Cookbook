/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chef.cookbook.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Kasza Csalad
 */
@Entity
@Table(name = "recipe")
@NamedQueries({
    @NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r"),
    @NamedQuery(name = "Recipe.findById", query = "SELECT r FROM Recipe r WHERE r.id = :id"),
    @NamedQuery(name = "Recipe.findByFoodId", query = "SELECT r FROM Recipe r WHERE r.foodId = :foodId"),
    @NamedQuery(name = "Recipe.findByIngredientId", query = "SELECT r FROM Recipe r WHERE r.ingredientId = :ingredientId"),
    @NamedQuery(name = "Recipe.findByAmount", query = "SELECT r FROM Recipe r WHERE r.amount = :amount"),
    @NamedQuery(name = "Recipe.findByMeasurement", query = "SELECT r FROM Recipe r WHERE r.measurement = :measurement")})
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "food_id")
    private Integer foodId;
    @Column(name = "ingredient_id")
    private Integer ingredientId;
    @Column(name = "amount")
    private Integer amount;
    @Size(max = 255)
    @Column(name = "measurement")
    private String measurement;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.chef_cookbook_war_1.0-SNAPSHOTPU");
    
    public Recipe() {
    }

    public Recipe(Integer id) {
        EntityManager em = emf.createEntityManager();
        
        try {
            Recipe r = em.find(Recipe.class, id);
            
            this.id = r.getId();
            this.foodId = r.getFoodId();
            this.ingredientId = r.getIngredientId();
            this.amount = r.getAmount();
            this.measurement = r.getMeasurement();
            
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Recipe(Integer id, Integer foodId, Integer ingredientId, Integer amount, String measurement) {
        this.id = id;
        this.foodId = foodId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.measurement = measurement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
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
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.chef.cookbook.model.Recipe[ id=" + id + " ]";
    }
    
}
