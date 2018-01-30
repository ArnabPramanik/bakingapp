package com.arnab.android.bakingapp.Fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arnab.android.bakingapp.Adapter.RvIngredientAdapter;
import com.arnab.android.bakingapp.Model.Ingredient;
import com.arnab.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment {
    @BindView(R.id.rv_ingredient)
    RecyclerView rvIngredient;

    private RvIngredientAdapter rvIngredientAdapter;


    public IngredientsFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients,container,false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        rvIngredient.setLayoutManager(layoutManager);
        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) getArguments().getSerializable("ingredients");
        rvIngredientAdapter = new RvIngredientAdapter(ingredients);
        rvIngredient.setAdapter(rvIngredientAdapter);
        return view;
    }



    public static IngredientsFragment newObj(ArrayList<Ingredient> ingredients) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredients", ingredients);
        ingredientsFragment.setArguments(bundle);

        return ingredientsFragment;
    }
}
