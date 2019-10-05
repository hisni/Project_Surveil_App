package com.aslam.co321_project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ViewDistribution extends AppCompatActivity {

    private String pharmacyName;
    private String city;
    private String phoneDriver = "";
    private String phonePharmacy = "";
    private ArrayList<String> boxList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_distribution);

        getParams();
        toolBarHandler();

        String temp = boxList.get(0);
        temp = temp.replace("[","");
        temp = temp.replace("]","");
        temp = temp.replaceAll(",","");

        String [] splitted = temp.split("\\s+");

        Button btnCallDriver = findViewById(R.id.buttonCallDriver);
        Button btnCallPharm = findViewById(R.id.buttonCallPharm);
        ListView listView = findViewById(R.id.lvViewDistribution);
        TextView textView = findViewById(R.id.tvTransInfo);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewDistribution.this, android.R.layout.simple_list_item_1, splitted);
        listView.setAdapter(adapter);

        btnCallDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phoneDriver.length()>0){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(phoneDriver));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(ViewDistribution.this, "no number found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCallPharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneDriver.length()>0){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(phonePharmacy));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(ViewDistribution.this, "no number found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView.setText("This boxes are transported to\n" +pharmacyName +" - "+ city);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewDistribution.this, BoxConditions.class);
                //TODO: change the box name
                intent.putExtra("boxName", "en1001");
                startActivity(intent);
            }
        });
    }

    private void toolBarHandler() {
        Toolbar toolbar = findViewById(R.id.toolbarBoxStatus);
        toolbar.setTitle("Box list");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //get parameters from previous activity
    private void getParams() {
        pharmacyName = getIntent().getStringExtra("pharmacy");
        boxList = (ArrayList<String>) getIntent().getSerializableExtra("boxList");
    }

}
