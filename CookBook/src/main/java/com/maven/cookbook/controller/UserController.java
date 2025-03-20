//RESTful webservice from patterns generated file.
package com.maven.cookbook.controller; 

import com.maven.cookbook.config.JWT;
import com.maven.cookbook.model.User;
import com.maven.cookbook.service.UserService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("user") //U.Model->U.Service->U.Controller
public class UserController {
    @Context
    private UriInfo context;
    private final UserService layer = new UserService(); 

    public UserController() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.login(body.getString("email"), body.getString("password"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("registerUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        User u = new User(
            body.getString("username"),
            (byte[]) body.get("image"),
            body.getString("email"),
            body.getString("password")
        );
        
        JSONObject obj = layer.registerUser(u);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("registerAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAdmin(@HeaderParam("token") String jwt, String bodyString){ //This is used on the admin page
        JSONObject body = new JSONObject(bodyString);
        
        User u = new User(
            body.getString("username"),
            (byte[]) body.get("image"),
            body.getString("email"),
            body.getString("password")
        );
        
        JSONObject obj = layer.registerAdmin(u, jwt);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getAllUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser(@HeaderParam("token") String jwt){ //This is used on the admin page
        
        JSONObject obj = layer.getAllUser(jwt);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getUserProfileInformation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfileInformation(@QueryParam("id") Integer id){
        
        JSONObject obj = layer.getUserProfileInformation(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("deleteUserById")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUserById(@HeaderParam("token") String jwt, @QueryParam("id") Integer id){ //This is an admin only command -- Todo Write null value handler
        JSONObject obj = layer.deleteUserById(id, jwt);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
