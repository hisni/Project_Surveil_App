package com.aslam.co321_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.aslam.co321_project.Authentication.logIn;
import com.aslam.co321_project.Distributor.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String type;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check whether already logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent = new Intent(MainActivity.this, com.aslam.co321_project.Authentication.logIn.class);
            startActivity(intent);
            finish();
        } else {
            uid = user.getUid();
            startApp();
        }
    }

    //find the type of user and start the app
    private void startApp(){
        try{
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference().child("userInfo").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    type = dataSnapshot.child("type").getValue().toString();
                    logIn.type = type;
                    if(type.equals("Driver")){
                        startDriver();
                    } else if (type.equals("Pharmacist")){
                        startPharmacist();
                    } else {
                        startDistributor();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    //start app for driver
    private void startDriver() {
        Intent intent = new Intent(MainActivity.this, com.aslam.co321_project.Driver.Home.class);
        intent.putExtra("uid", uid);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //start app for pharmacist
    private void startPharmacist() {
        Intent intent = new Intent(MainActivity.this, com.aslam.co321_project.Pharmacist.Home.class);
        intent.putExtra("uid", uid);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //start app for distributor
    private void startDistributor() {
        Intent intent = new Intent(MainActivity.this, Home.class);
        intent.putExtra("uid", uid);intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
