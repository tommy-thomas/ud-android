package org.udandroid.bakingapp.util;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.service.IngredientWidgetService;
import org.udandroid.bakingapp.widget.RecipeWidgetProvider;

/**
 * Created by tommy-thomas on 4/21/18.
 */

public class SaveRecipeTask extends AsyncTask <Recipe, String, Recipe> {

    private Context mContext;

    public SaveRecipeTask(Context context) {
        mContext = context;
    }

    @Override
    protected Recipe doInBackground(Recipe... recipes) {
        recipes[0].setDate(System.currentTimeMillis());
        RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(mContext);
        recipeDatabase.recipeDAO().update(recipes[0]);

        return null;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        notifyServiceUpdateRecipeWidget();
    }

    private void notifyServiceUpdateRecipeWidget(){
        IngredientWidgetService.startActionGetIngredientList(mContext);

        Intent intent = new Intent(mContext, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(mContext).getAppWidgetIds(new ComponentName(mContext, RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        mContext.sendBroadcast(intent);
    }
}
