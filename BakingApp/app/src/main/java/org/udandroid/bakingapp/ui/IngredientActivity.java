package org.udandroid.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.fragments.IngredientFragment;
import org.udandroid.bakingapp.models.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private final String TAG = IngredientActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        this.setTitle("Ingredients");

        String stringIngredient = getIntent().getStringExtra("stringIngredient");

        IngredientFragment ingredientFragment = new IngredientFragment();


        if( stringIngredient != null){
            Gson gson = new Gson();
            Type type_ingredient = new TypeToken<List<Ingredient>>() {
            }.getType();
            List<Ingredient> ingredientList = gson.fromJson(stringIngredient, type_ingredient);

            Log.d(TAG , ingredientList.get(0).getIngredient().toString());

            ingredientFragment.setIngredientList( ingredientList );

        }

        FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction()
                .add(R.id.fr_ingredient_detail_container, ingredientFragment)
                .commit();


    }
}
