package com.maven.cookbook.controller;

import com.maven.cookbook.service.FavouriteService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("favourite") //F.Model->F.Service->F.Controller
public class FavouriteController {

    @Context
    private UriInfo context;
    private FavouriteService layer = new FavouriteService(); 

    public FavouriteController() {
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
    @Path("getFavouriteByUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavouriteByUser(@QueryParam("id") Integer id){
        
        JSONObject obj = layer.getFavouriteByUser(id);
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("addFavourite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFavourite(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.addFavourite(body.getInt("foodId"), body.getInt("userId"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Path("removeFavourite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFavourite(String bodyString){
        JSONObject body = new JSONObject(bodyString);
        
        JSONObject obj = layer.removeFavourite(body.getInt("foodId"), body.getInt("userId"));
        return Response.status(obj.getInt("statusCode")).entity(obj.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
