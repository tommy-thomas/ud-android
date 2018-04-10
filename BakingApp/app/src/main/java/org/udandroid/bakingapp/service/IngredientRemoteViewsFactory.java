package org.udandroid.bakingapp.service;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.util.RecipeData;

import java.util.List;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList;
    private final String TAG = IngredientRemoteViewsFactory.class.getSimpleName();
    private RecipeData recipeData;


    public IngredientRemoteViewsFactory(Context context, Intent intent){

        this.context = context;
        recipeData = new RecipeData(context);
        ingredientList = recipeData.getIngredientList();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

//        if (mCursor != null) {
//            mCursor.close();
//        }
//
//        final long identityToken = Binder.clearCallingIdentity();
//        Uri uri = RecipeContract.BASE_CONTENT_URI;
//        mCursor = context.getContentResolver().query(uri,
//                null,
//                null,
//                null,
//                _ID + " DESC");
//
//        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {


    }

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

}
