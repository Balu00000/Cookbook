package com.maven.cookbook.controller;

import com.maven.cookbook.service.FoodService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
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
    public Response getFoodByUser(@QueryParam("id") Integer id){
        JSONObject obj = layer.getFoodByUser(id);
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
    public Response getFoodByDifficulty(@QueryParam("id") Integer id){
        
        JSONObject obj = layer.getFoodByDifficulty(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByDietary")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByDietary(@QueryParam("id") Integer id){
        
        JSONObject obj = layer.getFoodByDietary(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByCuisine")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByCuisine(@QueryParam("id") Integer id){

        JSONObject obj = layer.getFoodByCuisine(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getFoodByMealType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodByMealType(@QueryParam("id") Integer id){
        
        JSONObject obj = layer.getFoodByMealType(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("getAllFood")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFood(){
        
        JSONObject obj = layer.getAllFood();
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
