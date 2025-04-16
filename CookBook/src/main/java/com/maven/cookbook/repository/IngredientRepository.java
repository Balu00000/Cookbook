package com.maven.cookbook.repository;

import com.maven.cookbook.model.Ingredient;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class IngredientRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");  
    
    public Ingredient findIngredientById(Integer id) {
        
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Ingredient.class, id);
            
        } catch (Exception ex) {
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
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
