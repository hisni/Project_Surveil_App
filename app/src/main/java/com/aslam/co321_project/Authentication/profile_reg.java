package com.aslam.co321_project.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aslam.co321_project.R;
import com.aslam.co321_project.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class profile_reg extends AppCompatActivity {

    String usrName;
    String phone;
    String type;
    Uri filePath;
    ImageView imageView;
    Spinner spinner;
    String uid;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReferenceUserInfo;
    private DatabaseReference databaseReferenceType;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_reg);

        getParams();

        try{
            mAuth = FirebaseAuth.getInstance();
        } catch (Exception e){
            Toast.makeText(profile_reg.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
        }
        
        spinner = findViewById(R.id.spinnerType);

        //click listener for camera button
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }

        });

        //click listener for button "next"
        Button btnNext = findViewById(R.id.buttonSave);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.getReference();
                } catch (Exception e){
                    Toast.makeText(profile_reg.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
                }

                type = spinner.getSelectedItem().toString();

                try {
                    uid = mAuth.getCurrentUser().getUid();
                    ulpoadOtherInfo();
                    uploadImage();
                } catch (Exception e){
                    Toast.makeText(profile_reg.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //go to distributor's shop registration
    private void distrReg() {
        Intent intent = new Intent(profile_reg.this, distrReg.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    //go to pharmacy registration
    private void pharmReg(){
        Intent intent = new Intent(profile_reg.this, pharmReg.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    //finish the registration
    private void finishActivity() {
        Intent intent = new Intent(profile_reg.this, finishReg.class);
        intent.putExtra("uid", uid);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    //choose image from the gallery
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    //to display the chosen image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //upload image to firebase
    private void uploadImage() {
        if (filePath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("userImages/"+uid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(profile_reg.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(profile_reg.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    })

                    //go to new activity after finish the operation
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(type.equals("Driver")){
                                finishActivity();
                            } else if (type.equals("Pharmacist")){
                                pharmReg();
                            } else {
                                distrReg();
                            }
                        }
                    });
        }
    }

    //upload user information to firebase
    private void ulpoadOtherInfo() {
        //user information
        databaseReferenceUserInfo = FirebaseDatabase.getInstance().getReference("userInfo");
        User user = new User(usrName, phone, type);
        databaseReferenceUserInfo.child(uid).setValue(user);

        //user type
        if(type.equals("Driver")){
            databaseReferenceType = FirebaseDatabase.getInstance().getReference("driverList");
        } else if (type.equals("Pharmacist")){
            databaseReferenceType = FirebaseDatabase.getInstance().getReference("pharmacist");
        } else {
            databaseReferenceType = FirebaseDatabase.getInstance().getReference("distributor");
        }

        databaseReferenceType.child(uid).setValue(uid);
    }

    //function to get parameters from previous activity
    private void getParams() {
        //get parameters from previous activity
        usrName = getIntent().getStringExtra("usrName");
        phone = getIntent().getStringExtra("phone");
    }
}

