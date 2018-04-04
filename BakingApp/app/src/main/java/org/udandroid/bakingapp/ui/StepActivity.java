package org.udandroid.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapters.StepAdapter;
import org.udandroid.bakingapp.fragments.MasterStepListFragment;
import org.udandroid.bakingapp.fragments.RecipeDetailFragment;
import org.udandroid.bakingapp.models.Ingredient;
import org.udandroid.bakingapp.models.Step;

import java.util.List;

public class StepActivity extends AppCompatActivity implements MasterStepListFragment.StepClickListener {

    private StepAdapter stepAdapter;
    public List <Step> stepList;
    private List <Ingredient> ingredientList;
    private final static String TAG = StepActivity.class.getSimpleName();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (findViewById(R.id.fragment_step_detail) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

    }

    @Override
    public void onStepSelected(Step step) {

        if (step != null) {

            String stepLabel = "Step " + String.valueOf(step.getId());

            if (mTwoPane) {

                this.setTitle(stepLabel);

                RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                recipeDetailFragment.setDescription(step.getDescription());
                recipeDetailFragment.setVideoUrl(step.getVideoURL());

                FragmentManager fragmentManager = getSupportFragmentManager();


                fragmentManager.beginTransaction()
                        .add(R.id.fragment_step_detail, recipeDetailFragment)
                        .commit();

            } else {

                final Intent intent = new Intent(this, StepDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoURL", step.getVideoURL());
                bundle.putString("Description", step.getDescription());
                bundle.putString("stepLabel", stepLabel);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        }

    }
}
