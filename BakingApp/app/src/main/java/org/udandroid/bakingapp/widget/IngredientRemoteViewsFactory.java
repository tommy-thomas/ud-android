package org.udandroid.bakingapp.widget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.service.IngredientListService;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List <Ingredient> ingredientList;
    private final String TAG = IngredientRemoteViewsFactory.class.getSimpleName();
    private IngredientListReceiver ingredientListReceiver;


    public IngredientRemoteViewsFactory(Context context, Intent intent) {

        this.context = context;

//            Gson gson = new Gson();
//            String stringIngredientList = intent.getExtras().getString("ingredientList");//getStringExtra("ingredientList");
//            Type type = new TypeToken <List <Ingredient>>() {
//            }.getType();
//            ingredientList = gson.fromJson(stringIngredientList, type);
//            Log.d(TAG , ingredientList.get(0).getIngredient());
        //IngredientListService.startActionGetIngredientList(context);

    }

    @Override
    public void onCreate() {
        registerIngredientListReceiver();
        IngredientListService.startActionGetIngredientList(context);
    }


    @Override
    public void onDataSetChanged() {
        IngredientListService.startActionGetIngredientList(context);
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(ingredientListReceiver);
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
        ingredientListReceiver = new IngredientListReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IngredientListService.ACTION_GET_INGREDIENT_LIST);
        context.registerReceiver(ingredientListReceiver, intentFilter);
    }

    private class IngredientListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String stringIngredientList = intent.getStringExtra("ingredient-list");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Ingredient>>() {}.getType();
            ingredientList = gson.fromJson(stringIngredientList, type);
            Log.d(TAG , "Data received...");
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
        return false;
    }


}
