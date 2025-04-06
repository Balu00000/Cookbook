package com.maven.cookbook.service;

import com.maven.cookbook.model.MealType;
import com.maven.cookbook.repository.MealTypeRepository;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MealTypeServiceTest {
    private MealTypeRepository mockRepo;
    private MealTypeService service;
    
    public MealTypeServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
        mockRepo = mock(MealTypeRepository.class);
        
        service = new MealTypeService() {{
            layer = mockRepo;
        }};
    }
    
    @Test
    public void testGetAllMealTypeSuccess() {
        when(mockRepo.getAllMealType()).thenReturn(Arrays.asList(
            new MealType(4, "Breakfast"),
            new MealType(2, "Lunch")
        ));
        JSONObject result = service.getAllMealType();
        
        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(2, result.getJSONArray("result").length());
    }
    
    @Test
    public void testGetAllMealTypeNotFound() {
        when(mockRepo.getAllMealType()).thenReturn(Collections.emptyList());
        JSONObject result = service.getAllMealType();
        
        assertEquals("noMealTypeFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllMealTypeModelError() {
        when(mockRepo.getAllMealType()).thenReturn(null);
        JSONObject result = service.getAllMealType();
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllMealTypeReturnTypes(){
        when(mockRepo.getAllMealType()).thenReturn(Arrays.asList(
            new MealType(4, "Breakfast"),
            new MealType(2, "Lunch")
        ));
        JSONObject result = service.getAllMealType();
        JSONArray response = result.getJSONArray("result");
        
        JSONObject mealtype1 = response.getJSONObject(0);
        assertEquals(4, mealtype1.getInt("id"));
        assertEquals("Breakfast", mealtype1.getString("type"));

        JSONObject mealtype2 = response.getJSONObject(1);
        assertEquals(2, mealtype2.getInt("id"));
        assertEquals("Lunch", mealtype2.getString("type"));
    }
}
