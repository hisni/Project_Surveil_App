package com.aslam.co321_project.Pharmacist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aslam.co321_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPhrmacistPast extends Fragment {


    public FragmentPhrmacistPast() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phrmacist_past, container, false);
    }

}
