package com.aslam.co321_project.Driver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aslam.co321_project.AboutUs;
import com.aslam.co321_project.Authentication.logIn;
import com.aslam.co321_project.CustomListAdapter;
import com.aslam.co321_project.R;
import com.aslam.co321_project.ViewDistribution;
import com.aslam.co321_project.Work;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Home extends AppCompatActivity {
    public static String uid;
    DatabaseReference databaseReference;

    private ArrayList<Work> deliveryList = new ArrayList<>();
    private HashMap<Integer, String> distributorIdMap;
    private HashMap<Integer, String> randomIdMap;
    private ListView myListView;
    private CustomListAdapter customListAdapter;
    private String pharmacyAddress;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__home);

        h = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {

                    case 0:
                        finish();
                        break;

                }
            }

        };

        Toolbar toolbar = findViewById(R.id.toolbarDriver);
        setSupportActionBar(toolbar);

        myListView = findViewById(R.id.lvManageDistDriver);
        getParams();

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference();

            //setlistview
            getPaths();
        } catch (Exception e){
            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Home.this, ViewDistribution.class);
                intent.putExtra("pharmacy", deliveryList.get(position).getTitle());
                intent.putExtra("pharmacyAddress", pharmacyAddress);
                intent.putExtra("pharmacyId", deliveryList.get(position).getPharmacyId());
                intent.putExtra("distributorId", deliveryList.get(position).getDistributorId());
                intent.putExtra("randomId", deliveryList.get(position).getRandomId());
                intent.putExtra("driverId", Home.uid);
                startActivity(intent);
            }
        });
    }


    private void getPaths() {
        databaseReference.child("driverTask").child(uid).addValueEventListener(new ValueEventListener() {
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
            try{
                databaseReference.child("ongoingDeliveries").child(distributorId).child(randomId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String pharmacyName = dataSnapshot.child("pharmacyName").getValue().toString();
                        final String pharmacyId = dataSnapshot.child("pharmacyId").getValue().toString();

                        databaseReference.child("pharmacies").child(pharmacyId).child("pharmacyAddress").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pharmacyAddress = dataSnapshot.getValue().toString();
                                String [] splittedAddress = pharmacyAddress.split(",");
                                String cityName = splittedAddress[splittedAddress.length-1];

                                Work work = new Work(pharmacyName, cityName, distributorId, randomId, pharmacyId);

                                deliveryList.add(work);

                                customListAdapter = new CustomListAdapter(Home.this, R.layout.simplerow, deliveryList);
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
            } catch (Exception e){
                Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the MainActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);

            builder.setMessage("Are you sure?")
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logOut();
                        }
                    })
                    .setNegativeButton("Cancel", null);

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        } else if (id == R.id.action_aboutUs) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //this function will handle the logout process
    private void logOut() {
        //logout
        FirebaseAuth.getInstance().signOut();

        //clear the cache
        try{
            File dir = Home.this.getCacheDir();
            deleteDir(dir);
        } catch (Exception e){
            e.printStackTrace();
        }

        //go to login activity
        Intent intent = new Intent(Home.this, logIn.class);
        finish();
        finishAffinity();
        startActivity(intent);
    }

    //perform cache clear
    private boolean deleteDir(File dir) {
        if(dir != null && dir.isDirectory()){
            String [] children = dir.list();

            for(String s: children){
                boolean success = deleteDir(new File(dir, s));
                if(!success){
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()){
            return dir.delete();
        }
        return false;
    }

    //get parameters from previous activity
    private void getParams() {
        uid = getIntent().getStringExtra("uid");
    }
}
