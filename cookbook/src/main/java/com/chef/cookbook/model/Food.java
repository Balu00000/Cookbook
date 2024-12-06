package com.chef.cookbook.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "food")
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findById", query = "SELECT f FROM Food f WHERE f.id = :id"),
    @NamedQuery(name = "Food.findByName", query = "SELECT f FROM Food f WHERE f.name = :name"),
    @NamedQuery(name = "Food.findByUserId", query = "SELECT f FROM Food f WHERE f.userId = :userId"),
    @NamedQuery(name = "Food.findByRating", query = "SELECT f FROM Food f WHERE f.rating = :rating"),
    @NamedQuery(name = "Food.findByDifficultyId", query = "SELECT f FROM Food f WHERE f.difficultyId = :difficultyId"),
    @NamedQuery(name = "Food.findByMealTypeId", query = "SELECT f FROM Food f WHERE f.mealTypeId = :mealTypeId"),
    @NamedQuery(name = "Food.findByCuisineId", query = "SELECT f FROM Food f WHERE f.cuisineId = :cuisineId"),
    @NamedQuery(name = "Food.findByAddedAt", query = "SELECT f FROM Food f WHERE f.addedAt = :addedAt"),
    @NamedQuery(name = "Food.findByIsDeleted", query = "SELECT f FROM Food f WHERE f.isDeleted = :isDeleted"),
    @NamedQuery(name = "Food.findByDeletedAt", query = "SELECT f FROM Food f WHERE f.deletedAt = :deletedAt")})
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "image")
    private String image;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "user_id")
    private Integer userId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Float rating;
    @Lob
    @Size(max = 65535)
    @Column(name = "instructions")
    private String instructions;
    @Column(name = "difficulty_id")
    private Integer difficultyId;
    @Column(name = "meal_type_id")
    private Integer mealTypeId;
    @Column(name = "cuisine_id")
    private Integer cuisineId;
    @Column(name = "added_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedAt;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.chef_cookbook_war_1.0-SNAPSHOTPU");

    public Food() {
    }

    public Food(Integer id) {
        EntityManager em = emf.createEntityManager();
        
        try {
            Food f = em.find(Food.class, id);

            this.id = f.getId();
            this.name = f.getName();
            this.image = f.getImage();
            this.description = f.getDescription();
            this.userId = f.getUserId();
            this.rating = f.getRating();
            this.difficultyId = f.getDifficultyId();
            this.mealTypeId = f.getMealTypeId();
            this.cuisineId = f.getCuisineId();
            this.addedAt = f.getAddedAt();
            this.isDeleted = f.getIsDeleted();
            this.deletedAt = f.getDeletedAt();
            
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Food(Integer id, String name, String image, String description, Integer userId, Float rating, Integer difficultyId, Integer mealTypeId, Integer cuisineId, Date addedAt,Boolean isDeleted, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.userId = userId;
        this.rating = rating;
        this.difficultyId = difficultyId;
        this.mealTypeId = mealTypeId;
        this.cuisineId = cuisineId;
        this.addedAt = addedAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(Integer difficultyId) {
        this.difficultyId = difficultyId;
    }

    public Integer getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Integer mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public Integer getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Integer cuisineId) {
        this.cuisineId = cuisineId;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
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
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.chef.cookbook.model.Food[ id=" + id + " ]";
    }

}
