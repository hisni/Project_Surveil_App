package com.aslam.co321_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class add_duties extends AppCompatActivity {

    String uid;
    private DatabaseReference databaseReference;
    EditText etShopName;
    EditText etShopAddress;
    EditText etShopPhone;
    Button btnAdd;

    String shopName, shopPhone, shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_duties);

        etShopName = findViewById(R.id.shopName);
        etShopAddress = findViewById(R.id.shopAddress);
        etShopPhone = findViewById(R.id.shopPhone);
        btnAdd = findViewById(R.id.addBtn);


        getParams();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopName=etShopName.getText().toString();
                shopAddress=etShopAddress.getText().toString();
                shopPhone=etShopPhone.getText().toString();
                String id=UUID.randomUUID().toString();

                try{
                    databaseReference = FirebaseDatabase.getInstance().getReference("driverTask");
                    Delivery delivery = new Delivery(shopName, shopAddress, shopPhone, id);
                    databaseReference.child(id).setValue(delivery);

                    Toast.makeText(add_duties.this, "Added", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(add_duties.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //function to get parameters from previous activity
    private void getParams() {
        //get parameters from previous activity
        uid = getIntent().getStringExtra("uid");
    }
}
