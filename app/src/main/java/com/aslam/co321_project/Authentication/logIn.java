package com.aslam.co321_project.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aslam.co321_project.Distributor.MainActivity;
import com.aslam.co321_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class logIn extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText usernameEditText;
    EditText passwordEditText;
    String uid;
    public static String type;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_log_in);

        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        final Button loginButton = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressbar);

        ////////////////////////////////////////////////////////////////////////////////////////////
        final TextView registerBtn = findViewById(R.id.textViewSignup);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                Intent intent = new Intent(logIn.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slid_out_up);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();
                }  catch (Exception e){
                    Toast.makeText(logIn.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
                }

                if (isMailValid(usernameEditText.getText().toString()) && isPwValid(passwordEditText.getText().toString())){

                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Task<AuthResult> login_successfull = mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        try{
                                            //get user id
                                            uid = mAuth.getCurrentUser().getUid();
                                        } catch (Exception e){
                                            Toast.makeText(logIn.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
                                        }

                                        startApp();
                                    } else {
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(logIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    private boolean isMailValid(String email){
        if (email.isEmpty()) {
            usernameEditText.setError("Email is required");
            usernameEditText.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usernameEditText.setError("Please enter a valid email");
            usernameEditText.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isPwValid(String password){
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Minimum length of password should be 6");
            passwordEditText.requestFocus();
            return false;
        }

        return true;
    }

    //find the type of user and start the app
    private void startApp(){

        try{
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference().child("userInfo").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBar.setVisibility(View.GONE);

                    type = dataSnapshot.child("type").getValue().toString();

                    if(type.equals("Driver")){
                        startDriver();
                    } else if (type.equals("Pharmacist")){
                        startPharmacist();
                    } else {
                        startDistributor();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    //start app for driver
    private void startDriver() {
        Intent intent = new Intent(logIn.this, com.aslam.co321_project.Driver.MainActivity.class);
        intent.putExtra("uid", uid);
//        intent.putExtra("email", usernameEditText.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //start app for pharmacist
    private void startPharmacist() {
        Intent intent = new Intent(logIn.this, com.aslam.co321_project.Pharmacist.MainActivity.class);
        intent.putExtra("uid", uid);
//        intent.putExtra("email", usernameEditText.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //start app for distributor
    private void startDistributor() {
        Intent intent = new Intent(logIn.this, MainActivity.class);
        intent.putExtra("uid", uid);
//        intent.putExtra("email", usernameEditText.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
