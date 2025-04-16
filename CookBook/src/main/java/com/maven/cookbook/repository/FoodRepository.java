package com.maven.cookbook.repository;

import com.maven.cookbook.model.Food;
import com.maven.cookbook.model.FoodDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class FoodRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
        public Food findFoodById(Integer id) {
        /* id
        name
        image
        description
        preptime
        userId
        rating
        instructions
        difficultyId
        mealTypeId
        cuisineId
        addedAt
        isDeleted
        deletedAt
        */
        
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Food.class, id);
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
    }
        
    
    public List<FoodDTO> getFoodByUser(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByUser");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }

    public List<FoodDTO> getFoodByRating(){ //If things are null this will crash
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByRating");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public FoodDTO getFoodByRandom(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByRandom");
            spq.execute();
            
            List<Object[]> resultList = spq.getResultList();
            FoodDTO toReturn = null;
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
                
                toReturn = new FoodDTO(f, username, difficultyName, mealTypeType, cuisineType);
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

    public List<FoodDTO> getFoodByDifficulty(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByDifficulty");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public List<FoodDTO> getFoodByDietary(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByDietary");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public List<FoodDTO> getFoodByCuisine(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByCuisine");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public List<FoodDTO> getFoodByMealType(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByMealType");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public List<FoodDTO> getAllFood(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllFood");
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
                    formatter.parse(food[11].toString()),
                    Boolean.parseBoolean(food[12].toString()),
                    food[13] == null ? null : formatter.parse(food[13].toString())
                );
                String username = food[5].toString();
                String difficultyName = food[8].toString();
                String mealTypeType = food[9].toString();
                String cuisineType = food[10].toString();
                
                toReturn.add(new FoodDTO(f, username, difficultyName, mealTypeType, cuisineType));
            }
            return toReturn;
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public Boolean deleteFoodById(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteFoodById");
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
    
    public List<FoodDTO> getFoodByIngredients(String ingredients){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByIngredients");
            spq.registerStoredProcedureParameter("ingredientsIN", String.class, ParameterMode.IN);
            
            spq.setParameter("ingredientsIN", ingredients);
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public Boolean addFood(Food f, String ingredients){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addFood");
            
            spq.registerStoredProcedureParameter("nameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("imageIN", byte[].class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("descriptionIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("preptimeIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("userIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("instructionsIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("difficultyIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("mealTypeIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("cuisineIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("ingredientsIN", String.class, ParameterMode.IN);
            
            byte[] imageBytes = Base64.getDecoder().decode(f.getBase64Image());
            
            spq.setParameter("nameIN", f.getName());
            spq.setParameter("imageIN", imageBytes);
            spq.setParameter("descriptionIN", f.getDescription());
            spq.setParameter("preptimeIN", f.getPrepTime());
            spq.setParameter("userIdIN", f.getUserId());
            spq.setParameter("instructionsIN", f.getInstructions());
            spq.setParameter("difficultyIdIN", f.getDifficultyId());
            spq.setParameter("mealTypeIdIN", f.getMealTypeId());
            spq.setParameter("cuisineIdIN", f.getCuisineId());
            spq.setParameter("ingredientsIN", ingredients);
            
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
    
    public List<FoodDTO> getFoodByAddedAt(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodByAddedAt");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
    
    public List<FoodDTO> getFoodById(Integer id){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getFoodById");
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
        } catch (NumberFormatException | ParseException e) {
            System.err.println("Hiba: "+ e.getLocalizedMessage());
            return null;
        }finally{
            em.clear();
            em.close();
        }
    }
}
