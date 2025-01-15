package com.maven.cookbook.service;

import com.maven.cookbook.model.Difficulty;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class DifficultyService { //D.Model->D.Service->D.Controller
    private Difficulty layer = new Difficulty();
    
    public JSONObject getAllDifficulty(){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        List<Difficulty> modelResult = layer.getAllDifficulty();
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.isEmpty()) {
            status = "noDifficultyFound";
            statusCode = 417;
        }else {
            JSONArray result = new JSONArray();
            
            for(Difficulty actualUser: modelResult) {
                JSONObject toAdd = new JSONObject();
                
                toAdd.put("id", actualUser.getId());
                toAdd.put("name", actualUser.getName());
                toAdd.put("equipment", actualUser.getEquipment());

                result.put(toAdd);
            }
            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
