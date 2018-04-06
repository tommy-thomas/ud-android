package org.udandroid.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapters.StepAdapter;
import org.udandroid.bakingapp.fragments.IngredientFragment;
import org.udandroid.bakingapp.fragments.MasterStepListFragment;
import org.udandroid.bakingapp.fragments.RecipeDetailFragment;
import org.udandroid.bakingapp.models.Ingredient;
import org.udandroid.bakingapp.models.Recipe;
import org.udandroid.bakingapp.models.Step;

import java.lang.reflect.Type;
import java.util.List;

public class StepActivity extends AppCompatActivity implements MasterStepListFragment.StepClickListener,
        IngredientFragment.IngredientClickListener{

    private StepAdapter stepAdapter;
    public List <Step> stepList;
    Step currentStep;
    private List <Ingredient> ingredientList;
    private final static String TAG = StepActivity.class.getSimpleName();
    private boolean mTwoPane;
    private TextView tvIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        String stringRecipe = getIntent().getStringExtra("RECIPE_EXTRA");

        Gson gson = new Gson();
        Type type_recipe = new TypeToken <Recipe>() {
        }.getType();
        Recipe recipe = gson.fromJson(stringRecipe, type_recipe);
        stepList = recipe.getSteps();
        ingredientList = recipe.getIngredients();

        if (findViewById(R.id.ll_fragment_step_detail) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }


        if( savedInstanceState != null){
            Type type_step= new TypeToken <Step>() {
            }.getType();
            currentStep = gson.fromJson( savedInstanceState.getString("stringStep"), type_step);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mTwoPane) {

            currentStep = currentStep != null ? currentStep : stepList.get(0);
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setDescription(currentStep.getDescription());
            recipeDetailFragment.setVideoUrl(currentStep.getVideoURL());

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.fr_step_detail_container, recipeDetailFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentStep != null ) {
            Gson gson = new Gson();
            Type type_step = new TypeToken <Step>() {
            }.getType();
            String json_step = gson.toJson(currentStep, type_step);
            outState.putString("stringStep", json_step);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepSelected(Step step) {

        if (step != null) {

            currentStep = step;

            String stepLabel = currentStep.getShortDescription();

            if (mTwoPane) {

                this.setTitle(stepLabel);

                RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                recipeDetailFragment.setDescription(currentStep.getDescription());
                recipeDetailFragment.setVideoUrl(currentStep.getVideoURL());

                FragmentManager fragmentManager = getSupportFragmentManager();


                fragmentManager.beginTransaction()
                        .replace(R.id.fr_step_detail_container, recipeDetailFragment)
                        .commit();

            } else {

                final Intent intent = new Intent(this, StepDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoURL", currentStep.getVideoURL());
                bundle.putString("Description", currentStep.getDescription());
                bundle.putString("stepLabel", currentStep.getShortDescription());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        }

    }

    @Override
    public void onIngredientClicked() {

        final Intent intent = new Intent(this, IngredientActivity.class);
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        Type type_ingredient = new TypeToken<List<Ingredient>>() {
        }.getType();
        String json_ingredient = gson.toJson(ingredientList, type_ingredient);
        bundle.putString("stringIngredient" , json_ingredient);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
