package com.aslam.co321_project.Distributor;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentTransaction;
import android.view.FrameStats;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.aslam.co321_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageDistributes extends Fragment {

    View view;

    public ManageDistributes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_distributes, container, false);
        handleListView(view);

        return view;
    }


    //list view in manage distribution
    private void handleListView(View view) {
        String [] items = {"1st", "2nd", "3rd", "2nd", "3rd", "2nd", "3rd", "2nd", "3rd", "2nd", "3rd", "2nd", "3rd"};

        ListView listView = view.findViewById(R.id.lvManageDist);

        ArrayAdapter<String> lvAdapter = new ArrayAdapter<String>(getActivity(), R.layout.show_drivers, items);

        listView.setAdapter(lvAdapter);
    }

}
