package org.udandroid.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.models.Recipe;

/**
 * Created by tommy-thomas on 3/31/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Recipe[] recipes;
    private Context context;
    private final static String TAG = RecipeAdapter.class.getSimpleName();

    public RecipeAdapter(Context context, Recipe[] recipes){
        this.recipes = recipes;
        this.context = context;
    }


    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.ViewHolder viewHolder, int position) {


        if( viewHolder != null ){

            position = viewHolder.getAdapterPosition();

           if( recipes[position].getImage() != "" && recipes[position].getImage().length() > 0){
               Picasso.with(context)
                       .load(recipes[position].getImage()).into(viewHolder.ivIngredientCard);
           }

           viewHolder.tvIngredientCard.setText(recipes[position].getName().toString());

        }

    }

    @Override
    public int getItemCount() {
        return recipes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivIngredientCard;
        private TextView tvIngredientCard;

        public ViewHolder(View view) {
            super(view);
            ivIngredientCard = view.findViewById(R.id.iv_ingredient_card);
            tvIngredientCard = view.findViewById(R.id.tv_ingredient_card);
        }

    }
}
