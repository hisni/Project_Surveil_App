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

import java.util.ArrayList;
import java.util.HashMap;

import static com.aslam.co321_project.Distributor.Home.databaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageDistributes extends Fragment {

    private View view;
    private ListView myListView;
    private CustomListAdapter customListAdapter;
    private String pharmacyAddress;
    private ArrayList<Work> deliveryList = new ArrayList<>();
    private HashMap<Integer, String> randomIdMap;

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
            getPaths();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String pharmacyId = deliveryList.get(position).getPharmacyId();
                databaseReference.child("pharmacies").child(pharmacyId).child("pharmacyAddress").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pharmacyAddress = dataSnapshot.getValue().toString();

                        Intent intent = new Intent(getContext(), ViewDistribution.class);
                        intent.putExtra("pharmacy", deliveryList.get(position).getTitle());
                        intent.putExtra("pharmacyAddress", pharmacyAddress);
                        intent.putExtra("pharmacyId", pharmacyId);
                        intent.putExtra("distributorId", deliveryList.get(position).getDistributorId());
                        intent.putExtra("randomId", deliveryList.get(position).getRandomId());
                        intent.putExtra("driverId", deliveryList.get(position).getDriverId());
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }

    private void getPaths() {
        databaseReference.child("distributorTask").child(Home.uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                randomIdMap = new HashMap<>();
                int i = 0;
                for (DataSnapshot deliverySnapShot : dataSnapshot.getChildren()) {
                    String randomId = deliverySnapShot.child("randomId").getValue().toString();
                    randomIdMap.put(i, randomId);
                    i++;
                }
                setListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //retrieve data from firebase and set ListView
    private void setListView() {
        final String distributorId = Home.uid;
        for(int i = 0; i<randomIdMap.size(); i++){
            final String randomId = randomIdMap.get(i);
            databaseReference.child("ongoingDeliveries").child(distributorId).child(randomId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String pharmacyName = dataSnapshot.child("pharmacyName").getValue().toString();
                    final String pharmacyId = dataSnapshot.child("pharmacyId").getValue().toString();
                    String driverName = dataSnapshot.child("driverName").getValue().toString();
                    String driverId = dataSnapshot.child("driverId").getValue().toString();
                    Work work = new Work(pharmacyName, driverName, distributorId, randomId, pharmacyId);

                    deliveryList.add(work);

                    customListAdapter = new CustomListAdapter(getContext(), R.layout.simplerow, deliveryList);
                    myListView.setAdapter(customListAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}



