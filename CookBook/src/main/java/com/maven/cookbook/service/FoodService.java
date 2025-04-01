package com.maven.cookbook.service;

import com.maven.cookbook.config.JWT;
import com.maven.cookbook.model.Food;
import com.maven.cookbook.model.FoodDTO;
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
        
        List<FoodDTO> modelResult = layer.getFoodByUser(id);
        System.out.println(modelResult);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
    
    public JSONObject getFoodByRating() {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFoodByRating();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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

    public JSONObject getFoodByRandom() {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        FoodDTO modelResult = layer.getFoodByRandom();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.getFood().getId() == null) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONObject result = new JSONObject();

            result.put("id", modelResult.getFood().getId());
            result.put("name", modelResult.getFood().getName());
            result.put("image", modelResult.getFood().getBase64Image());
            result.put("description", modelResult.getFood().getDescription());
            result.put("prepTime", modelResult.getFood().getPrepTime());
            result.put("username", modelResult.getUsername());
            result.put("rating", modelResult.getFood().getRating());
            result.put("instructions", modelResult.getFood().getInstructions());
            result.put("difficulty", modelResult.getDifficultyName());
            result.put("mealTypeName", modelResult.getMealTypeType());
            result.put("cuisineName", modelResult.getCuisineType());
            result.put("addedAt", modelResult.getFood().getAddedAt());

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
        
        List<FoodDTO> modelResult = layer.getFoodByDifficulty(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
    
    public JSONObject getFoodByDietary(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFoodByDietary(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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

    public JSONObject getFoodByCuisine(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFoodByCuisine(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
    
    public JSONObject getFoodByMealType(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFoodByMealType(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
    
    public JSONObject getAllFood() {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<FoodDTO> modelResult = layer.getAllFood();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
    
    public JSONObject deleteFoodById(Integer Id, String jwt){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        if(JWT.isAdmin(jwt)) {
            boolean deleteUserById = layer.deleteFoodById(Id);
            if(deleteUserById == false){
                status = "fail";
                statusCode = 417;
            }
        }else{
            status = "PermissionError";
            statusCode=403;
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject getFoodByIngredients(String ingredients) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFoodByIngredients(ingredients);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
    
    public JSONObject addFood(Food f, String ingredients) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        boolean addFood = layer.addFood(f, ingredients);
            if(addFood == false){
                status = "fail";
                statusCode = 417;
            }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject getFoodByAddedAt() {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<FoodDTO> modelResult = layer.getFoodByAddedAt();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noFoodFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(FoodDTO food: modelResult) {
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
}
