package com.maven.cookbook.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query
            = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query
            = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query
            = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query
            = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByIsAdmin", query
            = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin"),
    @NamedQuery(name = "User.findByCreatedAt", query
            = "SELECT u FROM User u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "User.findByIsDeleted", query
            = "SELECT u FROM User u WHERE u.isDeleted = :isDeleted"),
    @NamedQuery(name = "User.findByDeletedAt", query
            = "SELECT u FROM User u WHERE u.deletedAt = :deletedAt")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "image")
    private byte[] image;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    private String base64Image;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "com.maven_CookBook_war_1.0-SNAPSHOTPU");   
    
    public User() { //U.Model->U.Service->U.Controller
    }

    public User(Integer id) {
        
        EntityManager em = emf.createEntityManager();

        try {
            User u = em.find(User.class, id);

            this.id = u.getId();
            this.username = u.getUsername();
            this.image = u.getImage();
            this.email = u.getEmail();
            this.password = u.getPassword();
            this.isAdmin = u.getIsAdmin();
            this.createdAt = u.getCreatedAt();
            this.isDeleted = u.getIsDeleted();
            this.deletedAt = u.getDeletedAt();
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public User(Integer id, String username, byte[] image, String email, String password, boolean isAdmin, Date createdAt, boolean isDeleted, Date deletedAt) {
        this.id = id;
        this.username = username;
        this.base64Image = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
    
    public User(Integer id, String username, byte[] image, String email, boolean isAdmin, Date createdAt, boolean isDeleted, Date deletedAt) {
        this.id = id;
        this.username = username;
        this.base64Image = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.email = email;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
    
    public User(Integer id, String username, String image, String email, boolean isAdmin, Date createdAt, boolean isDeleted, Date deletedAt) {
        this.id = id;
        this.username = username;
        this.base64Image = image;
        this.email = email;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
    
    public User(String username, String image, String email, String password) {
        this.username = username;
        this.base64Image = image;
        this.email = email;
        this.password = password;
    }
    
    public User(String username, String image, Date createdAt){
        this.username = username;
        this.base64Image = image;
        this.createdAt = createdAt;
    }
    
    public User(String username, byte[] image, Date createdAt){
        this.username = username;
        this.base64Image = image != null ? Base64.getEncoder().encodeToString(image) : null;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maven.cookbook.model.User[ id=" + id + " ]";
    }
    
    public static byte[] convertImageToBytes(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        fis.close();
        return bos.toByteArray();
    }

    public User login(String email, String password) {
        EntityManager em = emf.createEntityManager();

        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("login");
            
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("passwordIN", String.class, ParameterMode.IN);
            
            spq.setParameter("emailIN", email);
            spq.setParameter("passwordIN", password);
            
            spq.execute();
            
            List<Object[]> resultList = spq.getResultList();
            User toReturn = new User();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Object[] o : resultList){
                User u = new User(
                    Integer.valueOf(o[0].toString()),
                    o[1].toString(),
                    o[2] != null ? (byte[]) o[2] : null,
                    o[3].toString(),
                    Boolean.parseBoolean(o[4].toString()),
                    formatter.parse(o[5].toString()),
                    Boolean.parseBoolean(o[6].toString()),
                    o[7] == null ? null : formatter.parse(o[7].toString())
                );
                toReturn = u;
            }
            return toReturn;
            
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Boolean registerUser(User u){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("registerUser");
            
            spq.registerStoredProcedureParameter("usernameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("imageIN", byte[].class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("passwordIN", String.class, ParameterMode.IN);
            
            byte[] imageBytes = Base64.getDecoder().decode(u.getBase64Image());
            
            spq.setParameter("usernameIN", u.getUsername());
            spq.setParameter("imageIN", imageBytes);
            spq.setParameter("emailIN", u.getEmail());
            spq.setParameter("passwordIN", u.getPassword());
            
            spq.execute();
            
            return true;
            
        }catch(Exception e){
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public Boolean registerAdmin(User u){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("registerAdmin");
            
            spq.registerStoredProcedureParameter("usernameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("imageIN", byte[].class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("passwordIN", String.class, ParameterMode.IN);
            
            byte[] imageBytes = Base64.getDecoder().decode(u.getBase64Image());
            
            spq.setParameter("usernameIN", u.getUsername());
            spq.setParameter("imageIN", imageBytes);
            spq.setParameter("emailIN", u.getEmail());
            spq.setParameter("passwordIN", u.getPassword());
            
            spq.execute();
            
            return true;
            
        }catch(Exception e){
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public static Boolean isUserExists(String email){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("isUserExists");
            
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("resultOUT", Boolean.class, ParameterMode.OUT);
            
            spq.setParameter("emailIN", email);
            
            spq.execute();
            
            Boolean result = Boolean.valueOf(spq.getOutputParameterValue("resultOUT").toString());
            return result;
            
        }catch(Exception e){
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public List<User> getAllUser(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllUser");
            spq.execute();
            
            List<User> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Object[] record : resultList){
                User u = new User(
                    Integer.valueOf(record[0].toString()),
                    record[1].toString(),
                    record[2] != null ? (byte[]) record[2] : null, //image
                    record[3].toString(),
                    record[4].toString(),
                    Boolean.parseBoolean(record[5].toString()),
                    formatter.parse(record[6].toString()),
                    Boolean.parseBoolean(record[7].toString()),
                    record[8] == null ? null : formatter.parse(record[8].toString())
                );
                toReturn.add(u);
            }
            System.out.println(toReturn);
            return toReturn;
            
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public UserDTO getUserProfileInformation(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getUserProfileInformation");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", id);
            spq.execute();
            
            List<Object[]> resultList = spq.getResultList();
            UserDTO toReturn = null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Object[] user : resultList){
                User u = new User(
                    user[0].toString(),
                    user[1] != null ? (byte[]) user[1] : null,
                    formatter.parse(user[2].toString())
                );
                
                Integer postedFood = Integer.valueOf(user[3].toString());
                Integer favouritedFood = Integer.valueOf(user[4].toString());
                
                toReturn = new UserDTO(u, postedFood, favouritedFood);
            }
            return toReturn;
            
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Boolean deleteUserById(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteUserById");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", id);
            
            spq.execute();
            
            return true;
        } catch (Exception e) {
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Boolean updateUsername(Integer id, String username){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("updateUsername");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("usernameIN", String.class, ParameterMode.IN);
            
            spq.setParameter("idIN", id);
            spq.setParameter("usernameIN", username);
            
            spq.execute();
            
            return true;
        } catch (Exception e) {
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        } finally {
            em.clear();
            em.close();
        }
    }
    
    public Boolean updateImage(Integer id, String image) throws IOException{
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("updateImage");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("imageIN", byte[].class, ParameterMode.IN);
            
            byte[] imageBytes = convertImageToBytes(image);
            
            spq.setParameter("idIN", id);
            spq.setParameter("imageIN", imageBytes);
            
            spq.execute();
            
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Hiba: " + e.getLocalizedMessage());
            return null;
        } finally {
            em.clear();
            em.close();
        }
    }
}
