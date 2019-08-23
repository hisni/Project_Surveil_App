package com.aslam.co321_project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_1 extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        final EditText etMail = findViewById(R.id.mailReg);
        final EditText etPw = findViewById(R.id.pwReg);
        final EditText etPwCnf = findViewById(R.id.pwcnfrm);
        final Button btnSignUp = findViewById(R.id.signupButton);
        final TextView tvLogIn = findViewById(R.id.hadAccount);


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
                                    } else {
                                        Toast.makeText(Register_1.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Register_1.this, "User name or password wrong", Toast.LENGTH_SHORT).show();
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

    private boolean isValidMail(String mail){
        if (mail.length()>0) return true;
        return false;
    }

    private boolean isValidPW(String pw1, String pw2){
        if (pw1.equals(pw2)){
            return true;
        }
        return false;
    }
}
