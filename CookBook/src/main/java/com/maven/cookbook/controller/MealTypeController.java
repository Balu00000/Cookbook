package com.maven.cookbook.controller;

import com.maven.cookbook.service.MealTypeService;
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

@Path("mealType") //MT.Model->MT.Service->MT.Controller
public class MealTypeController {

    @Context
    private UriInfo context;
    private MealTypeService layer = new MealTypeService(); 

    public MealTypeController() {
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
    @Path("getAllMealType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMealType(){
        
        JSONObject obj = layer.getAllMealType();
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
