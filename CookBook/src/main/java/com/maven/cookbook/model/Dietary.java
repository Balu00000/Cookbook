package com.maven.cookbook.model;

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
@Table(name = "dietary")
@NamedQueries({
    @NamedQuery(name = "Dietary.findAll", query = "SELECT d FROM Dietary d"),
    @NamedQuery(name = "Dietary.findById", query = "SELECT d FROM Dietary d WHERE d.id = :id"),
    @NamedQuery(name = "Dietary.findByType", query = "SELECT d FROM Dietary d WHERE d.type = :type")})
public class Dietary implements Serializable {

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
    
    public Dietary() { //D.Model->D.Service->D.Controller
    }

    public Dietary(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            Dietary d = em.find(Dietary.class, id);
            
            this.id = d.getId();
            this.type = d.getType();
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Dietary(Integer id, String type){
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
        if (!(object instanceof Dietary)) {
            return false;
        }
        Dietary other = (Dietary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.Dietary[ id=" + id + " ]";
    }
    
    public List<Dietary> getAllDietary(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllDietary");
            spq.execute();
            
            List<Dietary> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] diet : resultList){
                Dietary d = new Dietary(
                    Integer.valueOf(diet[0].toString()),
                    diet[1].toString()
                );
                toReturn.add(d);
            }
            return toReturn;
        } catch (NumberFormatException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
}
