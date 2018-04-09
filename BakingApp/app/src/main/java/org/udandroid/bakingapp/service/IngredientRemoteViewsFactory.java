package org.udandroid.bakingapp.service;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.udandroid.bakingapp.helper.RecipeMapper;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.R;

import java.util.List;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList;

    public IngredientRemoteViewsFactory(Context context, Intent intent){

        this.context = context;
        //new FetchRecipesTask().execute();

    }

    @Override
    public void onCreate() {

        new FetchRecipesTask().execute();

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_ingredient_item);
        remoteViews.setTextViewText(R.id.tv_ingredient, ingredientList.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.tv_ingredient_measure, ingredientList.get(position).getMeasure());
        remoteViews.setTextViewText(R.id.tv_ingredient_quantity, ingredientList.get(0).getQuantity());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private class FetchRecipesTask extends AsyncTask<String, String, Recipe[]> {

        @Override
        protected Recipe[] doInBackground(String... strings) {
            RecipeMapper mapper = new RecipeMapper();
            mapper.mapData();
            Recipe[] recipes = mapper.recipes();
            return null;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            // super.onPostExecute(recipes);
           ingredientList = recipes[0].getIngredients();
        }
    }
}
