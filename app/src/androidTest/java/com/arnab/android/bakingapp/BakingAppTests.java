package com.arnab.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.view.View;

import com.arnab.android.bakingapp.Fragment.RecipeFragment;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;


/**
 * Created by arnab on 1/28/18.
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppTests {


    private IdlingResource mIdlingResource;
        @Rule
        public ActivityTestRule<MainActivity> mActivityTestRule =
                new ActivityTestRule<>(MainActivity.class);





    @Test
    public void checkButton() {
        if(mActivityTestRule.getActivity().mTwoPane == true)
        {
            Espresso.registerIdlingResources(mActivityTestRule.getActivity().getIdlingResource());
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rv_recipes),
                            isDisplayed()));
            recyclerView.perform(actionOnItemAtPosition(0, click()));


            onView(withId(R.id.rv_step)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));



        }
        else{
            Espresso.registerIdlingResources(mActivityTestRule.getActivity().getIdlingResource());
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rv_recipes),
                            isDisplayed()));
            recyclerView.perform(actionOnItemAtPosition(0, click()));


            onView(withText("Steps")).perform(click());
            onView(withId(R.id.rv_step)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            for(int counter = 1; counter <= 8 ; counter ++){
                if(counter == 1){
                    onView(withId(R.id.tv_prev)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
                }
                if (counter == 8) {
                    onView(withId(R.id.tv_next)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

                }

                if(counter < 7){
                    onView(withId(R.id.tv_next)).perform(click());
                }
            }

        }

    }


}



