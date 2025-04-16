package com.maven.cookbook.service;

import com.maven.cookbook.model.Dietary;
import com.maven.cookbook.repository.DietaryRepository;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class DietaryService { //D.Model->D.Service->D.Controller
    protected DietaryRepository layer = new DietaryRepository();
    
    public JSONObject getAllDietary(){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Dietary> modelResult = layer.getAllDietary();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noDietFound";
            statusCode = 404;
        }else {
            JSONArray result = new JSONArray();
            
            for(Dietary diet: modelResult) {
                JSONObject toAdd = new JSONObject();
                
                toAdd.put("id", diet.getId());
                toAdd.put("type", diet.getType());

                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
