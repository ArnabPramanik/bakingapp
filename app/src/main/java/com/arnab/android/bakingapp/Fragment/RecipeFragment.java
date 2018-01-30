package com.arnab.android.bakingapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arnab.android.bakingapp.Adapter.RecipeAdapter;
import com.arnab.android.bakingapp.Data.APIService;
import com.arnab.android.bakingapp.MainActivity;
import com.arnab.android.bakingapp.Model.Ingredient;
import com.arnab.android.bakingapp.Model.Recipe;
import com.arnab.android.bakingapp.Page2Activity;
import com.arnab.android.bakingapp.R;

import com.arnab.android.bakingapp.Util.DisplayUtils;
import com.arnab.android.bakingapp.Widget.WidgetIntentService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.flowable.FlowableDoOnLifecycle;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.arnab.android.bakingapp.MainActivity.idlingResource;

/**
 * Created by arnab on 1/20/18.
 */

public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeClickListener {


    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    RecipeAdapter recipeAdapter;
    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

        rvRecipes.setLayoutManager(layoutManager);

        recipeAdapter = new RecipeAdapter(getContext(),this);

        rvRecipes.setAdapter(recipeAdapter);
        if(savedInstanceState == null)
        {

            Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                    .build();

            APIService apiService = retrofit.create(APIService.class);

            Observable<ArrayList<Recipe>> recipesObservable = apiService.getRecipes();
            idlingResource.increment();
            recipesObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseData -> {

                        for (int counter = 0; counter < responseData.size(); counter++) {
                            recipes.add(responseData.get(counter));

                        }
                        recipeAdapter.setRecipes(recipes);
                        MainActivity.idlingResource.decrement();
                    });

        }
        else{
            recipes = (ArrayList<Recipe>) savedInstanceState.getSerializable("recipes");
            recipeAdapter.setRecipes(recipes);
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("recipes",recipes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        updateWidget(recipe.getIngredients());
        if(MainActivity.mTwoPane == false){
            Intent intent = new Intent(getActivity(), Page2Activity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }
        else{
            IngredientsFragment ingredientsFragment;
            StepsFragment stepsFragment;
            ingredientsFragment = IngredientsFragment.newObj(recipe.getIngredients());
            stepsFragment = StepsFragment.newObj(recipe.getSteps());
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ingredients_fragment,ingredientsFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.steps_fragment,stepsFragment).commit();
        }
    }

    private void updateWidget(List<Ingredient> ingredientArrayList) {
        String ingredients = "Ingredient are: \n";

        int c = 0;
        for (Ingredient i : ingredientArrayList) {
            c++;
            ingredients = ingredients
                    + c + ": "
                    + i.getIngredient() + ": "
                    + i.getQuantity() + " "
                    + i.getMeasure() + "\n";
        }
        WidgetIntentService.widgetRefresh(getActivity(), ingredients);
    }


}
