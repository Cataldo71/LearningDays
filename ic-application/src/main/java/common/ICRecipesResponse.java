package common;

import com.autodesk.ic.content.rest.objects.ICRecipe;

import java.util.List;

/**
 * Created by cataldp on 1/27/2015.
 */
public class ICRecipesResponse {
    List<ICRecipe> recipes;

    public List<ICRecipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<ICRecipe> recipes) {
        this.recipes = recipes;
    }
}
