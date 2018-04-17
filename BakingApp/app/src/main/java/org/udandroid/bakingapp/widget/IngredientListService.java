package org.udandroid.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by tommy-thomas on 4/17/18.
 */

public class IngredientListService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public static final String ACTION_UPDATE_INGREDIENTS = "org.udandroid.bakingapp.service.action.update_ingredients";

    public IngredientListService() {
        super("IngredientListService");
    }

    public static void startActionUpdateIngredients(Context context){
        Intent intent = new Intent(context , IngredientListService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if( intent != null){
            final String action = intent.getAction();
            if( ACTION_UPDATE_INGREDIENTS.equals(action)){
                handleActionUpdateIngredients();
            }
        }

    }

    private void handleActionUpdateIngredients(){
        RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(this);
        Recipe recipe = recipeDatabase.recipeDAO().getRecent();
        String recipeName = recipe.getName();
        List<Ingredient> ingredientList = recipe.getIngredients();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredient_list);
       RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager , appWidgetIds, recipeName, ingredientList);
    }
}
