package org.udandroid.bakingapp.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapter.StepAdapter;
import org.udandroid.bakingapp.model.Recipe;
import org.udandroid.bakingapp.model.Step;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tommy-thomas on 4/3/18.
 */

public class MasterStepListFragment extends Fragment {

    StepClickListener stepClickListener;

    private static final String TAG = MasterStepListFragment.class.getSimpleName();

    StepAdapter stepAdapter;
    List <Step> stepList;

    // StepClickListener interface, calls methods for onclick listener and data loading
    public interface StepClickListener {

        void onStepSelected(Step step);

    }


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            stepClickListener = (StepClickListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener and LoadSteps.");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_step_master_list, container, false);

        String stringRecipe = getActivity().getIntent().getStringExtra("RECIPE_EXTRA");

        if (stringRecipe != null) {

            Gson gson = new Gson();
            Type type_recipe = new TypeToken <Recipe>() {
            }.getType();
            Recipe recipe = gson.fromJson(stringRecipe, type_recipe);
            stepList = recipe.getSteps();
            Log.d(TAG, recipe.getIngredients().get(2).getIngredient().toString());


            final RecyclerView recyclerView = rootView.findViewById(R.id.rv_step);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(layoutManager);
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            }
            stepAdapter = new StepAdapter(getContext(), stepList, stepClickListener);
            recyclerView.setAdapter(stepAdapter);


        }

        return rootView;

    }

    public MasterStepListFragment() {
    }
}
