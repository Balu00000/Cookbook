package com.maven.cookbook.model;

import java.io.Serializable;
import java.text.ParseException;
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

@Entity
@Table(name = "favourite")
@NamedQueries({
    @NamedQuery(name = "Favourite.findAll", query = "SELECT f FROM Favourite f"),
    @NamedQuery(name = "Favourite.findById", query = "SELECT f FROM Favourite f WHERE f.id = :id"),
    @NamedQuery(name = "Favourite.findByUserId", query = "SELECT f FROM Favourite f WHERE f.userId = :userId"),
    @NamedQuery(name = "Favourite.findByFoodId", query = "SELECT f FROM Favourite f WHERE f.foodId = :foodId")})
public class Favourite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "food_id")
    private Integer foodId;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Favourite() { //F.Model->F.Service->F.Controller
    }

    public Favourite(Integer id) {
        /* Id
        userId
        foodId
        */
        
        EntityManager em = emf.createEntityManager();

        try {
            Favourite f = em.find(Favourite.class, id);

            this.id = f.getId();
            this.userId = f.getUserId();
            this.foodId = f.getFoodId();
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Favourite(Integer id, Integer userId, Integer foodId){
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
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
        if (!(object instanceof Favourite)) {
            return false;
        }
        Favourite other = (Favourite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.Favourite[ id=" + id + " ]";
    }
    
    public List<FoodDTO> getFavouriteByUser(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFavouriteByUser");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", id);
            
            spq.execute();
            
            List<FoodDTO> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for(Object[] food : resultList){ //This handles food table results because that's the only thing returned by sql
                Food f = new Food(
                    Integer.valueOf(food[0].toString()),
                    food[1].toString(),
                    food[2].toString(),
                    food[3].toString(),
                    food[4].toString(),   
                    Integer.valueOf(food[6].toString()), 
                    food[7].toString(),
                    formatter.parse(food[11].toString())
                );
                String username = food[5].toString();
                String difficultyName = food[8].toString();
                String mealTypeType = food[9].toString();
                String cuisineType = food[10].toString();
                
                toReturn.add(new FoodDTO(f, username, difficultyName, mealTypeType, cuisineType));
            }
            return toReturn;
        }catch(NumberFormatException | ParseException e){
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
}
