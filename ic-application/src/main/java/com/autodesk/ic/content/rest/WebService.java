package com.autodesk.ic.content.rest;

import com.autodesk.ic.content.rest.objects.ICRecipe;
import common.ICRecipesResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cataldp on 1/22/2015.
 */
@Path(WebService.WEBSERVICE_PATH)
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class WebService {
    public static final String WEBSERVICE_PATH = "/contentservice/v1";

    /**
     * Gets information about the running server.
     * This function does not require authentication.
     * @return Information about the running server.
     */
    @Path("system")
    @GET
    public String getSystem()  {

        return "Hello from the Content Service!";
    }

    @Path("recipes")
    @GET
    public ICRecipesResponse getAllRecipes() {
        ICRecipesResponse response = new ICRecipesResponse();
        List<ICRecipe> sampleRecipes = new ArrayList<ICRecipe>();
        ICRecipe recipe = new ICRecipe();
        recipe.setDescrption("Sample Recipe");
        recipe.setPartNumber("1234");
        recipe.setxPos(0);
        recipe.setyPos(0);
        sampleRecipes.add(recipe);
        response.setRecipes(sampleRecipes);

        return response;
    }
}
