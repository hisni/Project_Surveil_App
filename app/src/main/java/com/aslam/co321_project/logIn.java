package com.aslam.co321_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        ////////////////////////////////////////////////////////////////////////////////////////////
        final TextView registerBtn = findViewById(R.id.register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logIn.this, Register_1.class);
                startActivity(intent);
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValiMail(usernameEditText.getText().toString()) && isValidPW(passwordEditText.getText().toString())){

                    loadingProgressBar.setVisibility(View.VISIBLE);

                    Task<AuthResult> login_successfull = mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loadingProgressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(logIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                        startApp(usernameEditText.getText().toString());
                                    } else {
                                        Toast.makeText(logIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });
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

    private void startApp(String user){
        //find the type of user and start the app

        Intent intentDriver = new Intent(this, Driver_Home.class);
        intentDriver.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentDriver);
        finish();
    }
}
