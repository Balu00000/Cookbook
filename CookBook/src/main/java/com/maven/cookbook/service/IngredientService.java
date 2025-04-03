package com.maven.cookbook.service;

import com.maven.cookbook.model.Ingredient;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class IngredientService {
    private final Ingredient layer = new Ingredient();
    
    public JSONObject getIngredientByFoodId(Integer id) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        List<Ingredient> modelResult = layer.getIngredientByFoodId(id);

        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noIngredientFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();

            for(Ingredient actualIngredient: modelResult) {
                JSONObject toAdd = new JSONObject();

                toAdd.put("id", actualIngredient.getId());
                toAdd.put("name", actualIngredient.getName());
                toAdd.put("amount", actualIngredient.getAmount());
                toAdd.put("measurment", actualIngredient.getMeasurment());

                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
            
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
