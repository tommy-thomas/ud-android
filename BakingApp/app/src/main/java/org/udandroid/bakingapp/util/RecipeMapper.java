package org.udandroid.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.udandroid.bakingapp.IdlingResource.SimpleIdlingResource;
import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.model.Recipe;

import java.net.URL;

/**
 * Created by tommy-thomas on 3/31/18.
 */

public class RecipeMapper {

    private Context context;

    private static final int DELAY_MILLIS = 3000;

    private static final String TAG = RecipeMapper.class.getSimpleName();

    public static final String URL_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private Recipe[] recipes;

    /**
     * Temp idle resource method
     */
    public interface DelayerCallback{
        void onDone(Recipe[] recipes);
    }

    public RecipeMapper(Context context) {
        this.context = context;
    }

    public void mapData( final DelayerCallback callback,
                         @Nullable final SimpleIdlingResource idlingResource ) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        final RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(context);
        if (isConnected) {
            try {

                ObjectMapper Mapper = new ObjectMapper();
                recipes = Mapper.readValue(new URL(URL_RECIPES), Recipe[].class);

                if (recipes.length > 0) {
                   recipeDatabase.recipeDAO().insert(recipes);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            if (recipeDatabase != null && recipeDatabase.recipeDAO().countRecipes() > 0)
                recipes = recipeDatabase.recipeDAO().getAll();
        }

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
            }
        };


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(recipes);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);

    }

    public Recipe[] recipes() {
        return (recipes != null) ? recipes : new Recipe[0];
    }

}
