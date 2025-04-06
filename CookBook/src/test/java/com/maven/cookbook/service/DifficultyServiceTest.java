package com.maven.cookbook.service;

import com.maven.cookbook.model.Difficulty;
import com.maven.cookbook.repository.DifficultyRepository;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DifficultyServiceTest {
    private DifficultyRepository mockRepo;
    private DifficultyService service;
    
    public DifficultyServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
        mockRepo = mock(DifficultyRepository.class);
        
        service = new DifficultyService() {{
            layer = mockRepo;
        }};
    }

    @Test
    public void testGetAllDifficultySuccess() {
        when(mockRepo.getAllDifficulty()).thenReturn(Arrays.asList(
            new Difficulty(1, "Easy", "A few"),
            new Difficulty(3, "Hard", "A lot")
        ));
        JSONObject result = service.getAllDifficulty();
        
        assertEquals("success", result.getString("status"));
        assertEquals(200, result.getInt("statusCode"));
        assertEquals(2, result.getJSONArray("result").length());
    }
    
    @Test
    public void testGetAllDifficultyNotFound() {
        when(mockRepo.getAllDifficulty()).thenReturn(Collections.emptyList());
        JSONObject result = service.getAllDifficulty();
        
        assertEquals("noDifficultyFound", result.getString("status"));
        assertEquals(404, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllDifficultyModelError() {
        when(mockRepo.getAllDifficulty()).thenReturn(null);
        JSONObject result = service.getAllDifficulty();
        
        assertEquals("modelException", result.getString("status"));
        assertEquals(500, result.getInt("statusCode"));
    }
    
    @Test
    public void testGetAllDifficultyReturnTypes(){
        when(mockRepo.getAllDifficulty()).thenReturn(Arrays.asList(
            new Difficulty(2, "Medium", "A modest amount"),
            new Difficulty(3, "Hard", "A lot")
        ));
        JSONObject result = service.getAllDifficulty();
        JSONArray response = result.getJSONArray("result");
        
        JSONObject difficulty1 = response.getJSONObject(0);
        assertEquals(2, difficulty1.getInt("id"));
        assertEquals("Medium", difficulty1.getString("name"));
        assertEquals("A modest amount", difficulty1.getString("equipment"));
        
        JSONObject difficulty2 = response.getJSONObject(1);
        assertEquals(3, difficulty2.getInt("id"));
        assertEquals("Hard", difficulty2.getString("name"));
        assertEquals("A lot", difficulty2.getString("equipment"));
    }
}
