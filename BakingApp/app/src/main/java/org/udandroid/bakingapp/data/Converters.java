package org.udandroid.bakingapp.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.model.Step;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tommy-thomas on 4/14/18.
 */

public class Converters {

    @TypeConverter
    public static List<Ingredient> stringToIngredientsts(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredientList= gson.fromJson(json, type);
        return ingredientList;
    }

    @TypeConverter
    public static String ingredientsToString(List<Ingredient> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public static List<Step> stringToSteps(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();
        List<Step> steptList= gson.fromJson(json, type);
        return steptList;
    }

    @TypeConverter
    public static String stepsToString(List<Step> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public static List<Recipe> stringToRecipes(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipeList= gson.fromJson(json, type);
        return recipeList;
    }

    @TypeConverter
    public static String recipesToString(List<Recipe> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }

}
