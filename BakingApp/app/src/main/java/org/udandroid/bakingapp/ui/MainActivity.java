package org.udandroid.bakingapp.ui;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapters.RecipeListAdapter;
import org.udandroid.bakingapp.helpers.RecipeMapper;
import org.udandroid.bakingapp.models.Recipe;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Recipe[] recipes;
    private RecipeListAdapter recipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchRecipesTask().execute();

    }

    private void loadRecipeViews(){
        RecyclerView recyclerView = findViewById(R.id.rv_recipe);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        recipeListAdapter = new RecipeListAdapter(getApplicationContext(), recipes);
        recyclerView.setAdapter(recipeListAdapter);
    }

    private class FetchRecipesTask extends AsyncTask<String, String, Recipe[]>{

        @Override
        protected Recipe[] doInBackground(String... strings) {
            RecipeMapper mapper = new RecipeMapper();
            mapper.mapData();
            recipes = mapper.recipes();
            return null;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
           // super.onPostExecute(recipes);
           loadRecipeViews();
        }
    }


}
