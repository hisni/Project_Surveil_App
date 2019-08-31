package com.aslam.co321_project.Driver;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aslam.co321_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Customer extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Intent intent = getIntent();

        final String name = intent.getStringExtra("name");
        final String address = intent.getStringExtra("address");
        final String phone = intent.getStringExtra("phone");
        final String id = intent.getStringExtra("id");

        final TextView tvName = findViewById(R.id.shopName);
        final TextView tvAddress = findViewById(R.id.shopAddress);

        final Button btnDeleverd = findViewById(R.id.button3);
        final Button btnCall = findViewById(R.id.button);


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phone));
                startActivity(callIntent);
            }
        });

        btnDeleverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Customer.this);

                builder.setMessage("Are you sure?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteShop(id);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        tvName.setText(name);
        tvAddress.setText(address);

    }

    private void deleteShop(String id) {
        try{
            databaseReference = FirebaseDatabase.getInstance().getReference("driverTask");
            databaseReference.child(id).removeValue();

            Toast.makeText(Customer.this, "Pharmacy removed", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(Customer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
