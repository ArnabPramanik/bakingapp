package com.arnab.android.bakingapp;


import android.app.FragmentTransaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arnab.android.bakingapp.Adapter.ViewPagerAdapter;
import com.arnab.android.bakingapp.Fragment.IngredientsFragment;
import com.arnab.android.bakingapp.Fragment.StepsFragment;
import com.arnab.android.bakingapp.Model.Recipe;
import com.arnab.android.bakingapp.Model.Step;

import butterknife.BindView;

public class Page2Activity extends AppCompatActivity implements ActionBar.TabListener {
    private ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.container)
    public ViewPager mViewPager;

    private IngredientsFragment ingredientsFragment;
    private StepsFragment stepsFragment;
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        if (getIntent() != null && getIntent().hasExtra("recipe")) {
            recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            ingredientsFragment = IngredientsFragment.newObj(recipe.getIngredients());
            stepsFragment = StepsFragment.newObj(recipe.getSteps());
        } else {
            ingredientsFragment = (IngredientsFragment) fragmentManager
                    .getFragment(savedInstanceState, "ingredientFragment");
            stepsFragment = (StepsFragment) fragmentManager.getFragment(savedInstanceState,
                    "stepFragment");
        }
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager,ingredientsFragment,stepsFragment);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(viewPagerAdapter);


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(viewPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(android.R.id.home == id) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }




        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }


}
