package com.aslam.co321_project.Pharmacist;


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

import com.aslam.co321_project.Common.CustomListAdapter;
import com.aslam.co321_project.R;
import com.aslam.co321_project.Common.ViewDistribution;
import com.aslam.co321_project.Common.DeliverDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.aslam.co321_project.Pharmacist.MainActivity.databaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPharmacistHome extends Fragment {

    private ArrayList<DeliverDetails> deliveryList = new ArrayList<>();
    private HashMap<Integer, String> distributorIdMap;
    private HashMap<Integer, String> randomIdMap;

    private CustomListAdapter customListAdapter;

    private ListView myListView;

    private String driverId;

    public FragmentPharmacistHome() {
        // Required empty public constructor
    }

    private void getPaths() {
        databaseReference.child("pharmacyTask").child(MainActivity.uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                distributorIdMap = new HashMap<>();
                randomIdMap = new HashMap<>();
                int i = 0;
                for (DataSnapshot deliverySnapShot : dataSnapshot.getChildren()) {
                    String distributorId = deliverySnapShot.child("distributorId").getValue().toString();
                    String randomId = deliverySnapShot.child("randomId").getValue().toString();
                    distributorIdMap.put(i, distributorId);
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

        for (int i = 0; i<randomIdMap.size(); i++){
            final String distributorId = distributorIdMap.get(i);
            final String randomId = randomIdMap.get(i);

            databaseReference.child("distributors").child(distributorId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String distributorName = dataSnapshot.child("shopName").getValue().toString();

                    databaseReference.child("ongoingDeliveries").child(distributorId).child(randomId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String driverName = dataSnapshot.child("driverName").getValue().toString();
                            driverId = dataSnapshot.child("driverId").getValue().toString();

                            DeliverDetails deliverDetails = new DeliverDetails(distributorName, driverName, distributorId, driverId, randomId);

                            deliveryList.add(deliverDetails);

                            customListAdapter = new CustomListAdapter(getContext(), R.layout.simplerow, deliveryList);
                            myListView.setAdapter(customListAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacist_home, container, false);

        myListView = view.findViewById(R.id.lvCommonListView);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference();

            //setlistview
            getPaths();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ViewDistribution.class);
                intent.putExtra("nameToDisplay", deliveryList.get(position).getTitle());
                intent.putExtra("pharmacyId", MainActivity.uid);
                intent.putExtra("distributorId", deliveryList.get(position).getLeftId());
                intent.putExtra("randomId", deliveryList.get(position).getRandomId());
                intent.putExtra("driverId", deliveryList.get(position).getRightId());
                startActivity(intent);
            }
        });
        return view;
    }

}
