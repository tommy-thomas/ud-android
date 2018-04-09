package org.udandroid.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by tommy-thomas on 4/6/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredientList;
    private Context context;
    private final String TAG = IngredientAdapter.class.getSimpleName();


    public IngredientAdapter(Context context, List<Ingredient> ingredients){
        this.ingredientList = ingredients;
        this.context = context;
    }

    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_detail, viewGroup, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientAdapter.ViewHolder viewHolder, final int position) {

        if( viewHolder != null ){

            viewHolder.tvQuantity.setText( ingredientList.get(position).getQuantity());
            viewHolder.tvMeasure.setText( ingredientList.get(position).getMeasure());
            viewHolder.tvIngredient.setText( ingredientList.get(position).getIngredient());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvQuantity;
        private TextView tvMeasure;
        private TextView tvIngredient;

        public ViewHolder(View view) {
            super(view);
            tvIngredient = view.findViewById(R.id.tv_ingredient);
            tvMeasure = view.findViewById(R.id.tv_ingredient_measure);
            tvQuantity = view.findViewById(R.id.tv_ingredient_quantity);
        }

    }
}
