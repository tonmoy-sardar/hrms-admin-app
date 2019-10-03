package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sft.hrmsadmin.R;

public class AttendanceCategoryActivity extends MainActivity {

    public View view;
    LinearLayout ll_conveyance, ll_report, ll_approval, ll_leave_approval, ll_attendance_summery, ll_conveyance_report, ll_leave_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_attendance_category, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("TEAM ATTENDANCE");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);

        ll_conveyance = findViewById(R.id.ll_conveyance);
        ll_report = findViewById(R.id.ll_report);
        ll_approval = findViewById(R.id.ll_approval);
        ll_leave_approval = findViewById(R.id.ll_leave_approval);
        ll_attendance_summery = findViewById(R.id.ll_attendance_summery);
        ll_conveyance_report = findViewById(R.id.ll_conveyance_report);
        ll_leave_report = findViewById(R.id.ll_leave_report);


        ll_conveyance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, ConveyanceActivity.class);
                startActivity(intent);
            }
        });

        ll_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, DailyAttendanceReportActivity.class);
                startActivity(intent);
            }
        });


        ll_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, ApprovalListActivity.class);
                startActivity(intent);
            }
        });


        ll_leave_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, LeaveApprovalListActivity.class);
                startActivity(intent);
            }
        });


        ll_attendance_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, AttendanceSummeryActivity.class);
                startActivity(intent);
            }
        });


        ll_conveyance_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, ConveyanceReportActivity.class);
                startActivity(intent);
            }
        });


        ll_leave_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceCategoryActivity.this, LeaveReportListActivity.class);
                startActivity(intent);
            }
        });
    }
}
