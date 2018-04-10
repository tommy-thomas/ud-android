package org.udandroid.bakingapp.ui;

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
import org.udandroid.bakingapp.fragment.StepDetailFragment;
import org.udandroid.bakingapp.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class StepDetailActivity extends AppCompatActivity {

    private List <Ingredient> ingredientList;
    private IngredientListAdapter ingredientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step_detail);

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

        this.setTitle(getIntent().getStringExtra("stepLabel"));
        String description = getIntent().getStringExtra("Description");
        String videoURL = getIntent().getStringExtra("videoURL");

        String stringIngredient = getIntent().getStringExtra("stringIngredient");
        if (stringIngredient != null) {
            Gson gson = new Gson();
            Type type_ingredient = new TypeToken <List <Ingredient>>() {
            }.getType();
            ingredientList = gson.fromJson(stringIngredient, type_ingredient);
        }


        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setShowStepDetail(true);
        stepDetailFragment.setDescription(description);
        stepDetailFragment.setVideoUrl(videoURL);
//        if( ingredientList != null ){
//            stepDetailFragment.setIngredientList( ingredientList );
//            stepDetailFragment.setShowStepDetail(false);
//        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fr_step_detail_container, stepDetailFragment)
                .commit();


        final RecyclerView recyclerView = findViewById(R.id.rv_ingredient);
        recyclerView.setHasFixedSize(true);
        ingredientListAdapter = new IngredientListAdapter(this, ingredientList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ingredientListAdapter);

    }


}
