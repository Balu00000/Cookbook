/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maven.cookbook.model;

import static com.maven.cookbook.model.User.emf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cuisine")
@NamedQueries({
    @NamedQuery(name = "Cuisine.findAll", query = "SELECT c FROM Cuisine c"),
    @NamedQuery(name = "Cuisine.findById", query = "SELECT c FROM Cuisine c WHERE c.id = :id"),
    @NamedQuery(name = "Cuisine.findByType", query = "SELECT c FROM Cuisine c WHERE c.type = :type")})
public class Cuisine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "type")
    private String type;

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Cuisine() { //C.Model->C.Service->C.Controller
    }

    public Cuisine(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            Cuisine c = em.find(Cuisine.class, id);
            
            this.id = c.getId();
            this.type = c.getType();
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Cuisine(Integer id, String type){
        this.id = id;
        this.type = type;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuisine)) {
            return false;
        }
        Cuisine other = (Cuisine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.Cuisine[ id=" + id + " ]";
    }
    
    public List<Cuisine> getAllCuisine(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllCuisine");
            spq.execute();
            
            List<Cuisine> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] cuisine : resultList){
                Cuisine c = new Cuisine(
                    Integer.valueOf(cuisine[0].toString()),
                    cuisine[1].toString()
                );
                toReturn.add(c);
            }
            return toReturn;
        } catch (Exception e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
}
