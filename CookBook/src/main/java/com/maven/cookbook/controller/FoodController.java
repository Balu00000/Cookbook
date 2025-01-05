package com.maven.cookbook.controller;

import com.maven.cookbook.service.FoodService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("food") //F.Model->F.Service->F.Controller
public class FoodController {

    @Context
    private UriInfo context;
    private FoodService layer = new FoodService();

    public FoodController() {
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
    
    @GET
    @Path("getFoodByUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByUser(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.getFoodByUser(body.getInt("id"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByRating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByRating(){
        JSONObject obj = layer.getFoodByRating();
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByRandom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByRandom(){
        JSONObject obj = layer.getFoodByRandom();
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByDifficulty")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByDifficulty(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.getFoodByDifficulty(body.getInt("id"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByDietary")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByDietary(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.getFoodByDietary(body.getInt("id"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByCuisine")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByCuisine(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.getFoodByCuisine(body.getInt("id"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByMealType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByMealType(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.getFoodByMealType(body.getInt("id"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
