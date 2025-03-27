package com.maven.cookbook.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    public UserTest() {
    }
    static EntityManagerFactory emf;
    public EntityManager em;
    public User test;
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(
            "com.maven_CookBook_war_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
        test = new User();
    }
    //@Test
    public void testEntityManagerInitialization() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.maven_CookBook_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        assertNotNull(em);
        em.close();
        emf.close();
    }
    
    
    //@Test
    public void testLogin() throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String email = "kasza.david@simonyiszki.org";
        String password = "1234";
        
        User reponse = new User(
            4,
            "Kasza David",
            "YmlsbHkucG5n",
            "kasza.david@simonyiszki.org",
            true,
            formatter.parse("2024-09-20 13:29:23"),
            false,
            (Date) null
        );
        assertEquals("Test works!", reponse, test.login(email, password) );
    }
}
