package com.arnab.android.bakingapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arnab.android.bakingapp.Adapter.RvStepAdapter;
import com.arnab.android.bakingapp.MainActivity;
import com.arnab.android.bakingapp.Model.Step;
import com.arnab.android.bakingapp.R;
import com.arnab.android.bakingapp.Step2Activity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment implements RvStepAdapter.StepsClickListener{

    ArrayList<Step> steps;

    @BindView(R.id.rv_step)
    RecyclerView rvStep;

    private RvStepAdapter rvStepAdapter;

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            steps = (ArrayList<Step>) getArguments().getSerializable("steps");

        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvStep.setLayoutManager(layoutManager);
        rvStepAdapter = new RvStepAdapter(steps, this);
        rvStep.setAdapter(rvStepAdapter);
        return view;
    }

    @Override
    public void onStepsItemClick(Step step) {
        if(MainActivity.mTwoPane){
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.video_fragment, Step2Fragment.newObj(step))
                    .commit();
        }
        else{
            Intent intent = new Intent(getActivity(), Step2Activity.class);
            intent.putExtra("steps", steps);
            intent.putExtra("currentStep", steps.indexOf(step));
            startActivity(intent);
        }

    }



    public static StepsFragment newObj(ArrayList<Step> steps) {
        StepsFragment stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("steps", steps);
        stepsFragment.setArguments(bundle);

        return stepsFragment;
    }

}
