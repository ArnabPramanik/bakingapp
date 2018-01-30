package com.arnab.android.bakingapp;

import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arnab.android.bakingapp.Fragment.Step2Fragment;
import com.arnab.android.bakingapp.Model.Step;
import com.arnab.android.bakingapp.Util.DisplayUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.id;

public class Step2Activity extends AppCompatActivity {


    private Step2Fragment step2fragment;
    private Bundle savedInstanceState;
    private ArrayList<Step> steps;
    private int currentStep;

    @BindView(R.id.tv_prev)
    TextView tvPrev;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.layout_step)
    LinearLayout layoutStepNav;
    @BindView(R.id.v_horizontal)
    View vHorizontal;

    private void setNavigationButton(int stepIndex) {
        if (steps.size() > 1) {
            if (stepIndex == 0) {

                tvNext.setText(steps.get(stepIndex + 1).getShortDescription());
                tvPrev.setVisibility(View.INVISIBLE);
            } else if (stepIndex == steps.size() - 1) {

                tvPrev.setText(steps.get(stepIndex - 1).getShortDescription());
                tvNext.setVisibility(View.INVISIBLE);
            } else {
                tvNext.setVisibility(View.VISIBLE);
                tvPrev.setVisibility(View.VISIBLE);
                tvNext.setText(steps.get(stepIndex + 1).getShortDescription());
                tvPrev.setText(steps.get(stepIndex - 1).getShortDescription());
            }
        } else {
            tvPrev.setVisibility(View.GONE);
            tvNext.setVisibility(View.GONE);
        }
    }

    private void fragmentSet(Bundle savedInstanceState, int stepIndex) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(steps.get(stepIndex).getShortDescription());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        if (savedInstanceState != null) {
            step2fragment = (Step2Fragment) getSupportFragmentManager().getFragment(savedInstanceState, "step2Fragment");
        } else {
            step2fragment = Step2Fragment.newObj(steps.get(stepIndex));
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rl_container, step2fragment)
                .commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_step2);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra("steps") &&
                getIntent().hasExtra("currentStep")) {
            steps = (ArrayList<Step>) getIntent().getSerializableExtra("steps");
            if (savedInstanceState == null) {
                currentStep = getIntent().getIntExtra("currentStep", 0);
            } else {
                currentStep = savedInstanceState.getInt("currentStep");
            }
            setNavigationButton(currentStep);
        }

        fragmentSet(savedInstanceState, currentStep);
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (step2fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "step2Fragment", step2fragment);
        }
        outState.putInt("currentStep", currentStep);
        super.onSaveInstanceState(outState);
    }




    @OnClick(R.id.tv_next)
    public void Next() {
        currentStep ++;
        fragmentSet(savedInstanceState, currentStep);
        setNavigationButton(currentStep);
    }

    @OnClick(R.id.tv_prev)
    public void Previous() {
        currentStep --;
        fragmentSet(savedInstanceState, currentStep);
        setNavigationButton(currentStep);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(android.R.id.home == id) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
