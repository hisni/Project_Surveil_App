package com.aslam.co321_project.Common;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import com.aslam.co321_project.Authentication.logIn;
import com.aslam.co321_project.Driver.Home;
import com.aslam.co321_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import static com.aslam.co321_project.Driver.Home.h;

public class ViewDistribution extends AppCompatActivity {

    private String nameToDisplay;
    private String distributorId;
    private String randomId;
    private String pharmacyId;
    private String driverId;
    private String phoneLeft = "";
    private String phoneRight = "";

    private ListView listView;
    private LinkedList<String> linkedList; //boxList

    private Button buttonLeftCall;
    private Button buttonRightCall;
    private TextView textView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_view_distribution);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonLeftCall = findViewById(R.id.buttonLeftCall);
        buttonRightCall = findViewById(R.id.buttonRightCall);
        listView = findViewById(R.id.lvViewDistribution);
        textView = findViewById(R.id.tvTransInfo);

        getParams();
        toolBarHandler();
        setupTheActivity();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String boxName = linkedList.get(position);

                databaseReference.child("EndNodes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(boxName)){
                            Intent intent = new Intent(ViewDistribution.this, BoxConditions.class);
                            intent.putExtra("boxName", boxName);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ViewDistribution.this, "Sorry, this box has no data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        buttonLeftCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneLeft.length()>0){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneLeft));

                    try{
                        startActivity(callIntent);
                    } catch (Exception e){
                        Toast.makeText(ViewDistribution.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewDistribution.this, "no number found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRightCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneRight.length()>0){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneRight));

                    try{
                        startActivity(callIntent);
                    } catch (Exception e){
                        Toast.makeText(ViewDistribution.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewDistribution.this, "no number found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupTheActivity() {

        //get the box list
        databaseReference.child("ongoingDeliveries").child(distributorId).child(randomId).child("boxList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                linkedList = new LinkedList<>();
                for(DataSnapshot myDataSnapshot: dataSnapshot.getChildren()){
                    linkedList.add(myDataSnapshot.getValue().toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewDistribution.this, android.R.layout.simple_list_item_1, linkedList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(logIn.type.equals("Pharmacist")){
            getDistributorAddress();
            getDistributorPhone();
            getDriverPhone();

        } else {
            getPharmacyAddress();
            getPharmacyPhone();

            if(logIn.type.equals("Driver")){
                getDistributorPhone();
                handleDeliverButton();
            } else {
                getDriverPhone();
            }
        }

    }

    private void getPharmacyPhone() {
        databaseReference.child("userInfo").child(pharmacyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phonePharmacy = dataSnapshot.child("phone").getValue().toString();
                if(phoneRight.length()==0){
                    buttonRightCall.setText(" Pharmacy ");
                    phoneRight = phonePharmacy;
                } else {
                    buttonLeftCall.setText(" Pharmacy ");
                    phoneLeft = phonePharmacy;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDriverPhone() {
        databaseReference.child("userInfo").child(driverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phoneDriver = dataSnapshot.child("phone").getValue().toString();
                if(phoneRight.length()==0){
                    buttonRightCall.setText(" Driver ");
                    phoneRight = phoneDriver;
                } else {
                    buttonLeftCall.setText(" Driver ");
                    phoneLeft = phoneDriver;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDistributorPhone() {
        databaseReference.child("userInfo").child(distributorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phoneDistributor = dataSnapshot.child("phone").getValue().toString();
                if(phoneRight.length()==0){
                    buttonRightCall.setText(" Distributor ");
                    phoneRight = phoneDistributor;
                } else {
                    buttonLeftCall.setText(" Distributor ");
                    phoneLeft = phoneDistributor;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPharmacyAddress() {
        databaseReference.child("pharmacies").child(pharmacyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String addressToDisplay = dataSnapshot.child("pharmacyAddress").getValue().toString();
                textView.setText("This boxes are transported to\n" + nameToDisplay +"\n"+ addressToDisplay);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDistributorAddress() {
        databaseReference.child("distributors").child(distributorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String addressToDisplay = dataSnapshot.child("shopAddress").getValue().toString();
                textView.setText("This boxes are distributed by\n" + nameToDisplay +"\n"+ addressToDisplay);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handleDeliverButton() {
        Button btnDelivered = findViewById(R.id.btnDelivered);
        btnDelivered.setVisibility(View.VISIBLE);
        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewDistribution.this);

                builder.setMessage("delivered?")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                handleDelivered();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    //method to execute when delivered button is clicked
    private void handleDelivered() {
        TaskClass taskClass = new TaskClass(distributorId, randomId);
        databaseReference.child("deliveredSupplies").child("driverTask").child(Home.uid).child(randomId).setValue(taskClass);
        databaseReference.child("deliveredSupplies").child("pharmacyTask").child(pharmacyId).child(randomId).setValue(taskClass);
        databaseReference.child("deliveredSupplies").child("distributorTask").child(distributorId).child("randomId").setValue(randomId);

        databaseReference.child("pharmacyTask").child(pharmacyId).child(randomId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.child("distributorTask").child(distributorId).child(randomId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        databaseReference.child("driverTask").child(Home.uid).child(randomId).removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(ViewDistribution.this, "Success", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ViewDistribution.this, Home.class);
                                                        intent.putExtra("uid", Home.uid);
                                                        h.sendEmptyMessage(0);
                                                        finish();
                                                        startActivity(intent);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ViewDistribution.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        databaseReference.child("pharmacyTask").child(pharmacyId).child(randomId).child("randomId").setValue(randomId);
                                                        databaseReference.child("pharmacyTask").child(pharmacyId).child(randomId).child("distributorId").setValue(distributorId);
                                                        databaseReference.child("distributorTask").child(distributorId).child(randomId).child("randomId").setValue(randomId);
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ViewDistribution.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        databaseReference.child("pharmacyTask").child(pharmacyId).child(randomId).child("randomId").setValue(randomId);
                                        databaseReference.child("pharmacyTask").child(pharmacyId).child(randomId).child("distributorId").setValue(distributorId);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewDistribution.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        nameToDisplay = getIntent().getStringExtra("nameToDisplay");
        pharmacyId = getIntent().getStringExtra("pharmacyId");
        distributorId = getIntent().getStringExtra("distributorId");
        driverId = getIntent().getStringExtra("driverId");
        randomId = getIntent().getStringExtra("randomId");
    }

}
