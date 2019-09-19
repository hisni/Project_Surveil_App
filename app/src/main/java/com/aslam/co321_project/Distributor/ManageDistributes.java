package com.aslam.co321_project.Distributor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aslam.co321_project.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.aslam.co321_project.Distributor.Home.databaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageDistributes extends Fragment {

    View view;
    ListView myListView;
    ArrayList<Work> deliveryList = new ArrayList<>();

    public ManageDistributes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_distributes, container, false);
        myListView = view.findViewById(R.id.lvManageDist);

        try {
            setListView();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    //retrieve data from firebase
    private void setListView() {

        databaseReference.child("ongoingDeliveries").child(Home.uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot deliverySnapShot : dataSnapshot.getChildren()) {
                    String pharmacy = deliverySnapShot.child("pharmacy").getValue().toString();
                    String driver = deliverySnapShot.child("driver").getValue().toString();
                    List<String> boxList = Collections.singletonList(deliverySnapShot.child("boxList").getValue().toString());

                    Work work = new Work(driver, pharmacy, boxList);

                    deliveryList.add(work);
                }

                CustomListAdapter customListAdapter = new CustomListAdapter(getContext(), R.layout.simplerow, deliveryList);
                myListView.setAdapter(customListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



