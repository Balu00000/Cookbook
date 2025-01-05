package com.maven.cookbook.service;

import com.maven.cookbook.model.Food;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FoodService { //F.Model->F.Service->F.Controller
    private Food layer = new Food();
    
    /* id
        name
        image
        description
        userId
        rating
        instructions
        difficultyId
        mealTypeId
        cuisineId
        addedAt
        isDeleted
        deletedAt
        */
    
    public JSONObject getFoodByUser(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Food> modelResult = layer.getFoodByUser(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(Food food: modelResult) {
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
    
    public JSONObject getFoodByRating() {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Food> modelResult = layer.getFoodByRating();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(Food food: modelResult) {
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

    public JSONObject getFoodByRandom() {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        Food modelResult = layer.getFoodByRandom();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.getId() == null) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONObject result = new JSONObject();

            result.put("id", modelResult.getId());
            result.put("name", modelResult.getName());
            result.put("image", modelResult.getImage());
            result.put("description", modelResult.getDescription());
            result.put("userId", modelResult.getUserId());
            result.put("rating", modelResult.getRating());
            result.put("instructions", modelResult.getInstructions());
            result.put("difficultyId", modelResult.getDifficultyId());
            result.put("mealTypeId", modelResult.getMealTypeId());
            result.put("cuisineId", modelResult.getCuisineId());
            result.put("addedAt", modelResult.getAddedAt());
            result.put("isDeleted", modelResult.getIsDeleted());
            result.put("deletedAt", modelResult.getDeletedAt());

            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }

    public JSONObject getFoodByDifficulty(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Food> modelResult = layer.getFoodByDifficulty(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(Food food: modelResult) {
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
    
    public JSONObject getFoodByDietary(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Food> modelResult = layer.getFoodByDietary(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(Food food: modelResult) {
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

    public JSONObject getFoodByCuisine(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Food> modelResult = layer.getFoodByCuisine(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(Food food: modelResult) {
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
    
    public JSONObject getFoodByMealType(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Food> modelResult = layer.getFoodByMealType(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(Food food: modelResult) {
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
