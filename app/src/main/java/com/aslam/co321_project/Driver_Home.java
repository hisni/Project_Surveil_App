package com.aslam.co321_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver_Home extends AppCompatActivity {

    ListView lv;
    String uid;
    StorageReference storageReference;
    private DatabaseReference databaseReferenceUserInfo;
    ImageView imageView;
    Toolbar toolbar;

    DatabaseReference databaseReference;
    List<Delivery> shopList;

//    // Reference to an image file in Firebase Storage
//    StorageReference storageReference = ...;
//
//    // ImageView in your Activity
//    ImageView imageView = ...;
//
//// Load the image using Glide
//Glide.with(this /* context */)
//        .using(new FirebaseImageLoader())
//            .load(storageReference)
//        .into(imageView);
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.assignDriverTask:
                addDuty();
                return true;
            case  R.id.readings:
                goToReadings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToReadings() {
        Intent intent = new Intent(Driver_Home.this, graph.class);
        startActivity(intent);
    }

    private void addDuty() {
        Intent intent = new Intent(Driver_Home.this, add_duties.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                shopList.clear();
                for(DataSnapshot shopSnapShot: dataSnapshot.getChildren()){
                    Delivery shop = shopSnapShot.getValue(Delivery.class);

                    shopList.add(shop);
                }

                ShopList adapter = new ShopList(Driver_Home.this, shopList);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView3);

        getParams();
//        setProfilePicture();
//        try{
//            setUserName();
//        }catch (Exception e){
//            Toast.makeText(Driver_Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

        databaseReference = FirebaseDatabase.getInstance().getReference("driverTask");


        lv = findViewById(R.id.deliveries_LV);

        shopList= new ArrayList<>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvName = view.findViewById(R.id.tvName);
                TextView tvAddress = view.findViewById(R.id.tvAddress);
                TextView tvPhone = view.findViewById(R.id.tvPhone);
                TextView tvId = view.findViewById(R.id.tvId);

                Intent intent = new Intent(Driver_Home.this, Customer.class);

                intent.putExtra("name", tvName.getText().toString());
                intent.putExtra("phone", tvPhone.getText().toString());
                intent.putExtra("address", tvAddress.getText().toString());
                intent.putExtra("id", tvId.getText().toString());

                startActivity(intent);
            }
        });



        //message part
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Driver_Home.this, Driver_Messages.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void setUserName() {
        databaseReferenceUserInfo = FirebaseDatabase.getInstance().getReference("userInfo");
        databaseReference.child(uid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toolbar.setTitle(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//
//    private void setProfilePicture() {
//        StorageReference ref = storageReference.child("userImages/"+uid);
//        Glide.with(Driver_Home.this)
//        .using(new FirebaseImageLoader())
//            .load(storageReference)
//        .into(imageView);
//    }

    //get parameters from previous activity
    private void getParams() {
        uid = getIntent().getStringExtra("uid");
    }

}
