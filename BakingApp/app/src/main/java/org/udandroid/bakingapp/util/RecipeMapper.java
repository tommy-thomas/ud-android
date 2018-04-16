package org.udandroid.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.model.Recipe;

import java.net.URL;

/**
 * Created by tommy-thomas on 3/31/18.
 */

public class RecipeMapper {

    private Context context;

    private static final String TAG = RecipeMapper.class.getSimpleName();

    public static final String URL_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private Recipe[] recipes;

    public RecipeMapper(Context context) {
        this.context = context;
    }

    public void RecipeMapper(Context context) {
        this.context = context;
    }

    public void mapData() {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        ObjectMapper Mapper = new ObjectMapper();
        RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(context);


        if (isConnected) {
            try {
                recipes = Mapper.readValue(new URL(URL_RECIPES), Recipe[].class);
               // recipes = recipeDatabase.recipeDAO().getAll();

//                recipeDatabase.recipeDAO().clearRecipes();
//                recipeDatabase.recipeDAO().insertAll(recipes);

//                Recipe[] test = recipeDatabase.recipeDAO().getAll();
//                Log.d(TAG , test.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            if (recipeDatabase.recipeDAO().countRecipes() > 0)
                recipes = recipeDatabase.recipeDAO().getAll();


        }

    }

    public Recipe[] recipes() {
        return (recipes != null) ? recipes : new Recipe[0];
    }

}
