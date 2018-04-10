package org.udandroid.bakingapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.udandroid.bakingapp.model.Recipe;

import java.net.URL;

/**
 * Created by tommy-thomas on 3/31/18.
 */

public class RecipeMapper {

    private static final String TAG = RecipeMapper.class.getSimpleName();
    ObjectMapper Mapper;
    public static final String URL_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private Recipe[] recipes;

    public void mapData() {
        Mapper = new ObjectMapper();
        try {
            recipes = Mapper.readValue(new URL(URL_RECIPES), Recipe[].class);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Recipe[] recipes(){
        return recipes;
    }

}
