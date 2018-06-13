package org.udandroid.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.service.IngredientWidgetService;
import org.udandroid.bakingapp.util.SaveRecipeTask;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import timber.log.Timber;

public class IngredientWidgetConfig extends AppCompatActivity {

    private Recipe[] recipeList;
    private RecipeListReceiver recipeListReceiver;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_widget_config);
        setResult(RESULT_CANCELED);

        registerRecipeListReceiver();

        IngredientWidgetService.startActionGetRecipeList(this);

        spinner = findViewById(R.id.bakingAppWidgetSetup);


//        setUpWidget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleSetupWidget();
//            }
//        });
    }

    private void handleSetupWidget() {
        showAppWidget();
    }

    int appWidgetId;

    private void showAppWidget() {
        appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

        //Retrieve the App Widget ID from the Intent that launched the Activity//

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            //If the intent doesn’t have a widget ID, then call finish()//

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }

            //TO DO, Perform the configuration and get an instance of the AppWidgetManager//

            //Create the return intent//
            Intent resultValue = new Intent();

            //Pass the original appWidgetId//\
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            //Set the results from the configuration Activity//
            setResult(RESULT_OK, resultValue);

            //Finish the Activity//
            finish();
        }
    }

    private void registerRecipeListReceiver(){
        recipeListReceiver = new IngredientWidgetConfig.RecipeListReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IngredientWidgetService.ACTION_GET_RECIPE_LIST);
        this.registerReceiver(recipeListReceiver, intentFilter);
    }

    private class RecipeListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stringRecipeList = intent.getStringExtra("recipe-list");
            if( stringRecipeList != null && stringRecipeList != ""){
                Gson gson = new Gson();
                Type type = new TypeToken<Recipe[]>() {
                }.getType();
                recipeList = gson.fromJson(stringRecipeList, type);

                ArrayList recipeNames = new ArrayList();
                final HashMap<String , Recipe> hmRecipes = new HashMap<String, Recipe>();

                recipeNames.add("--select a recipe--");
                for(int i=0; i< recipeList.length; i++){
                    recipeNames.add( recipeList[i].getName() );
                    hmRecipes.put(  recipeList[i].getName() ,  recipeList[i]);
                }

                ArrayAdapter<Recipe> adapter =
                        new ArrayAdapter<Recipe>(context,  android.R.layout.simple_spinner_dropdown_item, recipeNames);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
//                        Toast.makeText(parent.getContext(),
//                                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                                Toast.LENGTH_SHORT).show();
                        if(!parent.getItemAtPosition(position).toString().equals("--select a recipe--")) {
                            new SaveRecipeTask(getApplicationContext()).execute(hmRecipes.get(parent.getItemAtPosition(position).toString()));
                            handleSetupWidget();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView <?> parent) {

                    }
                });

                Timber.v("Data received.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRecipeListener();
    }

    private void unregisterRecipeListener(){
        if( recipeListReceiver !=  null){
            this.unregisterReceiver(recipeListReceiver);
        }
    }
}
