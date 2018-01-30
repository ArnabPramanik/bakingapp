package com.arnab.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arnab.android.bakingapp.Model.Ingredient;
import com.arnab.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arnab on 1/24/18.
 */

public class RvIngredientAdapter extends RecyclerView.Adapter<RvIngredientAdapter.IngredientAdapterViewHolder>{

    private ArrayList<Ingredient> ingredients ;


    public RvIngredientAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;

    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);



        return new RvIngredientAdapter.IngredientAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {

        Ingredient item = ingredients.get(position);
        holder.tvIngredient.setText(item.getIngredient());
        String quantity = String.valueOf(item.getQuantity()) + " " + item.getMeasure();

        holder.tvQuantity.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_ingredient)
        TextView tvIngredient;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;

        IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

