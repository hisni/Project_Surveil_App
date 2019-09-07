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

import com.aslam.co321_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etMail;
    EditText etPw;
    EditText etPwCnf;
    EditText etUserName;
    EditText etContNo;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etMail = findViewById(R.id.editTextEmailReg);
        etPw = findViewById(R.id.editTextPasswordReg);
        etPwCnf = findViewById(R.id.editTextPasswordConfirmReg);
        final Button btnSignUp = findViewById(R.id.buttonSignUpReg);
        final TextView tvLogIn = findViewById(R.id.textViewLoginReg);
        etUserName = findViewById(R.id.editTextUserNameReg);
        etContNo = findViewById(R.id.editTextPhoneReg);
        progressBar = findViewById(R.id.progressbarReg);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();
                } catch (Exception e){
                    Toast.makeText(SignUp.this, "Oops! network error!", Toast.LENGTH_SHORT).show();
                }


                if (isValidMail(etMail.getText().toString()) && isValidPW(etPw.getText().toString(), etPwCnf.getText().toString())){

                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    try{
                        mAuth.createUserWithEmailAndPassword(etMail.getText().toString(), etPw.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            progressBar.setVisibility(View.GONE);

                                            Intent intent = new Intent(SignUp.this, AddAPhoto.class);
                                            intent.putExtra("usrName", etUserName.getText().toString());
                                            intent.putExtra("phone", etContNo.getText().toString());
                                            startActivity(intent);
                                        } else {
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }catch (Exception e){
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        //go to login activity
        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
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
