package com.aslam.co321_project.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aslam.co321_project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DistributorShopDetail extends AppCompatActivity {
    EditText etDistName;
    EditText etDistPhone;
    EditText etDistAddress;

    String uid;
    String shopName;
    String shopPhone;
    String shopAddress;

    private DatabaseReference databaseReferenceUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_distributor_shop_detail);

        getParams();

        etDistName = findViewById(R.id.etDistributorName);
        etDistPhone = findViewById(R.id.etDistributorPhone);
        etDistAddress = findViewById(R.id.etDistributorAddress);

        Button btnAdd = findViewById(R.id.buttonAddShop);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopName = etDistName.getText().toString();
                shopPhone = etDistPhone.getText().toString();
                shopAddress = etDistAddress.getText().toString();
                try{
                    ulpoadDistributorInfo();
                } catch (Exception e){
                    Toast.makeText(DistributorShopDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //upload distributor information to firebase
    private void ulpoadDistributorInfo() {
        //user information
        databaseReferenceUserInfo = FirebaseDatabase.getInstance().getReference("distributors");
        Distributor distributor = new Distributor(shopName, shopPhone, shopAddress);
        databaseReferenceUserInfo.child(uid).setValue(distributor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finishActivity();
                    }
                });
    }

    //finish the registration
    private void finishActivity() {
        Intent intent = new Intent(DistributorShopDetail.this, finishReg.class);
        intent.putExtra("uid", uid);
        intent.putExtra("type", "distributor");
        startActivity(intent);
    }

    //function to get parameters from previous activity
    private void getParams() {
        //get parameters from previous activity
        uid = getIntent().getStringExtra("uid");
    }
}
