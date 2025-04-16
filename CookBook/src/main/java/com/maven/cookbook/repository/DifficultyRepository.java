package com.maven.cookbook.repository;

import com.maven.cookbook.model.Difficulty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class DifficultyRepository {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public Difficulty findDifficultyById(Integer id) {
        
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Difficulty.class, id);
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
    }
    
    public List<Difficulty> getAllDifficulty(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllDifficulty");
            spq.execute();
            
            List<Difficulty> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] diff : resultList){
                Difficulty d = new Difficulty(
                    Integer.valueOf(diff[0].toString()),
                    diff[1].toString(),
                    diff[2].toString()
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
