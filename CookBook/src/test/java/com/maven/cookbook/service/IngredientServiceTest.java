package com.maven.cookbook.service;

import com.maven.cookbook.model.Ingredient;
import com.maven.cookbook.repository.IngredientRepository;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientServiceTest {
    private IngredientRepository mockRepo;
    private IngredientService service;
    
    public IngredientServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
        mockRepo = mock(IngredientRepository.class);
        
        service = new IngredientService() {{
            layer = mockRepo;
        }};
    }
    
    @Test
    public void testGetIngredientByFoodIdSuccess() {
        when(mockRepo.getIngredientByFoodId(Integer.SIZE)).thenReturn(Arrays.asList(
            new Ingredient(1, "Chicken", 2, "kgs"),
            new Ingredient(2, "Milk", 25, "mls")
        ));
        JSONObject result = service.getIngredientByFoodId(Integer.SIZE);
        
        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(2, result.getJSONArray("result").length());
    }
    
    @Test
    public void testGetIngredientByFoodIdNotFound() {
        when(mockRepo.getIngredientByFoodId(Integer.SIZE)).thenReturn(Collections.emptyList());
        JSONObject result = service.getIngredientByFoodId(Integer.SIZE);
        
        assertEquals("noIngredientFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetIngredientByFoodIdModelError() {
        when(mockRepo.getIngredientByFoodId(Integer.SIZE)).thenReturn(null);
        JSONObject result = service.getIngredientByFoodId(Integer.SIZE);
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetIngredientByFoodIdReturnTypes(){
        when(mockRepo.getIngredientByFoodId(Integer.SIZE)).thenReturn(Arrays.asList(
            new Ingredient(1, "Chicken", 2, "kgs"),
            new Ingredient(2, "Milk", 25, "mls")
        ));
        JSONObject result = service.getIngredientByFoodId(Integer.SIZE);
        JSONArray response = result.getJSONArray("result");
        
        JSONObject ingredient1 = response.getJSONObject(0);
        assertEquals(1, ingredient1.getInt("id"));
        assertEquals("Chicken", ingredient1.getString("name"));
        assertEquals(2, ingredient1.getInt("amount"));
        assertEquals("kgs", ingredient1.getString("measurment"));

        JSONObject ingredient2 = response.getJSONObject(1);
        assertEquals(2, ingredient2.getInt("id"));
        assertEquals("Milk", ingredient2.getString("name"));
        assertEquals(25, ingredient2.getInt("amount"));
        assertEquals("mls", ingredient2.getString("measurment"));
    }
    
}
