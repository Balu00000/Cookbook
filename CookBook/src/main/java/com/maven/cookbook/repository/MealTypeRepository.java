package com.maven.cookbook.repository;

import com.maven.cookbook.model.MealType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class MealTypeRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
    
    public MealType findMealTypeById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(MealType.class, id);
        } catch(Exception ex){
            System.err.println("Hiba: " + ex.getLocalizedMessage());
        } finally {
            em.clear();
            em.close();
        }
        return null;
    }
    
    public List<MealType> getAllMealType(){
        EntityManager em = emf.createEntityManager();
        
        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMealType");
            spq.execute();
            
            List<MealType> toReturn = new ArrayList();
            List<Object[]> resultList = spq.getResultList();
            
            for(Object[] type : resultList){
                MealType mt = new MealType(
                    Integer.valueOf(type[0].toString()),
                    type[1].toString()
                );
                toReturn.add(mt);
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
