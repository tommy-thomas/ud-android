package org.udandroid.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.service.IngredientWidgetService;
import org.udandroid.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        views.setTextViewText(R.id.tv_recipe_title, "Recipe: " + recipeName);

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
        views.setOnClickPendingIntent(R.id.tv_recipe_title, pendingIntent);

        Intent intent = new Intent(context, IngredientRemoteViewsService.class);
        views.setRemoteAdapter(R.id.lv_ingredient_list, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     * @param recipeName
     */
    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds, String recipeName) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName );
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        IngredientWidgetService.startActionUpdateIngredients(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
       // super.onReceive(context, intent);
       IngredientWidgetService.startActionUpdateIngredients(context);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        IngredientWidgetService.startActionUpdateIngredients(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        IngredientWidgetService.startActionUpdateIngredients(context);
        IngredientWidgetService.startActionGetIngredientList(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

