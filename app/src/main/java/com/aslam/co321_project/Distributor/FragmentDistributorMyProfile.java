package com.aslam.co321_project.Distributor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aslam.co321_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDistributorMyProfile extends Fragment {


    public FragmentDistributorMyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_distributor_my_profile, container, false);
    }

}
