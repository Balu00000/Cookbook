package com.maven.cookbook.service;

import com.maven.cookbook.model.Cuisine;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CuisineService { //C.Model->C.Service->C.Controller
    private final Cuisine layer = new Cuisine();
    
    public JSONObject getAllCuisine(){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Cuisine> modelResult = layer.getAllCuisine();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noCuisineFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();
            
            for(Cuisine cuisine: modelResult) {
                JSONObject toAdd = new JSONObject();
                
                toAdd.put("id", cuisine.getId());
                toAdd.put("name", cuisine.getType());

                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
