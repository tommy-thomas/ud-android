package org.udandroid.bakingapp.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.widget.RecipeWidgetProvider;

import java.lang.reflect.Type;
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
    public static final String ACTION_GET_INGREDIENT_LIST = "org.udandroid.bakingapp.service.action.get_ingredient_list";
    final static String INGREDIENT_LIST_DATA = "ingredient_list_data";

    public IngredientListService() {
        super(IngredientListService.class.getName());
    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, IngredientListService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }

    public static void startActionGetIngredientList(Context context) {
        Intent intent = new Intent(context, IngredientListService.class);
        intent.setAction(ACTION_GET_INGREDIENT_LIST);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                handleActionUpdateIngredients();
            } else if (ACTION_GET_INGREDIENT_LIST.equals(action)) {
                sendIngredientListBackToClient();
            }
        }

    }

    private void sendIngredientListBackToClient() {
        RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(this);
        Recipe recipe = recipeDatabase.recipeDAO().getRecent();
        Gson gson = new Gson();
        Type type = new TypeToken <List <Ingredient>>() {
        }.getType();
        String json = gson.toJson(recipe.getIngredients(), type);
        Intent intent = new Intent();
        intent.setAction(ACTION_GET_INGREDIENT_LIST);
        intent.putExtra("ingredient-list", json);
        sendBroadcast(intent);
    }

    private void handleActionUpdateIngredients() {
        RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(this);
        if(recipeDatabase.recipeDAO().countRecipes() < 0 ){
            return;
        }
        Recipe recipe = recipeDatabase.recipeDAO().getRecent();
        if (recipe != null) {
            String recipeName = recipe.getName();
            List <Ingredient> ingredientList = recipe.getIngredients();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredient_list);
            RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager, appWidgetIds, recipeName, ingredientList);
        }
    }


}
