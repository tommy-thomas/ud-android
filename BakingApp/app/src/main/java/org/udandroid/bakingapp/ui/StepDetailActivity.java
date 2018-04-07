package org.udandroid.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.fragments.StepDetailFragment;
import org.udandroid.bakingapp.models.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class StepDetailActivity extends AppCompatActivity {

    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step_detail);
        this.setTitle(getIntent().getStringExtra("stepLabel"));
        String description = getIntent().getStringExtra("Description");
        String videoURL = getIntent().getStringExtra("videoURL");

        String stringIngredient = getIntent().getStringExtra("stringIngredient");
            if (stringIngredient != null) {
                Gson gson = new Gson();
                Type type_ingredient = new TypeToken<List<Ingredient>>() {
                }.getType();
                ingredientList = gson.fromJson(stringIngredient, type_ingredient);
            }


        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setShowStepDetail(true);
        stepDetailFragment.setDescription(description);
        stepDetailFragment.setVideoUrl(videoURL);
        if( ingredientList != null ){
            stepDetailFragment.setIngredientList( ingredientList );
            stepDetailFragment.setShowStepDetail(false);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
               .add(R.id.fr_step_detail_container, stepDetailFragment)
                .commit();

    }


}
