package org.udandroid.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapters.IngredientAdapter;
import org.udandroid.bakingapp.adapters.StepAdapter;
import org.udandroid.bakingapp.fragments.MasterStepListFragment;
import org.udandroid.bakingapp.fragments.StepDetailFragment;
import org.udandroid.bakingapp.models.Ingredient;
import org.udandroid.bakingapp.models.Recipe;
import org.udandroid.bakingapp.models.Step;

import java.lang.reflect.Type;
import java.util.List;

public class StepActivity extends AppCompatActivity implements
        MasterStepListFragment.StepClickListener {

    private StepAdapter stepAdapter;
    public List <Step> stepList;
    Step currentStep;
    private List <Ingredient> ingredientList;
    private final static String TAG = StepActivity.class.getSimpleName();
    private boolean mTwoPane;
    private TextView tvIngredient;
    private Gson gson = new Gson();
    private IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        String stringRecipe = getIntent().getStringExtra("RECIPE_EXTRA");

        Type type_recipe = new TypeToken <Recipe>() {
        }.getType();
        Recipe recipe = gson.fromJson(stringRecipe, type_recipe);
        this.setTitle( recipe.getName() );
        stepList = recipe.getSteps();
        ingredientList = recipe.getIngredients();

        Log.d(TAG , "Two Pane: " + ingredientList.get(0).getIngredient().toString());


        // Two panes?
        if (findViewById(R.id.ll_fragment_step_detail) != null) {
            mTwoPane = true;
            setIngredientSheet();
            RecyclerView recyclerView = findViewById(R.id.rv_ingredient);
            recyclerView.setHasFixedSize(true);
            ingredientAdapter = new IngredientAdapter(this, ingredientList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(ingredientAdapter);

        } else {
            mTwoPane = false;
        }

        // check for saved step in savedInstanceState.
        if (savedInstanceState != null) {
            Type type_step = new TypeToken <Step>() {
            }.getType();
            currentStep = gson.fromJson(savedInstanceState.getString("stringStep"), type_step);
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void setIngredientSheet(){
        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // set the peek height
        bottomSheetBehavior.setPeekHeight(200);

        // set hideable or not
        bottomSheetBehavior.setHideable(false);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mTwoPane) {

            currentStep = currentStep != null ? currentStep : stepList.get(0);
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setDescription(currentStep.getDescription());
            stepDetailFragment.setVideoUrl(currentStep.getVideoURL());
            stepDetailFragment.setIngredientList(ingredientList);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.fr_step_detail_container, stepDetailFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentStep != null) {
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

                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setDescription(currentStep.getDescription());
                stepDetailFragment.setVideoUrl(currentStep.getVideoURL());
                stepDetailFragment.setIngredientList(ingredientList);

                FragmentManager fragmentManager = getSupportFragmentManager();


                fragmentManager.beginTransaction()
                        .replace(R.id.fr_step_detail_container, stepDetailFragment)
                        .commit();

            } else {

                final Intent intent = new Intent(this, StepDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoURL", currentStep.getVideoURL());
                bundle.putString("Description", currentStep.getDescription());
                bundle.putString("stepLabel", currentStep.getShortDescription());
                Gson gson = new Gson();
                Type type_ingredient = new TypeToken <List <Ingredient>>() {
                }.getType();
                String json_ingredient = gson.toJson(ingredientList, type_ingredient);
                bundle.putString("stringIngredient", json_ingredient);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        }

    }

    public void hideIngredientList() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_ingredient);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void onIngredientClicked() {

        if (mTwoPane) {

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setIngredientList(ingredientList);
                stepDetailFragment.setShowStepDetail(false);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.fr_step_detail_container, stepDetailFragment)
                    .commit();
        } else {

            final Intent intent = new Intent(this, StepDetailActivity.class);
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            Type type_ingredient = new TypeToken <List <Ingredient>>() {
            }.getType();
            String json_ingredient = gson.toJson(ingredientList, type_ingredient);
            bundle.putString("stringIngredient", json_ingredient);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    //TODO add a flag to bundles in  onIngredientClicked and onStepSelected that can be passed to stepDetailFragment.setShowStepDetail(true);
}
