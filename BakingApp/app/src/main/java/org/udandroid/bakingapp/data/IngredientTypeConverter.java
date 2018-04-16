package org.udandroid.bakingapp.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tommy-thomas on 4/14/18.
 */

public class IngredientTypeConverter {
   @android.arch.persistence.room.TypeConverter
    public static List<Ingredient> stringToIngredientsts(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredientList= gson.fromJson(json, type);
        return ingredientList;
    }

    @android.arch.persistence.room.TypeConverter
    public static String ingredientsToString(List<Ingredient> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
