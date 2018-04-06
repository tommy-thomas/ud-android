package org.udandroid.bakingapp.fragments;

import android.content.Context;
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
    IngredientClickListener ingredientClickListener;


    public IngredientFragment() {
    }

    // IngredientClickListener interface, calls methods for onclick listener and data loading
    public interface IngredientClickListener {

        void onIngredientClicked();

    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {

           ingredientClickListener = (IngredientClickListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener and LoadSteps.");
        }
    }

    public void setIngredientList(List<Ingredient> ingredientList){
        ingredientList = ingredientList;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);


        if (ingredientList != null) {
            Gson gson = new Gson();
            Type type_ingredient = new TypeToken <List <Ingredient>>() {
            }.getType();
            Log.d(TAG, ingredientList.get(2).getIngredient().toString());

            final RecyclerView recyclerView = rootView.findViewById(R.id.rv_ingredient);
            recyclerView.setHasFixedSize(true);
            ingredientAdapter = new IngredientAdapter(getContext(), ingredientList);
            recyclerView.setAdapter(ingredientAdapter);
        }




        return rootView;

    }
}
