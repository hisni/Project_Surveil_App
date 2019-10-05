package com.aslam.co321_project.Distributor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aslam.co321_project.CustomListAdapter;
import com.aslam.co321_project.R;

import com.aslam.co321_project.ViewDistribution;
import com.aslam.co321_project.Work;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.aslam.co321_project.Distributor.Home.databaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageDistributes extends Fragment {

    private View view;
    private ListView myListView;
    private CustomListAdapter customListAdapter;
    private ArrayList<Work> deliveryList = new ArrayList<>();

    public ManageDistributes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_distributes, container, false);
        myListView = view.findViewById(R.id.lvManageDist);

        //TODO: searchview
//        SearchView searchView = view.findViewById(R.id.searchBar);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(deliveryList.contains(query)){
//                    customListAdapter.getFilter().filter(query);
//                } else {
//                    Toast.makeText(getContext(), "No match found", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //customListAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });

        try {
            setListView();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ViewDistribution.class);
                intent.putExtra("pharmacy", deliveryList.get(position).getTitle());
                intent.putExtra("boxList", (Serializable) deliveryList.get(position).boxList);
                startActivity(intent);
            }
        });

        return view;
    }

    //retrieve data from firebase and set ListView
    private void setListView() {

        databaseReference.child("ongoingDeliveries").child(Home.uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot deliverySnapShot : dataSnapshot.getChildren()) {
                    String pharmacyName = deliverySnapShot.child("pharmacyName").getValue().toString();
                    String driverName = deliverySnapShot.child("driverName").getValue().toString();
                    List<String> boxList = Collections.singletonList(deliverySnapShot.child("boxList").getValue().toString());

                    Work work = new Work(pharmacyName, driverName, boxList);

                    deliveryList.add(work);
                }

                customListAdapter = new CustomListAdapter(getContext(), R.layout.simplerow, deliveryList);
                myListView.setAdapter(customListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



