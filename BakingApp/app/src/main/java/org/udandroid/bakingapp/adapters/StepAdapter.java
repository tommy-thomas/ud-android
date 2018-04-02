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
import org.udandroid.bakingapp.models.Step;

import java.util.List;

/**
 * Created by tommy-thomas on 4/1/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> steps;
    private Context context;
    private final static String TAG = StepAdapter.class.getSimpleName();

    public StepAdapter(Context context, List<Step> steps){
        this.steps = steps;
        this.context = context;
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_card, viewGroup, false);
       return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StepAdapter.ViewHolder viewHolder, int position) {

        if( viewHolder != null ){
            if( steps.get(position).getThumbnailURL() != "" && steps.get(position).getThumbnailURL().length() > 0){
                Picasso.with(context)
                        .load(steps.get(position).getThumbnailURL()).into(viewHolder.ivStepThumbnail);
            }

            viewHolder.tvStepShortDescription.setText(steps.get(position).getShortDescription().toString());

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivStepThumbnail;
        private TextView tvStepShortDescription;

        public ViewHolder(View view){
            super(view);
            ivStepThumbnail = view.findViewById(R.id.iv_step_thumbnail);
            tvStepShortDescription = view.findViewById(R.id.tv_step_short_description);
        }


    }
}
