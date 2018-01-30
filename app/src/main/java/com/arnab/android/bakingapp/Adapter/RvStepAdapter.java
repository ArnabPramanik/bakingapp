package com.arnab.android.bakingapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arnab.android.bakingapp.Model.Step;
import com.arnab.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arnab on 1/23/18.
 */

public class RvStepAdapter extends RecyclerView.Adapter<RvStepAdapter.StepAdapterViewHolder> {


    private ArrayList<Step> steps;
    private StepsClickListener clickListener;

    public RvStepAdapter(ArrayList<Step> steps, StepsClickListener clickListener) {
        this.steps = steps;
        this.clickListener = clickListener;
    }

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);

        return new RvStepAdapter.StepAdapterViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        Step item = steps.get(position);

        holder.tvStepDesc.setText(item.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public interface StepsClickListener {
        void onStepsItemClick(Step step);
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.linear_step)
        LinearLayout linearStep;
        @BindView(R.id.tv_step_desc)
        TextView tvStepDesc;


        StepAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvStepDesc.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onStepsItemClick(steps.get(getAdapterPosition()));
        }
    }
}
