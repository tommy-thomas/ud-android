package org.udandroid.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.udandroid.bakingapp.R;
import org.udandroid.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by tommy-thomas on 4/8/18.
 */

public class WidgetIngredientAdapter extends ArrayAdapter {

    private final List<Ingredient> ingredientList;
    private final Context  context;
    private final LayoutInflater mInflater;

    public WidgetIngredientAdapter(@NonNull  Context context, int resource, @NonNull List ingredientList) {
        super(context, resource,ingredientList);
        this.context = context;
        this.ingredientList = ingredientList;
        this.mInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.recipe_widget_ingredient_item, null);
            holder = new ViewHolder();
            holder.quantity = (TextView)convertView.findViewById(R.id.tv_ingredient_quantity);
            holder.measure = (TextView)convertView.findViewById(R.id.tv_ingredient_measure);
            holder.ingredient =  (TextView)convertView.findViewById(R.id.tv_ingredient);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Ingredient ingredient = getItem(position);
        if( ingredient != null ){
            holder.quantity.setText(ingredient.getQuantity());
            holder.measure.setText(ingredient.getMeasure());
            holder.ingredient.setText(ingredient.getIngredient());
        }
        return convertView;
    }

    @Override
    public int getCount()
    {
        return ingredientList.size();
    }


    @Override
    public Ingredient getItem(int position)
    {
        return ingredientList.get(position);
    }

    public class ViewHolder
    {
        TextView measure;
        TextView quantity;
        TextView ingredient;
    }


}
