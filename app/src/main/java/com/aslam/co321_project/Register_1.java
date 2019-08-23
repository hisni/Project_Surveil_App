package com.aslam.co321_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Register_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        final EditText etMail = findViewById(R.id.mail);
        final EditText etPw = findViewById(R.id.pw);
        final EditText etPwCnf = findViewById(R.id.pwcnfrm);


        if (isValidMail(etMail.getText().toString()) && isValidPW(etPw.getText().toString(), etPwCnf.getText().toString())){

        }
    }

    final boolean isValidMail(String mail){
        return true;
    }

    final boolean isValidPW(String pw1, String pw2){
        if (pw1.equals(pw2)){
            return true;
        }

        return false;
    }
}
