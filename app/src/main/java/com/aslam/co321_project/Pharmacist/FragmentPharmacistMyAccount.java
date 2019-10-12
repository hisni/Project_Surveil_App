package com.aslam.co321_project.Pharmacist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aslam.co321_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.aslam.co321_project.Pharmacist.MainActivity.databaseReference;
import static com.aslam.co321_project.Pharmacist.MainActivity.uid;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPharmacistMyAccount extends Fragment {

    private TextView tvUserName;
    private TextView tvInfoUserName;
    private TextView tvEmail;
    private TextView tvAddress;
    private TextView tvMobile;
    private TextView tvShopName;
    private TextView tvShopMobile;
    private TextView tvShopAddress;

    public FragmentPharmacistMyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharmacist_my_account, container, false);
        tvUserName = view.findViewById(R.id.profile_username_pharmacist);
        tvInfoUserName = view.findViewById(R.id.info_name_pharmacist);
        tvEmail = view.findViewById(R.id.info_email_pharmacist);
        tvAddress = view.findViewById(R.id.info_address_pharmacist);
        tvMobile = view.findViewById(R.id.info_mobile_pharmacist);
        tvShopName = view.findViewById(R.id.shop_name_pharmacist);
        tvShopMobile = view.findViewById(R.id.shop_mobile_pharmacist);
        tvShopAddress = view.findViewById(R.id.shop_address_pharmacist);

        setBasicData();
        setShopData();
        return view;
    }

    private void setShopData() {
        databaseReference.child("pharmacies").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("pharmacyName").getValue().toString();
                String phone = dataSnapshot.child("pharmacyPhone").getValue().toString();
                String address = dataSnapshot.child("pharmacyAddress").getValue().toString();

                tvShopName.setText(name);
                tvShopMobile.setText(phone);
                tvShopAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setBasicData() {
        databaseReference.child("userInfo").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();

                tvUserName.setText(name);
                tvInfoUserName.setText(name);
                tvMobile.setText(phone);
                tvAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        tvEmail.setText(email);
    }

}
