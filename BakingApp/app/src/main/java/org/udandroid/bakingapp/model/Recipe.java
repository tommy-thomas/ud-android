package org.udandroid.bakingapp.model;

import java.util.List;

/**
 * Created by tommy-thomas on 3/30/18.
 */

public class Recipe {

    private String id;
    private String name;
    private String servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> steps;


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getServings(){
        return servings;
    }

    public void setServings(String servings){
        this.servings = servings;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public List<Step> getSteps(){
        return steps;
    }

    public void setSteps(List<Step> steps){
        this.steps = steps;
    }
}
