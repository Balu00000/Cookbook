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
@Table(name = "meal_type")
@NamedQueries({
    @NamedQuery(name = "MealType.findAll", query = "SELECT m FROM MealType m"),
    @NamedQuery(name = "MealType.findById", query = "SELECT m FROM MealType m WHERE m.id = :id"),
    @NamedQuery(name = "MealType.findByType", query = "SELECT m FROM MealType m WHERE m.type = :type")})
public class MealType implements Serializable {

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
    
    public MealType() { //MT.Model->MT.Service->MT.Controller
    }

    public MealType(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            MealType mt = em.find(MealType.class, id);
            
            this.id = mt.getId();
            this.type = mt.getType();
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    public MealType(Integer id, String type){
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
        if (!(object instanceof MealType)) {
            return false;
        }
        MealType other = (MealType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.MealType[ id=" + id + " ]";
    }
    
    public List<MealType> getAllMealType(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMealType");
            spq.execute();
            
            List<MealType> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] type : resultList){
                MealType mt = new MealType(
                    Integer.valueOf(type[0].toString()),
                    type[1].toString()
                );
                toReturn.add(mt);
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
