package com.maven.cookbook.service; //Default Java Class

import com.maven.cookbook.config.JWT;
import com.maven.cookbook.model.User;
import com.maven.cookbook.model.UserDTO;
import com.maven.cookbook.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;


public class UserService { //U.Model->U.Repository->U.Service->U.Controller
    protected UserRepository layer = new UserRepository();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public boolean isValidPassword(String password){
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
                    statusCode = 404;
                }else if(modelResult.getIsDeleted() == true){
                    status = "deletedUser";
                    statusCode = 404;
                }
                else {
                    JSONObject result = new JSONObject();
                    result.put("id", modelResult.getId());
                    result.put("username", modelResult.getUsername());
                    result.put("image", modelResult.getBase64Image());
                    result.put("email", modelResult.getEmail());
                    result.put("isAdmin", modelResult.getIsAdmin());
                    result.put("createdAt", modelResult.getCreatedAt());
                    result.put("isDeleted", modelResult.getIsDeleted());
                    result.put("deletedAt", modelResult.getDeletedAt());
                    result.put("jwt", JWT.createJWT(modelResult));

                    toReturn.put("result", result);
                }
            }
        } else {
            status = "InvalidEmail";
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
                
                Boolean userIsExists = UserRepository.isUserExists(u.getEmail());
                
                if(userIsExists == null){
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
            } else {
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

    public JSONObject registerAdmin(User u, String jwt) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        //if(JWT.isAdmin(jwt)) {
            if(isValidEmail(u.getEmail())){
                if(isValidPassword(u.getPassword())){
                    boolean userIsExists = UserRepository.isUserExists(u.getEmail());
                    
                    if(UserRepository.isUserExists(u.getEmail()) == null){
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
        /*}else{
            status = "PermissionError";
            statusCode=403;
        }*/
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }

    public JSONObject getAllUser(String jwt) {
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        //if(JWT.isAdmin(jwt)) {
            List<User> modelResult = layer.getAllUser();
            
            if(modelResult == null) {
                status = "modelException";
                statusCode = 500;
            }else if (modelResult.isEmpty()) {
                status = "noUserFound";
                statusCode = 404;
            }else {
                JSONArray result = new JSONArray();

                for(User actualUser: modelResult) {
                    JSONObject toAdd = new JSONObject();

                    toAdd.put("id", actualUser.getId());
                    toAdd.put("username", actualUser.getUsername());
                    toAdd.put("image", actualUser.getBase64Image());
                    toAdd.put("email", actualUser.getEmail());
                    toAdd.put("password", actualUser.getPassword());
                    toAdd.put("isAdmin", actualUser.getIsAdmin());
                    toAdd.put("createdAt", actualUser.getCreatedAt());
                    toAdd.put("isDeleted", actualUser.getIsDeleted());
                    toAdd.put("deletedAt", actualUser.getDeletedAt());

                    result.put(toAdd);
                }
                toReturn.put("result", result);
            }
        /*}else{
            status = "PermissionError";
            statusCode=403;
        }*/
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }

    public JSONObject getUserProfileInformation(Integer id){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        UserDTO modelResult = layer.getUserProfileInformation(id);
        if(modelResult == null) {
            status = "modelException";
            statusCode = 500;
        }else if (modelResult.getUser().getUsername() == null) {
            status = "noUserFound";
            statusCode = 404;
        }else {
            JSONObject result = new JSONObject();
            
            result.put("username", modelResult.getUser().getUsername());
            result.put("image", modelResult.getUser().getBase64Image());
            result.put("createdAt", modelResult.getUser().getCreatedAt());
            result.put("postedFood", modelResult.getPostedFood());
            result.put("favouritedFood", modelResult.getFavouriteFood());

            toReturn.put("result", result);
        }
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject deleteUserById(Integer id, String jwt){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        if(JWT.isAdmin(jwt)) {
            boolean deleteUserById = layer.deleteUserById(id);
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
    
    public JSONObject updateUsername(Integer id, String username){
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        boolean updateUsername = layer.updateUsername(id, username);
        if(updateUsername == false){
            status = "fail";
            statusCode = 417;
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
    
    public JSONObject updateImage(Integer id, String image) throws IOException{
        JSONObject toReturn = new JSONObject();
        String status = "success";
        int statusCode = 200;
        
        boolean updateImage = layer.updateImage(id, image);
        if(updateImage == false){
            status = "fail";
            statusCode = 417;
        }
        
        toReturn.put("status", status);
        toReturn.put("statusCode", statusCode);
        return toReturn;
    }
}
