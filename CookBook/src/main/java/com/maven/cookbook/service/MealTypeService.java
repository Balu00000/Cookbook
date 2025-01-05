package com.maven.cookbook.service;

import com.maven.cookbook.model.MealType;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class MealTypeService { //MT.Model->MT.Service->MT.Controller
    private MealType layer = new MealType();
    
    public JSONObject getAllMealType(){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<MealType> modelResult = layer.getAllMealType();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noMealTypeFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();
            
            for(MealType mealType: modelResult) {
                JSONObject toAdd = new JSONObject();
                
                toAdd.put("id", mealType.getId());
                toAdd.put("type", mealType.getType());

                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
