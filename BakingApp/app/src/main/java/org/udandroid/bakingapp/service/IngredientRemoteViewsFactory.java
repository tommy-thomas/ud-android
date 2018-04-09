package org.udandroid.bakingapp.service;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_INGREDIENT_BUNDLE;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.CONTENT_URI;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry._ID;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList;
    private final String TAG = IngredientRemoteViewsFactory.class.getSimpleName();
    private Gson gson = new Gson();
    private Cursor mCursor;

    public IngredientRemoteViewsFactory(Context context, Intent intent){

        this.context = context;
        setIngredientList();

    }

    private void setIngredientList(){
        try {

            Cursor cursor = context.getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    _ID + " DESC LIMIT 1");

            if (cursor.moveToFirst()) {

                    //String stringRecipe = getIntent().getStringExtra("RECIPE_EXTRA");
                    Type type_ingredient = new TypeToken<List<Ingredient>>() {
                    }.getType();
                    ingredientList = gson.fromJson( cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT_BUNDLE)) , type_ingredient);

            }

        } catch (Exception e) {
            Log.e(TAG, "Failed to load data.");
            e.printStackTrace();
        }
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
        if (mCursor != null) {
            mCursor.close();
        }

    }

    @Override
    public int getCount() {
        return ( ingredientList != null ) ? ingredientList.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_ingredient_item);
        if( ingredientList != null ){
        remoteViews.setTextViewText(R.id.tv_ingredient, ingredientList.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.tv_ingredient_measure, ingredientList.get(position).getMeasure());
        remoteViews.setTextViewText(R.id.tv_ingredient_quantity, ingredientList.get(0).getQuantity());
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
