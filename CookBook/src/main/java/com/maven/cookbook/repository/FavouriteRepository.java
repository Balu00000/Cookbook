package com.maven.cookbook.repository;

import com.maven.cookbook.model.Favourite;
import com.maven.cookbook.model.Food;
import com.maven.cookbook.model.FoodDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class FavouriteRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Favourite findFavouriteById(Integer id) {
        
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Favourite.class, id);
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
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
            
            for(Object[] food : resultList){
                Food f = new Food(
                    Integer.valueOf(food[0].toString()),
                    food[1].toString(),
                    food[2] != null ? (byte[]) food[2] : null,
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
    
    public Boolean addFavourite(Integer userId, Integer foodId){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addFavourite");
            
            spq.registerStoredProcedureParameter("userIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("foodIdIN", Integer.class, ParameterMode.IN);
            
            
            spq.setParameter("userIdIN", userId);
            spq.setParameter("foodIdIN", foodId);
            
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
    
    public Boolean removeFavourite(Integer userId, Integer foodId){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("removeFavourite");
            
            spq.registerStoredProcedureParameter("userIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("foodIdIN", Integer.class, ParameterMode.IN);
            
            
            spq.setParameter("userIdIN", userId);
            spq.setParameter("foodIdIN", foodId);
            
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
}
