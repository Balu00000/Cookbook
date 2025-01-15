package com.maven.cookbook.service;

import com.maven.cookbook.model.Favourite;
import com.maven.cookbook.model.Food;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FavouriteService { //F.Model->F.Service->F.Controller
    private Favourite layer = new Favourite();
    
    public JSONObject getFavouriteByUser(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<Food> modelResult = layer.getFavouriteByUser(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "UserHasNoFavourites";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();
            
            for(Food food: modelResult){
                JSONObject toAdd = new JSONObject();
                
                toAdd.put("id", food.getId());
                toAdd.put("name", food.getName());
                toAdd.put("image", food.getImage());
                toAdd.put("description", food.getDescription());
                toAdd.put("userId", food.getUserId());
                toAdd.put("rating", food.getRating());
                toAdd.put("instructions", food.getInstructions());
                toAdd.put("difficultyId", food.getDifficultyId());
                toAdd.put("mealTypeId", food.getMealTypeId());
                toAdd.put("cuisineId", food.getCuisineId());
                toAdd.put("addedAt", food.getAddedAt());
                toAdd.put("isDeleted", food.getIsDeleted());
                toAdd.put("deletedAt", food.getDeletedAt());
            
                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
