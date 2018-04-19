package org.udandroid.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapter.IngredientListAdapter;
import org.udandroid.bakingapp.data.RecipeDatabase;
import org.udandroid.bakingapp.fragment.MasterStepListFragment;
import org.udandroid.bakingapp.fragment.StepDetailFragment;
import org.udandroid.bakingapp.model.Ingredient;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.model.Step;
import org.udandroid.bakingapp.widget.RecipeWidgetProvider;

import java.lang.reflect.Type;
import java.util.List;

public class StepActivity extends AppCompatActivity implements
        MasterStepListFragment.StepClickListener {

    public List <Step> stepList;
    private Step currentStep;
    private List <Ingredient> ingredientList;
    private Recipe currentRecipe;
    private final static String TAG = StepActivity.class.getSimpleName();
    private boolean mTwoPane;
    private Gson gson = new Gson();
    private IngredientListAdapter ingredientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        String stringRecipe = getIntent().getStringExtra("RECIPE_EXTRA");

        Type type_recipe = new TypeToken <Recipe>() {
        }.getType();
        currentRecipe = gson.fromJson(stringRecipe, type_recipe);
        this.setTitle(currentRecipe.getName());
        stepList = currentRecipe.getSteps();
        ingredientList = currentRecipe.getIngredients();


        // Two panes?
        if (findViewById(R.id.ll_fragment_step_detail) != null) {
            mTwoPane = true;
            setIngredientSheet();
            RecyclerView recyclerView = findViewById(R.id.rv_ingredient);
            recyclerView.setHasFixedSize(true);
            ingredientListAdapter = new IngredientListAdapter(this, ingredientList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(ingredientListAdapter);

        } else {
            mTwoPane = false;
        }

        // check for saved step in savedInstanceState.
        if (savedInstanceState != null) {
            Type type_step = new TypeToken <Step>() {
            }.getType();
            currentStep = gson.fromJson(savedInstanceState.getString("stringStep"), type_step);
        }

        //Save current recipe as most recent
        currentRecipe.setDate(System.currentTimeMillis() / 1000);
        new SaveRecipeTask(this).execute(currentRecipe);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void setIngredientSheet() {
        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // set the peek height
        //bottomSheetBehavior.setPeekHeight(200);

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
            int currentIndex = currentStep.getId() - 1;
            int previousStepPos = currentIndex > 0 ? currentIndex - 1 : -1;
            int nextStepPos = currentIndex < ingredientList.size() - 1 ? currentIndex + 1 : -1;
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setDescription(currentStep.getDescription());
            stepDetailFragment.setVideoUrl(currentStep.getVideoURL());
            stepDetailFragment.setIngredientList(ingredientList);
            stepDetailFragment.setPreviousAndNextStep(previousStepPos, nextStepPos);
            stepDetailFragment.setStepList(stepList);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.fr_step_detail_container, stepDetailFragment)
                    .addToBackStack(null)
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
    public void onStepSelected(Step step, int previousStepPos, int nextStepPos) {

        if (step != null) {

            currentStep = step;

            String stepLabel = currentStep.getShortDescription();

            if (mTwoPane) {

                this.setTitle(stepLabel);

                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setDescription(currentStep.getDescription());
                stepDetailFragment.setVideoUrl(currentStep.getVideoURL());
                stepDetailFragment.setIngredientList(ingredientList);
                stepDetailFragment.setPreviousAndNextStep(previousStepPos, nextStepPos);
                stepDetailFragment.setStepList(stepList);

                FragmentManager fragmentManager = getSupportFragmentManager();


                fragmentManager.beginTransaction()
                        .replace(R.id.fr_step_detail_container, stepDetailFragment)
                        .addToBackStack(null)
                        .commit();

            } else {

                final Intent intent = new Intent(this, StepDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoURL", currentStep.getVideoURL());
                bundle.putString("Description", currentStep.getDescription());
                bundle.putString("stepLabel", currentStep.getShortDescription());
                bundle.putInt("previousStepPos", previousStepPos);
                bundle.putInt("nextStepPos", nextStepPos);
                Gson gson = new Gson();
                Type type_ingredient = new TypeToken <List <Ingredient>>() {
                }.getType();
                String json_ingredient = gson.toJson(ingredientList, type_ingredient);
                bundle.putString("stringIngredient", json_ingredient);
                Type type_step = new TypeToken <List <Step>>() {
                }.getType();
                String json_step_list = gson.toJson(stepList, type_step);
                bundle.putString("stepListString", json_step_list);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        }

    }

    private void notifyServiceUpdateRecipeWidget(){
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    private class SaveRecipeTask extends AsyncTask <Recipe, String, Recipe> {

        private Context aynscContext;

        public SaveRecipeTask(Context context) {
            aynscContext = context;
        }

        @Override
        protected Recipe doInBackground(Recipe... recipes) {
            RecipeDatabase recipeDatabase = RecipeDatabase.getRecipeDatabase(aynscContext);
            recipeDatabase.recipeDAO().update(recipes[0]);

            notifyServiceUpdateRecipeWidget();
            return null;
        }
    }

}