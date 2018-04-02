package org.udandroid.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.adapters.StepAdapter;
import org.udandroid.bakingapp.models.Recipe;
import org.udandroid.bakingapp.models.Step;

import java.lang.reflect.Type;
import java.util.List;

public class StepActivity extends AppCompatActivity {

    private StepAdapter stepAdapter;
    private List<Step> stepList;
    private final static String TAG = StepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent recipeIntent = getIntent();

        Gson gson = new Gson();
        String stringRecipe= recipeIntent.getStringExtra("RECIPE_EXTRA");
        if(stringRecipe != null) {
            Type type_ingredient = new TypeToken<Recipe>() {}.getType();
            Recipe recipe = gson.fromJson(stringRecipe, type_ingredient);
            this.setTitle(recipe.getName());
            stepList = recipe.getSteps();
            Log.d(TAG, recipe.getIngredients().get(0).getIngredient().toString());

            loadStepViews();
        }
        else{
            Log.d(TAG,"failed");
        }


    }

    private void loadStepViews(){
        RecyclerView recyclerView = findViewById(R.id.rv_step);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        stepAdapter = new StepAdapter(getApplicationContext() , stepList);
        recyclerView.setAdapter(stepAdapter);
    }
}
