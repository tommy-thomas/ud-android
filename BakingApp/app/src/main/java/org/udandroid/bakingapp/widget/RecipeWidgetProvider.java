package org.udandroid.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.service.IngredientRemoteViewsService;
import org.udandroid.bakingapp.util.RecipeData;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private List<Ingredient> ingredientList;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RecipeData recipeData =new RecipeData(context);
        CharSequence widgetTitle = recipeData.getRecipeName() != null &&
                recipeData.getRecipeName() != "" ? recipeData.getRecipeName() : "Recipe";


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

       views.setTextViewText(R.id.tv_recipe_title , widgetTitle);

        try {
            Intent intent = new Intent(context, IngredientRemoteViewsService.class);
            views.setRemoteAdapter(R.id.lv_ingredient_list, intent);
        } catch (Exception e){
            e.getMessage();
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

