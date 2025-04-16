package com.maven.cookbook.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "food")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findById", query
            = "SELECT f FROM Food f WHERE f.id = :id"),
    @NamedQuery(name = "Food.findByName", query
            = "SELECT f FROM Food f WHERE f.name = :name"),
    @NamedQuery(name = "Food.findByPrepTime", query
            = "SELECT f FROM Food f WHERE f.prepTime = :prepTime"),
    @NamedQuery(name = "Food.findByUserId", query
            = "SELECT f FROM Food f WHERE f.userId = :userId"),
    @NamedQuery(name = "Food.findByRating", query
            = "SELECT f FROM Food f WHERE f.rating = :rating"),
    @NamedQuery(name = "Food.findByDifficultyId", query
            = "SELECT f FROM Food f WHERE f.difficultyId = :difficultyId"),
    @NamedQuery(name = "Food.findByMealTypeId", query
            = "SELECT f FROM Food f WHERE f.mealTypeId = :mealTypeId"),
    @NamedQuery(name = "Food.findByCuisineId", query
            = "SELECT f FROM Food f WHERE f.cuisineId = :cuisineId"),
    @NamedQuery(name = "Food.findByAddedAt", query
            = "SELECT f FROM Food f WHERE f.addedAt = :addedAt"),
    @NamedQuery(name = "Food.findByIsDeleted", query
            = "SELECT f FROM Food f WHERE f.isDeleted = :isDeleted"),
    @NamedQuery(name = "Food.findByDeletedAt", query
            = "SELECT f FROM Food f WHERE f.deletedAt = :deletedAt")})
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
    @Size(max = 255)
    @Column(name = "prep_time")
    private String prepTime;
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;
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
    
    private String base64Image;
    
    public Food() { //F.Model->F.Service->F.Controller
    }
    
    public Food(Integer id) {
        this.id = id;
    }

    public Food(Integer id, String name, String image, String description, String preptime, Integer rating, String instructions, Date addedAt) {
        this.id = id;
        this.name = name;
        this.base64Image = image;
        this.description = description;
        this.prepTime = preptime;
        this.rating = rating;
        this.instructions = instructions;
        this.addedAt = addedAt;
    }
    
    public Food(Integer id, String name, byte[] image, String description, String preptime, Integer rating, String instructions, Date addedAt) {
        this.id = id;
        this.name = name;
        this.base64Image = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.description = description;
        this.prepTime = preptime;
        this.rating = rating;
        this.instructions = instructions;
        this.addedAt = addedAt;
    }
    
    public Food(Integer id, String name, byte[] image, String description, String preptime, Integer rating, String instructions, Date addedAt, boolean isDeleted, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.base64Image = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.description = description;
        this.prepTime = preptime;
        this.rating = rating;
        this.instructions = instructions;
        this.addedAt = addedAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
    
    public Food(String name, byte[] image, String description, String preptime,Integer userid, String instructions, Integer difficultyid, Integer mealtypeid, Integer cuisineid) {
        this.name = name;
        this.base64Image = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.description = description;
        this.prepTime = preptime;
        this.userId = userid;
        this.instructions = instructions;
        this.difficultyId = difficultyid;
        this.mealTypeId = mealtypeid;   
        this.cuisineId = cuisineid; 
    }
    
    public Food(String name, String image, String description, String preptime,Integer userid, String instructions, Integer difficultyid, Integer mealtypeid, Integer cuisineid) {
        this.name = name;
        this.base64Image = image;
        this.description = description;
        this.prepTime = preptime;
        this.userId = userid;
        this.instructions = instructions;
        this.difficultyId = difficultyid;
        this.mealTypeId = mealtypeid;   
        this.cuisineId = cuisineid; 
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

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
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
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.model.Food[ id=" + id + " ]";
    }
}
