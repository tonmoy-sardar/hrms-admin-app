package com.sft.hrmsadmin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sft.hrmsadmin.R;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        System.out.println("className======>>>" + getClass().getSimpleName());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                finish();

            }
        }, 2000);
    }
}
