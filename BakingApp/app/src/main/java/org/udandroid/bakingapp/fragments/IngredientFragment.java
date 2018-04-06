package org.udandroid.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.adapters.IngredientAdapter;
import org.udandroid.bakingapp.models.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tommy-thomas on 4/6/18.
 */

public class IngredientFragment extends Fragment {

    List <Ingredient> ingredientList;
    private final String TAG = IngredientFragment.class.getSimpleName();
    IngredientAdapter ingredientAdapter;

    public IngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        String stringIngredient = getActivity().getIntent().getStringExtra("INGREDIENT_EXTRA");

        if (stringIngredient != null) {
            Gson gson = new Gson();
            Type type_ingredient = new TypeToken <List <Ingredient>>() {
            }.getType();
            ingredientList = gson.fromJson(stringIngredient, type_ingredient);
            Log.d(TAG, ingredientList.get(2).getIngredient().toString());

            final RecyclerView recyclerView = rootView.findViewById(R.id.rv_ingredient);
            recyclerView.setHasFixedSize(true);
            ingredientAdapter = new IngredientAdapter(getContext(), ingredientList);
            recyclerView.setAdapter(ingredientAdapter);
        }


        return rootView;

    }
}
