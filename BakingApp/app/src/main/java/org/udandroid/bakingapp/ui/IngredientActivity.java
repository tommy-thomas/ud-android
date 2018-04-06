package org.udandroid.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.fragments.IngredientFragment;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        this.setTitle("Ingredients");

        IngredientFragment ingredientFragment = new IngredientFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction()
                .add(R.id.fr_step_detail_container, ingredientFragment)
                .commit();


    }
}
