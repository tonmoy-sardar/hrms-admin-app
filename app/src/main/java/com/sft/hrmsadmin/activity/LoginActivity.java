package com.sft.hrmsadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.MethodUtils;


public class LoginActivity extends AppCompatActivity {

    TextView tvForgotPWD;
    Button btLogin;
    EditText etEmail, etPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MethodUtils.setStickyBar(LoginActivity.this);
        System.out.println("className======>>>" + getClass().getSimpleName());

        tvForgotPWD = findViewById(R.id.tvForgotPWD);
        btLogin = findViewById(R.id.btLogin);
        etEmail = findViewById(R.id.etEmail);
        etPWD = findViewById(R.id.etPWD);

        tvForgotPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
