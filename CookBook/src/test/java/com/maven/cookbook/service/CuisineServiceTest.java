package com.maven.cookbook.service;

import com.maven.cookbook.model.Cuisine;
import com.maven.cookbook.repository.CuisineRepository;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CuisineServiceTest {
    private CuisineRepository mockRepo;
    private CuisineService service;
    
    public CuisineServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
        mockRepo = mock(CuisineRepository.class);
        
        service = new CuisineService() {{
            layer = mockRepo;
        }};
    }

    @Test
    public void testGetAllCuisineSuccess() {
        when(mockRepo.getAllCuisine()).thenReturn(Arrays.asList(
            new Cuisine(1, "Italian"),
            new Cuisine(2, "Mexican")
        ));
        JSONObject result = service.getAllCuisine();
        
        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(2, result.getJSONArray("result").length());
    }
    
    @Test
    public void testGetAllCuisineNotFound() {
        when(mockRepo.getAllCuisine()).thenReturn(Collections.emptyList());
        JSONObject result = service.getAllCuisine();
        
        assertEquals("noCuisineFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllCuisineModelError() {
        when(mockRepo.getAllCuisine()).thenReturn(null);
        JSONObject result = service.getAllCuisine();
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllCuisineReturnTypes(){
        when(mockRepo.getAllCuisine()).thenReturn(Arrays.asList(
            new Cuisine(1, "Italian"),
            new Cuisine(2, "Mexican")
        ));
        JSONObject result = service.getAllCuisine();
        JSONArray response = result.getJSONArray("result");
        
        JSONObject cuisine1 = response.getJSONObject(0);
        assertEquals(1, cuisine1.getInt("id"));
        assertEquals("Italian", cuisine1.getString("name"));

        JSONObject cuisine2 = response.getJSONObject(1);
        assertEquals(2, cuisine2.getInt("id"));
        assertEquals("Mexican", cuisine2.getString("name"));
    }
}
