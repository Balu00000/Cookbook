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
@Table(name = "difficulty")
@NamedQueries({
    @NamedQuery(name = "Difficulty.findAll", query = "SELECT d FROM Difficulty d"),
    @NamedQuery(name = "Difficulty.findById", query = "SELECT d FROM Difficulty d WHERE d.id = :id"),
    @NamedQuery(name = "Difficulty.findByName", query = "SELECT d FROM Difficulty d WHERE d.name = :name"),
    @NamedQuery(name = "Difficulty.findByEquipment", query = "SELECT d FROM Difficulty d WHERE d.equipment = :equipment")})
public class Difficulty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "equipment")
    private String equipment;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Difficulty() {  //D.Model->D.Service->D.Controller
    }

    public Difficulty(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            Difficulty d = em.find(Difficulty.class, id);
            
            this.id = d.getId();
            this.name = d.getName();
            this.equipment = d.getEquipment();
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Difficulty(Integer id, String name, String equipment){
        this.id = id;
        this.name = name;
        this.equipment = equipment;
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
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
        if (!(object instanceof Difficulty)) {
            return false;
        }
        Difficulty other = (Difficulty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.Difficulty[ id=" + id + "]";
    }
    
    public List<Difficulty> getAllDifficulty(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllDifficulty");
            spq.execute();
            
            List<Difficulty> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] diff : resultList){
                Difficulty d = new Difficulty(
                    Integer.valueOf(diff[0].toString()),
                    diff[1].toString(),
                    diff[2].toString()
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
