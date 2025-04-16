package com.maven.cookbook.service;

import com.maven.cookbook.model.FoodDTO;
import com.maven.cookbook.repository.FavouriteRepository;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FavouriteService { //F.Model->F.Service->F.Controller
    protected FavouriteRepository layer = new FavouriteRepository();
    
    public JSONObject getFavouriteByUser(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFavouriteByUser(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "UserHasNoFavourites";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();
            
            for(FoodDTO food: modelResult){
                JSONObject toAdd = new JSONObject();
                
                toAdd.put("id", food.getFood().getId());
                toAdd.put("name", food.getFood().getName());
                toAdd.put("image", food.getFood().getBase64Image());
                toAdd.put("description", food.getFood().getDescription());
                toAdd.put("prepTime", food.getFood().getPrepTime());
                toAdd.put("username", food.getUsername());
                toAdd.put("rating", food.getFood().getRating());
                toAdd.put("instructions", food.getFood().getInstructions());
                toAdd.put("difficultyName", food.getDifficultyName());
                toAdd.put("mealTypeName", food.getMealTypeType());
                toAdd.put("cuisineName", food.getCuisineType());
                toAdd.put("addedAt", food.getFood().getAddedAt());
            
                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject addFavourite(Integer userId, Integer foodId) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        boolean registerUser = layer.addFavourite(userId, foodId);
        if(registerUser == false){
            status = "fail";
            statusCode = 417;  
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject removeFavourite(Integer userId, Integer foodId) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        boolean registerUser = layer.removeFavourite(userId, foodId);
        if(registerUser == false){
            status = "fail";
            statusCode = 417;  
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
