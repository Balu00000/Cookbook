package com.maven.cookbook.controller;

import com.maven.cookbook.service.CuisineService;
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

@Path("cuisine") //C.Model->C.Service->C.Controller
public class CuisineController {

    @Context
    private UriInfo context;
    private CuisineService layer = new CuisineService(); 
    
    public CuisineController() {
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
    @Path("getAllCuisine")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCuisine(){
        
        JSONObject obj = layer.getAllCuisine();
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
