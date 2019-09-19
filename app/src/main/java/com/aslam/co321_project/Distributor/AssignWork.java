package com.aslam.co321_project.Distributor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aslam.co321_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//get the database reference from home
import static com.aslam.co321_project.Distributor.Home.databaseReference;

/**
 * A simple {@link Fragment} subclass.
 */

//TODO: this activity has some bugs
public class AssignWork extends Fragment {

    DatabaseReference userInfoReference;
    View view;
    String driverName;
    Spinner driverSpinner, pharmacySpinner;
    List<String> driverIds;


    public AssignWork() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        userInfoReference = databaseReference.child("userInfo");

        try {
            setDriverSpinner();
            setPharmacySpinner();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_assign_work, container, false);

        final EditText etBoxes = view.findViewById(R.id.etAsnWrkBoxes);
        final Button btnAdd = view.findViewById(R.id.btnAsnWrk);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String boxes = etBoxes.getText().toString();
                if (boxes.isEmpty()){
                    etBoxes.setError("at least one box is required");
                    etBoxes.requestFocus();
                } else {
                    String [] splittedBoxArray = boxes.split("\\s+");
                    List<String> splittedBoxList = Arrays.asList(splittedBoxArray);

                    try {
                        uploadData(splittedBoxList);
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setDriverSpinner() {
        databaseReference.child("drivers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                driverIds = new ArrayList<>();

                for(DataSnapshot driverSnapShot: dataSnapshot.getChildren()){

                    String driverId = driverSnapShot.child("uid").getValue(String.class);

                    userInfoReference.child(driverId).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            driverName = dataSnapshot.getValue(String.class);

                            //we have wait until data is get retrieved
                            try {
                                driverIds.add(driverName);
                            } catch (Exception e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            driverSpinner = view.findViewById(R.id.spinnerSelectDriver);
                            ArrayAdapter<String> driverAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, driverIds);
                            driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            driverSpinner.setAdapter(driverAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("", "error while retrieving the name: "+databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setPharmacySpinner() {
        databaseReference.child("pharmacies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> pharmacyIds = new ArrayList<>();

                for(DataSnapshot pharmacySnapShot: dataSnapshot.getChildren()){
                    String pharmacyId = pharmacySnapShot.child("pharmacyName").getValue(String.class);
                    pharmacyIds.add(pharmacyId);
                }

                pharmacySpinner = view.findViewById(R.id.spinnerSelectPharmacy);
                ArrayAdapter<String> pharmacyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, pharmacyIds);
                pharmacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pharmacySpinner.setAdapter(pharmacyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadData(List splittedBoxList) {
        String selectedDriver = driverSpinner.getSelectedItem().toString();
        String selectedPharmacy = pharmacySpinner.getSelectedItem().toString();

        Work work = new Work(selectedDriver, selectedPharmacy, splittedBoxList);

        databaseReference.child("ongoingDeliveries/"+ Home.uid+"/").child(UUID.randomUUID().toString()).setValue(work)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                });
    }

}
