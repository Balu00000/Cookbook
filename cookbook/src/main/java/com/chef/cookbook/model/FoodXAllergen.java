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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kasza Csalad
 */
@Entity
@Table(name = "food_x_allergen")
@NamedQueries({
    @NamedQuery(name = "FoodXAllergen.findAll", query = "SELECT f FROM FoodXAllergen f"),
    @NamedQuery(name = "FoodXAllergen.findById", query = "SELECT f FROM FoodXAllergen f WHERE f.id = :id"),
    @NamedQuery(name = "FoodXAllergen.findByAllergenId", query = "SELECT f FROM FoodXAllergen f WHERE f.allergenId = :allergenId"),
    @NamedQuery(name = "FoodXAllergen.findByFoodId", query = "SELECT f FROM FoodXAllergen f WHERE f.foodId = :foodId")})
public class FoodXAllergen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "allergen_id")
    private int allergenId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "food_id")
    private int foodId;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.chef_cookbook_war_1.0-SNAPSHOTPU");
    
    public FoodXAllergen() {
    }

    public FoodXAllergen(Integer id) {
        EntityManager em = emf.createEntityManager();
        
        try {
            FoodXAllergen fa = em.find(FoodXAllergen.class, id);
            
            this.id = fa.getId();
            this.allergenId = fa.getAllergenId();
            this.foodId = fa.getFoodId();
            
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }

    public FoodXAllergen(Integer id, int allergenId, int foodId) {
        this.id = id;
        this.allergenId = allergenId;
        this.foodId = foodId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(int allergenId) {
        this.allergenId = allergenId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
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
        if (!(object instanceof FoodXAllergen)) {
            return false;
        }
        FoodXAllergen other = (FoodXAllergen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.chef.cookbook.model.FoodXAllergen[ id=" + id + " ]";
    }
    
}
