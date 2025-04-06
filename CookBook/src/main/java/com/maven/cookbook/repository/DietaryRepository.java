package com.maven.cookbook.repository;

import com.maven.cookbook.model.Dietary;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class DietaryRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Dietary findDietaryById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Dietary.class, id);
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
    }
    
    public List<Dietary> getAllDietary(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllDietary");
            spq.execute();
            
            List<Dietary> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] diet : resultList){
                Dietary d = new Dietary(
                    Integer.valueOf(diet[0].toString()),
                    diet[1].toString()
                );
                toReturn.add(d);
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
