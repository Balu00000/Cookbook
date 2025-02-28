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
import javax.validation.constraints.Size;

/**
 *
 * @author Gama
 */
@Entity
@Table(name = "ingredient")
@NamedQueries({
    @NamedQuery(name = "Ingredient.findAll", query = "SELECT i FROM Ingredient i"),
    @NamedQuery(name = "Ingredient.findById", query = "SELECT i FROM Ingredient i WHERE i.id = :id"),
    @NamedQuery(name = "Ingredient.findByName", query = "SELECT i FROM Ingredient i WHERE i.name = :name"),
    @NamedQuery(name = "Ingredient.findByProtein", query = "SELECT i FROM Ingredient i WHERE i.protein = :protein"),
    @NamedQuery(name = "Ingredient.findByCarb", query = "SELECT i FROM Ingredient i WHERE i.carb = :carb"),
    @NamedQuery(name = "Ingredient.findByFat", query = "SELECT i FROM Ingredient i WHERE i.fat = :fat"),
    @NamedQuery(name = "Ingredient.findByCholesterol", query = "SELECT i FROM Ingredient i WHERE i.cholesterol = :cholesterol"),
    @NamedQuery(name = "Ingredient.findByFiber", query = "SELECT i FROM Ingredient i WHERE i.fiber = :fiber")})
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "protein")
    private Float protein;
    @Column(name = "carb")
    private Float carb;
    @Column(name = "fat")
    private Float fat;
    @Column(name = "cholesterol")
    private Float cholesterol;
    @Column(name = "fiber")
    private Float fiber;

    public Ingredient() {
    }

    public Ingredient(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getCarb() {
        return carb;
    }

    public void setCarb(Float carb) {
        this.carb = carb;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Float getFiber() {
        return fiber;
    }

    public void setFiber(Float fiber) {
        this.fiber = fiber;
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
        if (!(object instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.Ingredient[ id=" + id + " ]";
    }
    
}
