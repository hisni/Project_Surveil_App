package com.aslam.co321_project;

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

    private String pharmacyName;
    private String pharmacyAddress;
    private String phoneDriverOrDistributor = "";
    private String phonePharmacy = "";
    private String distributorId;
    private String randomId;
    private String pharmacyId;
    private String driverId;
    private ListView listView;
    private LinkedList<String> linkedList; //boxList
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_distribution);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        getParams();
        toolBarHandler();
        retrieveUserInfo();

        Button btnCallDriver = findViewById(R.id.buttonCallDriver);
        Button btnCallPharm = findViewById(R.id.buttonCallPharm);
        Button btnDelivered = findViewById(R.id.btnDelivered);
        listView = findViewById(R.id.lvViewDistribution);
        TextView textView = findViewById(R.id.tvTransInfo);


        if (logIn.type.equals("Driver")){
            handleSpecialDriverFunctions(btnDelivered, btnCallDriver);
        }

        btnCallDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneDriverOrDistributor.length()>0){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneDriverOrDistributor));

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

        btnCallPharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phonePharmacy.length()>0){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phonePharmacy));

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

        textView.setText("This boxes are transported to\n" +pharmacyName +"\n"+ pharmacyAddress);

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
    }

    private void retrieveUserInfo() {

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


        //get the distributor's or driver's phone no
        if(logIn.type.equals("Driver")){
            databaseReference.child("userInfo").child(distributorId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    phoneDriverOrDistributor = dataSnapshot.child("phone").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            databaseReference.child("userInfo").child(driverId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    phoneDriverOrDistributor = dataSnapshot.child("phone").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //get the pharmacy's phone no
        databaseReference.child("userInfo").child(pharmacyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phonePharmacy = dataSnapshot.child("phone").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void handleSpecialDriverFunctions(Button btn, Button btnCall) {
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
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

        btnCall.setText(" Distributor ");
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
        pharmacyName = getIntent().getStringExtra("pharmacy");
        pharmacyId = getIntent().getStringExtra("pharmacyId");
        pharmacyAddress = getIntent().getStringExtra("pharmacyAddress");
        distributorId = getIntent().getStringExtra("distributorId");
        driverId = getIntent().getStringExtra("driverId");
        randomId = getIntent().getStringExtra("randomId");
    }

}
