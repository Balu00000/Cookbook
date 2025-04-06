package com.maven.cookbook.service;

import com.maven.cookbook.model.Food;
import com.maven.cookbook.model.FoodDTO;
import com.maven.cookbook.repository.FoodRepository;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FoodServiceTest {
    private FoodRepository mockRepo;
    private FoodService service;
    
    public FoodServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
        mockRepo = mock(FoodRepository.class);
        service = new FoodService() {
        {
            layer = mockRepo;
        }};
        Mockito.reset(mockRepo);
    }
    
    @Test
    public void testGetFoodByIdSuccess() {
        Food mockfood = new Food();
        
        when(mockRepo.getFoodById(Integer.SIZE)).thenReturn(Arrays.asList(
            new FoodDTO(mockfood,"John Apple","Easy","Breakfast","Keto"),
            new FoodDTO(mockfood,"Leo Neo","Hard","Dinner","Carnivore")
        ));
        JSONObject result = service.getFoodById(Integer.SIZE);
        
        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(2, result.getJSONArray("result").length());
    }
    
    @Test
    public void testGetFoodByIdModelException() {
        when(mockRepo.getFoodById(Integer.SIZE)).thenReturn(null);
        JSONObject result = service.getFoodById(Integer.SIZE);
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetFoodByIdNotFound() {
        when(mockRepo.getFoodById(Integer.SIZE)).thenReturn(Collections.emptyList());
        JSONObject result = service.getFoodById(Integer.SIZE);
        
        assertEquals("noFoodFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetFoodByRandomSuccess() {
        Food mockfood = new Food(1);
        
        when(mockRepo.getFoodByRandom()).thenReturn(new FoodDTO(mockfood,"Leo Neo","Hard","Dinner","Carnivore"));
        JSONObject result = service.getFoodByRandom();
        
        assertEquals("success",result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(6, result.getJSONObject("result").length());
    }
    
    @Test
    public void testGetFoodByRandomModelException() {
        when(mockRepo.getFoodByRandom()).thenReturn(null);
        JSONObject result = service.getFoodByRandom();
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetFoodByRandomNotFound() {
        when(mockRepo.getFoodByRandom()).thenReturn(new FoodDTO(new Food()));
        JSONObject result = service.getFoodByRandom();
        System.out.println(result.toString());
        
        assertEquals("noFoodFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testAddFood() {
        Food mockFood = new Food();
        mockFood.setName("Pizza");
        mockFood.setBase64Image("someBase64Image");
        mockFood.setDescription("Delicious pizza");
        mockFood.setPrepTime("30");
        mockFood.setUserId(1);
        mockFood.setInstructions("Bake at 200C");
        mockFood.setDifficultyId(1);
        mockFood.setMealTypeId(1);
        mockFood.setCuisineId(1);
        
        String ingredients = "Tomato, Cheese, Dough";
        
        when(mockRepo.addFood(mockFood, ingredients)).thenReturn(true);

        JSONObject result = service.addFood(mockFood, ingredients);

        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
    }
    
    
}
