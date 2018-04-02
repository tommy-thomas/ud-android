package org.udandroid.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.udandroid.bakingapp.fragments.RecipeStepFragment;

public class StepDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        this.setTitle(getIntent().getStringExtra("stepLabel"));
        String description = getIntent().getStringExtra("Description");
        String videoURL = getIntent().getStringExtra("videoURL");

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setDescription(description);
        recipeStepFragment.setVideoUrl(videoURL);

        FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction()
               .add(R.id.fr_step_detail_container, recipeStepFragment)
                .commit();

    }


}
