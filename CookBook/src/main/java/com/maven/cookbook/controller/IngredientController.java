package com.maven.cookbook.controller;

import com.maven.cookbook.service.IngredientService;
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

@Path("ingredient")
public class IngredientController {

    @Context
    private UriInfo context;
    private final IngredientService layer = new IngredientService(); 

    public IngredientController() {
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
    @Path("getIngredientByFoodId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientByFoodId(@QueryParam("id") Integer id){
        
        JSONObject obj = layer.getIngredientByFoodId(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
