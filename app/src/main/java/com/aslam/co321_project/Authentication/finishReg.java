package com.aslam.co321_project.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aslam.co321_project.Distributor.Home;
import com.aslam.co321_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class finishReg extends AppCompatActivity {

    Button btnFinish;
    String uid;
    String type;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_reg);

        btnFinish = findViewById(R.id.finishBtn);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                } catch (Exception e){
                    email = "look at finish reg";
                }
                getParams();
                startApp();
            }
        });
    }

    private void startApp() {
        if(type.equals("Driver")){
            startDriver();
        } else if (type.equals("Pharmacist")){
            startPharmacist();
        } else {
            startDistributor();
        }
    }

    //start app for driver
    private void startDriver() {
        Intent intent = new Intent(finishReg.this, com.aslam.co321_project.Driver.Home.class);
        intent.putExtra("uid", uid);
        intent.putExtra("email", email);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    //start app for pharmacist
    private void startPharmacist() {
        Intent intent = new Intent(finishReg.this, com.aslam.co321_project.Pharmacist.Home.class);
        intent.putExtra("uid", uid);
        intent.putExtra("email", email);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finishAffinity();
        startActivity(intent);
    }

    //start app for distributor
    private void startDistributor() {
        Intent intent = new Intent(finishReg.this, Home.class);
        intent.putExtra("uid", uid);
        intent.putExtra("email", email);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finishAffinity();
        startActivity(intent);
    }

    //get parameters from previous activity
    private void getParams() {
        uid = getIntent().getStringExtra("uid");
        type = getIntent().getStringExtra("type");
    }
}
