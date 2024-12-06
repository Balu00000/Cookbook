/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chef.cookbook.service;

import com.chef.cookbook.config.JWT;
import com.chef.cookbook.model.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class UserService {
    private User layer = new User();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static boolean isValidPassword(String password){
        if(password.length() < 8){
            return false;
        }
        
        boolean hasNumber = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasSpecialChar = false;
        
        for(char c: password.toCharArray()){
            if(Character.isDigit(c)){
                hasNumber = true;
            }else if(Character.isUpperCase(c)){
                hasUpperCase = true;
            }else if(Character.isLowerCase(c)){
                hasLowerCase = true;
            }else if("!@#$%^&*()_+-=[]{}|;':,.<>?/`~".indexOf(c) != -1){
                hasSpecialChar = true;
            }
        }
        return hasNumber && hasUpperCase && hasLowerCase && hasSpecialChar;
    }
    
    public JSONObject login(String email, String password) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;

        if (isValidEmail(email)) {
            User modelResult = layer.login(email, password);

            if (modelResult == null) {
                status = "modelException";
                statusCode = 500;
            } else {
                if (modelResult.getId() == null) {
                    status = "userNotFound";
                    statusCode = 417;
                } else {
                    JSONObject result = new JSONObject();
                    result.put("id", modelResult.getId());
                    result.put("username", modelResult.getUsername());
                    result.put("image", modelResult.getImage());
                    result.put("email", modelResult.getEmail());
                    result.put("isAdmin", modelResult.getIsAdmin());
                    result.put("isDeleted", modelResult.getIsDeleted());
                    result.put("jwt", JWT.createJWT(modelResult));

                    toReturn.put("result", result);
                }
            }

        } else {
            status = "invalidEmail";
            statusCode = 417;
        }

        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject registerUser(User u) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        if(isValidEmail(u.getEmail())){
            if(isValidPassword(u.getPassword())){
                boolean userIsExists = User.isUserExists(u.getEmail());
                if(User.isUserExists(u.getEmail()) == null){
                    status = "ModelException";
                    statusCode = 500;
                }else if (userIsExists == true){
                    status = "UserAlreadyExists";
                    statusCode = 417;
                }else{
                    boolean registerUser = layer.registerUser(u);
                    if(registerUser == false){
                        status = "fail";
                        statusCode = 417;
                    }
                }
            }else{
                status = "InvalidPassword";
                statusCode = 417;
            }
        }else{
            status = "InvalidEmail";
            statusCode = 417;
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject registerAdmin(User u) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        if(isValidEmail(u.getEmail())){
            if(isValidPassword(u.getPassword())){
                boolean userIsExists = User.isUserExists(u.getEmail());
                if(User.isUserExists(u.getEmail()) == null){
                    status = "ModelException";
                    statusCode = 500;
                }else if (userIsExists == true){
                    status = "UserAlreadyExists";
                    statusCode = 417;
                }else{
                    boolean registerAdmin = layer.registerAdmin(u);
                    if(registerAdmin == false){
                        status = "fail";
                        statusCode = 417;
                    }
                }
            }else{
                status = "InvalidPassword";
                statusCode = 417;
            }
        }else{
            status = "InvalidEmail";
            statusCode = 417;
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
