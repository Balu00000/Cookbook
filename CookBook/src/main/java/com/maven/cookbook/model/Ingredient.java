package com.maven.cookbook.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
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
    
    private Integer amount;
    private String measurment;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");  
    
    public Ingredient() {
    }
    
    public Ingredient(Integer id) {
        
        EntityManager em = emf.createEntityManager();

        try {
            Ingredient i = em.find(Ingredient.class, id);

            this.id = i.getId();
            this.name = i.getName();
            this.protein = i.getProtein();
            this.carb = i.getCarb();
            this.fat = i.getFat();
            this.cholesterol = i.getCholesterol();
            this.fiber = i.getFiber();
            
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Ingredient(Integer id, String name, Integer amount, String measurment){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.measurment = measurment;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMeasurment() {
        return measurment;
    }

    public void setMeasurment(String measurment) {
        this.measurment = measurment;
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
    
    public List<Ingredient> getIngredientByFoodId(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getIngredientByFoodId");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", id);
            
            spq.execute();
            
            List<Ingredient> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            for(Object[] ingredient : resultList){
                Ingredient i = new Ingredient(
                    Integer.valueOf(ingredient[0].toString()),
                    ingredient[1].toString(),
                    Integer.valueOf(ingredient[2].toString()),
                    ingredient[3].toString()
                );
                toReturn.add(i);
            }
            System.out.println(toReturn);
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
