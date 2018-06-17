package org.udandroid.bakingapp.widget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.multidex.BuildConfig;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.releasetree.BakingAppReleaseTree;
import org.udandroid.bakingapp.service.IngredientWidgetService;

import java.lang.reflect.Type;
import java.util.List;

import timber.log.Timber;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List <Ingredient> ingredientList;
    private final String TAG = IngredientRemoteViewsFactory.class.getSimpleName();
    private IngredientWidgetReceiver ingredientWidgetReceiver;


    public IngredientRemoteViewsFactory(Context context, Intent intent) {

        this.context = context;

        registerIngredientListReceiver();

    }

    @Override
    public void onCreate() {

        if( BuildConfig.DEBUG ){
            Timber.plant( new Timber.DebugTree());
        } else {
            Timber.plant( new BakingAppReleaseTree() );
        }
        IngredientWidgetService.startActionGetIngredientList(context);
    }


    @Override
    public void onDataSetChanged() {
        IngredientWidgetService.startActionGetIngredientList(context);
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(ingredientWidgetReceiver);
    }

    @Override
    public int getCount() {
        return (ingredientList != null) ? ingredientList.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_ingredient_item);
        if (ingredientList != null && ingredientList.size() > 0) {
            remoteViews.setTextViewText(R.id.tv_ingredient, ingredientList.get(position).getIngredient());
            remoteViews.setTextViewText(R.id.tv_ingredient_measure, ingredientList.get(position).getMeasure());
            remoteViews.setTextViewText(R.id.tv_ingredient_quantity, ingredientList.get(position).getQuantity());
        }

        return remoteViews;
    }

    private void registerIngredientListReceiver(){
        ingredientWidgetReceiver = new IngredientWidgetReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IngredientWidgetService.ACTION_GET_INGREDIENT_LIST);
        context.registerReceiver(ingredientWidgetReceiver, intentFilter);
    }

    private class IngredientWidgetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stringIngredientList = intent.getStringExtra("ingredient-list");
            if( stringIngredientList != null && stringIngredientList != ""){
                Gson gson = new Gson();
                Type type = new TypeToken <List <Ingredient>>() {
                }.getType();
                ingredientList = gson.fromJson(stringIngredientList, type);
                Timber.v("Data received.");
            } else {
                Timber.d("No data Data received.");
            }
        }
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


}
