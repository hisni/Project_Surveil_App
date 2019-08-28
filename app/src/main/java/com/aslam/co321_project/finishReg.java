package com.aslam.co321_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class finishReg extends AppCompatActivity {

    Button btnFinish;
    String uid;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_reg);

        btnFinish = findViewById(R.id.finishBtn);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        Intent intent = new Intent(finishReg.this, Driver_Home.class);
        intent.putExtra("uid", uid);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    //start app for pharmacist
    private void startPharmacist() {
        Intent intent = new Intent(finishReg.this, Pharmacist_Home.class);
        intent.putExtra("uid", uid);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finishAffinity();
        startActivity(intent);
    }

    //start app for distributor
    private void startDistributor() {
        Intent intent = new Intent(finishReg.this, Distributor_home.class);
        intent.putExtra("uid", uid);
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
