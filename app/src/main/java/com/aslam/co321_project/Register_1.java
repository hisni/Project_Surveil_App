package com.aslam.co321_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_1 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etMail;
    EditText etPw;
    EditText etPwCnf;
    EditText etUserName;
    EditText etContNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        etMail = findViewById(R.id.mailReg);
        etPw = findViewById(R.id.pwReg);
        etPwCnf = findViewById(R.id.pwcnfrm);
        final Button btnSignUp = findViewById(R.id.signupButton);
        final TextView tvLogIn = findViewById(R.id.hadAccount);
        etUserName = findViewById(R.id.usrname);
        etContNo = findViewById(R.id.phone);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();

                if (isValidMail(etMail.getText().toString()) && isValidPW(etPw.getText().toString(), etPwCnf.getText().toString())){
                    mAuth.createUserWithEmailAndPassword(etMail.getText().toString(), etPw.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register_1.this, "Successfully registered", Toast.LENGTH_SHORT).show();

                                        Intent intProfilePhoto = new Intent(Register_1.this, profile_reg.class);
                                        intProfilePhoto.putExtra("usrName", etUserName.getText().toString());
                                        intProfilePhoto.putExtra("phone", etContNo.getText().toString());
                                        startActivity(intProfilePhoto);
                                    } else {
                                        Toast.makeText(Register_1.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }


            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private boolean isValidMail(String email){
        if (email.isEmpty()) {
            etMail.setError("Email is required");
            etMail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etMail.setError("Please enter a valid email");
            etMail.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidPW(String password, String password2){
        if (password.isEmpty()) {
            etPw.setError("Password is required");
            etPw.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPw.setError("Minimum lenght of password should be 6");
            etPw.requestFocus();
            return false;
        }

        if (password2.isEmpty()) {
            etPwCnf.setError("Please confirm the password");
            etPwCnf.requestFocus();
            return false;
        }

        if (password2.length() < 6) {
            etPwCnf.setError("Minimum lenght of password should be 6");
            etPwCnf.requestFocus();
            return false;
        }

        if (!password.equals(password2)){
            etPw.setError("Password should be same");
            etPw.requestFocus();
            etPwCnf.setError("Password should be same");
            etPwCnf.requestFocus();
            return false;
        }

        return true;
    }
}
