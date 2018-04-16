package org.udandroid.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by tommy-thomas on 3/30/18.
 */
@Entity(tableName = "recipes")
public class Recipe {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "servings")
    private String servings;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "ingredients")
    private List<Ingredient> ingredients;

    @ColumnInfo(name = "steps")
    private List<Step> steps;

    @ColumnInfo(name = "date")
    private long date;

    public long getDate(){
        return date;
    }

    public void setDate(long date){
        this.date = date;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
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
