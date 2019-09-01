package com.aslam.co321_project.Distributor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.aslam.co321_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignWork extends Fragment {


    public AssignWork() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assign_work, container, false);

        // Inflate the layout for this fragment
        return view;
    }

}
