package com.arnab.android.bakingapp.Adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arnab.android.bakingapp.Model.Recipe;
import com.arnab.android.bakingapp.R;
import com.arnab.android.bakingapp.Util.DisplayUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arnab on 1/20/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> implements View.OnClickListener{
    private ArrayList<Recipe> recipes;
    private RecipeClickListener clickListener;
    private Context mContext;

    public RecipeAdapter( Context context, RecipeClickListener clickListener){

        mContext = context;
        this.clickListener = clickListener;

    }

    @Override
    public void onClick(View v) {

    }

    public interface RecipeClickListener {
        void onRecipeItemClick(Recipe review);
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.tvTitle.setText(recipe.getName());
        holder.tvServing.setText(String.valueOf(recipe.getServings()));

        if(!recipe.getImage().isEmpty()){

           Picasso.with(mContext).load(recipe.getImage()).placeholder(R.drawable.ic_room_service_black_24dp).into(holder.ivThumbnail);
        }




    }



    @Override
    public int getItemCount() {
        if(recipes == null){
            return 0;
        }
        else{
            return recipes.size();
        }

    }

    public void setRecipes(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.iv_thumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tv_title)
        TextView tvTitle;


        @BindView(R.id.tv_serving)
        TextView tvServing;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onRecipeItemClick(recipes.get(getAdapterPosition()));

        }
    }


}
