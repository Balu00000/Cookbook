package com.maven.cookbook.service;

import com.maven.cookbook.model.Dietary;
import com.maven.cookbook.repository.DietaryRepository;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DietaryServiceTest {
    private DietaryRepository mockRepo;
    private DietaryService service;
    
    public DietaryServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
        mockRepo = mock(DietaryRepository.class);
        
        service = new DietaryService() {{
            layer = mockRepo;
        }};
    }
    
    @Test
    public void testGetAllDietarySuccess() {
        when(mockRepo.getAllDietary()).thenReturn(Arrays.asList(
            new Dietary(1, "Vegan"),
            new Dietary(2, "Keto")
        ));
        JSONObject result = service.getAllDietary();
        
        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(2, result.getJSONArray("result").length());
    }
    
    @Test
    public void testGetAllDietaryNotFound() {
        when(mockRepo.getAllDietary()).thenReturn(Collections.emptyList());
        JSONObject result = service.getAllDietary();
        
        assertEquals("noDietFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllDietaryModelError() {
        when(mockRepo.getAllDietary()).thenReturn(null);
        JSONObject result = service.getAllDietary();
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllDietaryReturnTypes(){
        when(mockRepo.getAllDietary()).thenReturn(Arrays.asList(
            new Dietary(4, "Vegan"),
            new Dietary(9, "Keto")
        ));
        JSONObject result = service.getAllDietary();
        JSONArray response = result.getJSONArray("result");
        
        JSONObject dietary1 = response.getJSONObject(0);
        assertEquals(4, dietary1.getInt("id"));
        assertEquals("Vegan", dietary1.getString("type"));

        JSONObject dietary2 = response.getJSONObject(1);
        assertEquals(9, dietary2.getInt("id"));
        assertEquals("Keto", dietary2.getString("type"));
    }
}
