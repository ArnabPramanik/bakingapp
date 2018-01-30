package com.arnab.android.bakingapp.Data;

import com.arnab.android.bakingapp.Model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableOnErrorNext;
import retrofit2.http.GET;

/**
 * Created by arnab on 1/19/18.
 */

public interface APIService {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<ArrayList<Recipe>> getRecipes();
}
