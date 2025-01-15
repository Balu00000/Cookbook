package com.maven.cookbook.controller;

import com.maven.cookbook.service.DifficultyService;
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

@Path("difficulty") //D.Model->D.Service->D.Controller
public class DifficultyController {

    @Context
    private UriInfo context;
    private DifficultyService layer = new DifficultyService(); 

    public DifficultyController() {
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
    @Path("getAllDifficulty")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDifficulty(){
        JSONObject obj = layer.getAllDifficulty();
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
