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
@Table(name = "allergen")
@NamedQueries({
    @NamedQuery(name = "Allergen.findAll", query = "SELECT a FROM Allergen a"),
    @NamedQuery(name = "Allergen.findById", query = "SELECT a FROM Allergen a WHERE a.id = :id"),
    @NamedQuery(name = "Allergen.findByType", query = "SELECT a FROM Allergen a WHERE a.type = :type"),
    @NamedQuery(name = "Allergen.findByEffect", query = "SELECT a FROM Allergen a WHERE a.effect = :effect")})
public class Allergen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @Size(max = 255)
    @Column(name = "effect")
    private String effect;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.chef_cookbook_war_1.0-SNAPSHOTPU");
    
    public Allergen() {
    }

    public Allergen(Integer id) {
        EntityManager em = emf.createEntityManager();
        
        try {
            Allergen a = em.find(Allergen.class, id);
            
            this.id = a.getId();
            this.type = a.getType();
            this.effect = a.getEffect();
            
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Allergen(Integer id, String type, String effect) {
        this.id = id;
        this.type = type;
        this.effect = effect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
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
        if (!(object instanceof Allergen)) {
            return false;
        }
        Allergen other = (Allergen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.chef.cookbook.model.Allergen[ id=" + id + " ]";
    }
    
}
