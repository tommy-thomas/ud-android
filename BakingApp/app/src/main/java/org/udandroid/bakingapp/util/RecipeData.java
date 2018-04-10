package org.udandroid.bakingapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_ID;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_INGREDIENT_BUNDLE;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME;
import static org.udandroid.bakingapp.data.RecipeContract.RecipeEntry.CONTENT_URI;

/**
 * Created by tommy-thomas on 4/10/18.
 */

public class RecipeData {

    private List<Ingredient> ingredientList;
    private String recipeName;
    private Context mContext;

    public RecipeData(Context context){
        try {

            mContext = context;

            Cursor cursor = mContext.getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    _ID + " DESC LIMIT 1");

            if (cursor.moveToFirst()) {

                Gson gson = new Gson();
                Type type_ingredient = new TypeToken<List<Ingredient>>() {
                }.getType();
                ingredientList =  gson.fromJson( cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT_BUNDLE)) , type_ingredient);
                recipeName= cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME));
                cursor.close();
            }

        } catch (Exception e) {
            Log.e("RecipeData: ", "Failed to load ingredient list.");
            e.printStackTrace();
        }
    }

    public void saveIngredientState(int recipeID, String recipeName, String jsonIngredient){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID , recipeID);
        contentValues.put(COLUMN_RECIPE_NAME, recipeName);
        contentValues.put(COLUMN_INGREDIENT_BUNDLE, jsonIngredient);

        Uri uri = mContext.getContentResolver().insert(CONTENT_URI , contentValues);

    }

    public List<Ingredient> getIngredientList(){
       return ingredientList;
    }

    public String getRecipeName(){
        return recipeName;
    }
}
