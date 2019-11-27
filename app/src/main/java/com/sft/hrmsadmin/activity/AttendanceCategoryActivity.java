package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.sft.hrmsadmin.R;

public class AttendanceCategoryActivity extends MainActivity {

    public View view;
    LinearLayout ll_conveyance, ll_report, ll_approval, ll_leave_approval, ll_attendance_summery, ll_conveyance_report, ll_leave_report,ll_attendance_leave_section,
            ll_conveyance_section,ll_daily_summery_section,ll_leave_conveyance;
    CardView cv_approvals,cv_reports;

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
        cv_approvals = findViewById(R.id.cv_approvals);
        cv_reports = findViewById(R.id.cv_reports);
        ll_attendance_leave_section = findViewById(R.id.ll_attendance_leave_section);
        ll_conveyance_section = findViewById(R.id.ll_conveyance_section);
        ll_daily_summery_section = findViewById(R.id.ll_daily_summery_section);
        ll_leave_conveyance = findViewById(R.id.ll_leave_conveyance);


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

        setAnimation(cv_approvals);
        setAnimation(cv_reports);
        setAnimation(ll_attendance_leave_section);
        setAnimation(ll_daily_summery_section);
        setAnimation(ll_conveyance_section);
        setAnimation(ll_leave_conveyance);
    }


    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(AttendanceCategoryActivity.this, R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    private void setAnimationEnterFromLeft(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(AttendanceCategoryActivity.this, R.anim.item_animaion_from_left);
        viewToAnimate.startAnimation(animation);
    }


    private void setAnimationEnterFromRight(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(AttendanceCategoryActivity.this, R.anim.item_animaion_from_right);
        viewToAnimate.startAnimation(animation);
    }
}
