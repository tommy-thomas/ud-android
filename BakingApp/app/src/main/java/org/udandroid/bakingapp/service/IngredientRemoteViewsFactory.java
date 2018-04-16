package org.udandroid.bakingapp.service;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList;
    private final String TAG = IngredientRemoteViewsFactory.class.getSimpleName();


    public IngredientRemoteViewsFactory(Context context, Intent intent){

        this.context = context;

    }

    private void setIngredientList(List ingredientList){
        this.ingredientList = ingredientList;
    }

    @Override
    public void onCreate() {
        new TaskRecentRecipeTask(context).execute();
    }

    @Override
    public void onDataSetChanged() {

        //new TaskRecentRecipeTask(context).execute();
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        return ( ingredientList != null ) ? ingredientList.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_ingredient_item);
        if( ingredientList != null && ingredientList.size() > 0){
        remoteViews.setTextViewText(R.id.tv_ingredient, ingredientList.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.tv_ingredient_measure, ingredientList.get(position).getMeasure());
        remoteViews.setTextViewText(R.id.tv_ingredient_quantity, ingredientList.get(position).getQuantity());
        }

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

    private class TaskRecentRecipeTask extends AsyncTask<String,String,Recipe> {

        private Context aynscContext;

        public TaskRecentRecipeTask(Context context){
            aynscContext = context;
        }

        @Override
        protected Recipe doInBackground(String... strings) {
            RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(aynscContext);
            return recipeDatabase.recipeDAO().getRecent();
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            if( recipe != null ){
                setIngredientList((List) recipe.getIngredients());
            }
        }
    }

}
