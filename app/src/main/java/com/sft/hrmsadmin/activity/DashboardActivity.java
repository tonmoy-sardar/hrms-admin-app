package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sft.hrmsadmin.R;

public class DashboardActivity extends MainActivity {

    public View view;
    LinearLayout ll_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_dashboard, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("HRMS DASHBOARD");

        ll_attendance = findViewById(R.id.ll_attendance);

        ll_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,AttendanceCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
