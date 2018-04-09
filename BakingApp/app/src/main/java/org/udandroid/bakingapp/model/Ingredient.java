package org.udandroid.bakingapp.model;

/**
 * Created by tommy-thomas on 3/30/18.
 */

public class Ingredient {

    private String quantity;
    private String measure;
    private String ingredient;

    public String getQuantity(){
        return quantity;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public String getMeasure(){
        return measure;
    }

    public void setMeasure(String measure){
        this.measure = measure;
    }

    public String getIngredient(){
        return ingredient;
    }

    public void setIngredient(String ingredient){
        this.ingredient =ingredient;
    }


}
