package org.udandroid.bakingapp;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.udandroid.bakingapp.adapters.RecipeAdapter;
import org.udandroid.bakingapp.helpers.RecipeMapper;
import org.udandroid.bakingapp.models.Recipe;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Recipe[] recipes;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchIngredientsTask().execute();

    }

    private void loadRecipeViews(){
        RecyclerView recyclerView = findViewById(R.id.rv_ingredient);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
        recyclerView.setAdapter(recipeAdapter);
    }

    private class FetchIngredientsTask extends AsyncTask<String, String, Recipe[]>{

        @Override
        protected Recipe[] doInBackground(String... strings) {
            RecipeMapper mapper = new RecipeMapper();
            mapper.mapData();
            recipes = mapper.recipes();
//            List<Step> stepList = recipes[1].getSteps();
//            Log.d(TAG , stepList.get(2).getDescription().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
           // super.onPostExecute(recipes);
           loadRecipeViews();
        }
    }


}
