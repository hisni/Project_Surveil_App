package com.aslam.co321_project.Driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aslam.co321_project.R;

import java.util.Arrays;

public class Messages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__messages);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = findViewById(R.id.driverMsg);

        //sample messages
        final String values[] = {"8:16 am   Box 1 Temperature ok", "8:07 am   Check the box 1. *Temp"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arrays.asList(values));
        lv.setAdapter(arrayAdapter);
    }
}
