package com.arnab.android.bakingapp;


import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arnab.android.bakingapp.Data.APIService;
import com.arnab.android.bakingapp.Fragment.IngredientsFragment;
import com.arnab.android.bakingapp.Fragment.RecipeFragment;
import com.arnab.android.bakingapp.Fragment.StepsFragment;
import com.arnab.android.bakingapp.Model.Recipe;
import com.arnab.android.bakingapp.Util.DisplayUtils;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    public static boolean mTwoPane;
    public static CountingIdlingResource idlingResource = new CountingIdlingResource("idling resource");

    TextView mErrorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMessage = (TextView)(findViewById(R.id.tv_error));
        if(DisplayUtils.isOnline(this)) {
            mErrorMessage.setVisibility(View.INVISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();

            if (findViewById(R.id.detail_layout) != null) {
                mTwoPane = true;
                if(fragmentManager.findFragmentById(R.id.master_list) == null){
                    RecipeFragment recipeFragment = new RecipeFragment();
                    fragmentManager.beginTransaction().add(R.id.master_list, recipeFragment).commit();

                }


            } else {
                mTwoPane = false;
                if(fragmentManager.findFragmentById(R.id.fragment_recipe_container) == null){
                    RecipeFragment recipeFragment = new RecipeFragment();

                    fragmentManager.beginTransaction().add(R.id.fragment_recipe_container, recipeFragment).commit();
                }

            }
        }
        else{
            mErrorMessage.setVisibility(View.VISIBLE);
        }

    }
    public IdlingResource getIdlingResource(){
        return idlingResource;
    }

}

