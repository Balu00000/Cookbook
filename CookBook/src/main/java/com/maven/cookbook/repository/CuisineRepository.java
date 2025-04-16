package com.maven.cookbook.repository;

import com.maven.cookbook.model.Cuisine;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class CuisineRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Cuisine findCuisineById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Cuisine.class, id);
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
    }
    
    public List<Cuisine> getAllCuisine(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllCuisine");
            spq.execute();
            
            List<Cuisine> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] cuisine : resultList){
                Cuisine c = new Cuisine(
                    Integer.valueOf(cuisine[0].toString()),
                    cuisine[1].toString()
                );
                toReturn.add(c);
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
