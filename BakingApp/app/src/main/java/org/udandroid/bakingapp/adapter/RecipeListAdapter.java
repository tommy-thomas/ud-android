package org.udandroid.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.ui.StepActivity;
import org.udandroid.bakingapp.model.Recipe;

import java.lang.reflect.Type;

/**
 * Created by tommy-thomas on 3/31/18.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private Recipe[] recipes;
    private Context context;
    private final static String TAG = RecipeListAdapter.class.getSimpleName();

    public RecipeListAdapter(Context context, Recipe[] recipes){
        this.recipes = recipes;
        this.context = context;
    }


    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeListAdapter.ViewHolder viewHolder, int position) {


        if( viewHolder != null ){

            position = viewHolder.getAdapterPosition();

           if( recipes[position].getImage() != "" && recipes[position].getImage().length() > 0){
               Picasso.with(context)
                       .load(recipes[position].getImage()).into(viewHolder.ivRecipeCard);
           }

           viewHolder.tvServingCard.setText("Serving: " + recipes[position].getServings());
           viewHolder.tvRecipeCard.setText(recipes[position].getName().toString());

           viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Context context = viewHolder.itemView.getContext();
                   int i = viewHolder.getAdapterPosition();
                   Intent showRecipeSteps = new Intent(context, StepActivity.class);

                   Gson gson = new Gson();
                   Type type_recipe = new TypeToken<Recipe>() {}.getType();
                   String json_recipe = gson.toJson(recipes[i],  type_recipe);
                   showRecipeSteps.putExtra("RECIPE_EXTRA", json_recipe);

                   context.startActivity(showRecipeSteps);
               }
           });



        }

    }

    @Override
    public int getItemCount() {
        return recipes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivRecipeCard;
        private TextView tvRecipeCard;
        private TextView tvServingCard;

        public ViewHolder(View view) {
            super(view);
            ivRecipeCard = view.findViewById(R.id.iv_recipe_card);
            tvRecipeCard = view.findViewById(R.id.tv_recipe_card);
            tvServingCard = view.findViewById(R.id.tv_recipe_serving_card);
        }

    }
}
