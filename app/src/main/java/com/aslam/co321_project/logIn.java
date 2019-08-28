package com.aslam.co321_project;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText usernameEditText;
    EditText passwordEditText;
    String uid;
    String type;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        final Button loginButton = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressbar);

        ////////////////////////////////////////////////////////////////////////////////////////////
        final TextView registerBtn = findViewById(R.id.textViewSignup);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logIn.this, Register_1.class);
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

                if (isValiMail(usernameEditText.getText().toString()) && isValidPW(passwordEditText.getText().toString())){

                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Task<AuthResult> login_successfull = mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        progressBar.setVisibility(View.GONE);

                                        try{
                                            //get user id
                                            uid = mAuth.getCurrentUser().getUid();
                                        } catch (Exception e){
                                            Toast.makeText(logIn.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
                                        }

                                        setType();
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

    //TODO have to implement this function for get the type of the user from database
    private void setType(){
        type="Driver";
    }

    private boolean isValiMail(String email){
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

    private boolean isValidPW(String password){
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Minimum lenght of password should be 6");
            passwordEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void startApp(){
        //find the type of user and start the app
        if(type.equals("Driver")){
            startDriver();
        } else if (type.equals("Pharmacist")){
            startPharmacist();
        } else {
            startDistributor();
        }
    }

    //start app for driver
    private void startDriver() {
        Intent intent = new Intent(logIn.this, Driver_Home.class);
        intent.putExtra("uid", uid);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //start app for pharmacist
    private void startPharmacist() {
        Intent intent = new Intent(logIn.this, Pharmacist_Home.class);
        intent.putExtra("uid", uid);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //start app for distributor
    private void startDistributor() {
        Intent intent = new Intent(logIn.this, Distributor_home.class);
        intent.putExtra("uid", uid);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
