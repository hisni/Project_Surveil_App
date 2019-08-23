package com.aslam.co321_project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        final EditText etMail = findViewById(R.id.mail);
        final EditText etPw = findViewById(R.id.pw);
        final EditText etPwCnf = findViewById(R.id.pwcnfrm);
        final Button btnSignUp = findViewById(R.id.signupButton);

        final String mail = etMail.getText().toString();
        final String pw1 = etPw.getText().toString();
        final String pw2 = etPwCnf.getText().toString();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidMail(mail) && isValidPW(pw1, pw2)){
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();

                    //create a new user in firebase
                    mAuth.createUserWithEmailAndPassword(mail, pw1)
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
                }
            }
        });

    }

    final private boolean isValidMail(String mail){
        return true;
    }

    final private boolean isValidPW(String pw1, String pw2){
        if (pw1.equals(pw2)){
            return true;
        }

        return false;
    }
}
