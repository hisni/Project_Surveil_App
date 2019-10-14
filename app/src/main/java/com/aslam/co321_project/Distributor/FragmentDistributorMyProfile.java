package com.aslam.co321_project.Distributor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aslam.co321_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.aslam.co321_project.Distributor.MainActivity.databaseReference;
import static com.aslam.co321_project.Distributor.MainActivity.uid;
/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDistributorMyProfile extends Fragment {

    private TextView tvUserName;
    private TextView tvInfoUserName;
    private TextView tvEmail;
    private TextView tvAddress;
    private TextView tvMobile;
    private TextView tvShopName;
    private TextView tvShopMobile;
    private TextView tvShopAddress;

    public FragmentDistributorMyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distributor_my_profile, container, false);
        tvUserName = view.findViewById(R.id.profile_username_distributor);
        tvInfoUserName = view.findViewById(R.id.info_name_distributor);
        tvEmail = view.findViewById(R.id.info_email_distributor);
        tvAddress = view.findViewById(R.id.info_address_distributor);
        tvMobile = view.findViewById(R.id.info_mobile_distributor);
        tvShopName = view.findViewById(R.id.shop_name_distributor);
        tvShopMobile = view.findViewById(R.id.shop_mobile_distributor);
        tvShopAddress = view.findViewById(R.id.shop_address_distributor);
        
        setBasicData();
        setShopData();
//        handleEdits(view);
        return view;
    }

    private void setShopData() {
        databaseReference.child("distributors").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("shopName").getValue().toString();
                String phone = dataSnapshot.child("shopPhone").getValue().toString();
                String address = dataSnapshot.child("shopAddress").getValue().toString();

                tvShopName.setText(name);
                tvShopMobile.setText(phone);
                tvShopAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void handleEdits(View view) {
//        final EditText etName = view.findViewById(R.id.etDistributorName);
//        EditText etMobile = view.findViewById(R.id.etDistributorPhone);
//        EditText etAddress = view.findViewById(R.id.etDistributorAddress);
//
//        Button update = view.findViewById(R.id.btnUpdate);
//
//        etName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(etName.isCursorVisible()){
//                    tvInfoUserName.setVisibility(View.GONE);
//                    etName.setVisibility(View.VISIBLE);
//                } else {
//                    tvInfoUserName.setVisibility(View.VISIBLE);
//                    etName.setVisibility(View.GONE);
//                }
//            }
//        });
//    }

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
