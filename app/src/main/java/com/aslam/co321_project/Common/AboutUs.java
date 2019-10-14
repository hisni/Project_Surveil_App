package com.aslam.co321_project.Common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aslam.co321_project.R;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_about_us);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
