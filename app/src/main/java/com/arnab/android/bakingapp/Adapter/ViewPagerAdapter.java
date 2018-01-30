package com.arnab.android.bakingapp.Adapter;





import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arnab.android.bakingapp.Fragment.IngredientsFragment;
import com.arnab.android.bakingapp.Fragment.StepsFragment;

/**
 * Created by arnab on 1/23/18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private IngredientsFragment ingredientsFragment;
    private StepsFragment stepsFragment;
    public ViewPagerAdapter(FragmentManager fm,IngredientsFragment ingredientsFragment,StepsFragment stepsFragment) {
        super(fm);
        this.ingredientsFragment = ingredientsFragment;
        this.stepsFragment = stepsFragment;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ingredientsFragment;
            case 1:
                return stepsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ingredients";
            case 1:
                return "Steps";
        }
        return null;
    }
}
