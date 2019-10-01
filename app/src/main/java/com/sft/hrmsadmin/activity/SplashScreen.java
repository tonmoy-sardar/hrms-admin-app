package com.sft.hrmsadmin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.MySharedPreferance;


public class SplashScreen extends Activity {

    MySharedPreferance mySharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        System.out.println("className======>>>" + getClass().getSimpleName());


        mySharedPreferance= new MySharedPreferance(this);
        if (mySharedPreferance.getPreferancceString(mySharedPreferance.remember_user_name).equalsIgnoreCase("")){
            startNextActivity();
        } else {
            RememberLogin();
        }
    }



    public void startNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }


    public void RememberLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }



}
