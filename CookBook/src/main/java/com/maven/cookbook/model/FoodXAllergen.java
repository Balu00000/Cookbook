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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Gama
 */
@Entity
@Table(name = "food_x_allergen")
@NamedQueries({
    @NamedQuery(name = "FoodXAllergen.findAll", query = "SELECT f FROM FoodXAllergen f"),
    @NamedQuery(name = "FoodXAllergen.findById", query = "SELECT f FROM FoodXAllergen f WHERE f.id = :id"),
    @NamedQuery(name = "FoodXAllergen.findByFoodId", query = "SELECT f FROM FoodXAllergen f WHERE f.foodId = :foodId"),
    @NamedQuery(name = "FoodXAllergen.findByAllergenId", query = "SELECT f FROM FoodXAllergen f WHERE f.allergenId = :allergenId")})
public class FoodXAllergen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "food_id")
    private int foodId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "allergen_id")
    private int allergenId;

    public FoodXAllergen() {
    }

    public FoodXAllergen(Integer id) {
        this.id = id;
    }

    public FoodXAllergen(Integer id, int foodId, int allergenId) {
        this.id = id;
        this.foodId = foodId;
        this.allergenId = allergenId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(int allergenId) {
        this.allergenId = allergenId;
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
        return "com.maven.cookbook.FoodXAllergen[ id=" + id + " ]";
    }
    
}
