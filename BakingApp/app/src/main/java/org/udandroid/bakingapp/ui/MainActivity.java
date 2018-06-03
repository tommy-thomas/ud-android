package org.udandroid.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.BuildConfig;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.udandroid.bakingapp.IdlingResource.SimpleIdlingResource;
import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapter.RecipeListAdapter;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.releasetree.BakingAppReleaseTree;
import org.udandroid.bakingapp.util.RecipeMapper;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeMapper.DelayerCallback {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Recipe[] recipes;
    private RecipeListAdapter recipeListAdapter;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Nullable
    public SimpleIdlingResource getmIdlingResource() {
        return mIdlingResource;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new FetchRecipesTask(this).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchRecipesTask(this).execute();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new BakingAppReleaseTree());
        }

        getIdlingResource();

    }

    private void loadRecipeViews() {
        TextView tvNoData = findViewById(R.id.tv_no_data);
        RecyclerView recyclerView = findViewById(R.id.rv_recipe);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        if (recipes != null && recipes.length > 0) {
            tvNoData.setVisibility(View.GONE);
        }
        recipeListAdapter = new RecipeListAdapter(getApplicationContext(), recipes);
        recyclerView.setAdapter(recipeListAdapter);
    }

    @Override
    public void onDone(Recipe[] recipes) {
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


    private class FetchRecipesTask extends AsyncTask <String, String, Recipe[]> {

        private Context aynscContext;

        public FetchRecipesTask(Context context) {
            aynscContext = context;
        }

        @Override
        protected Recipe[] doInBackground(String... strings) {

            RecipeMapper mapper = new RecipeMapper(aynscContext);
            mapper.mapData(MainActivity.this, mIdlingResource);
            recipes = mapper.recipes();
            return null;
        }


        @Override
        protected void onPostExecute(Recipe[] recipes) {
            // super.onPostExecute(recipes);
            loadRecipeViews();
            Timber.v("Recipes loaded.");
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}
