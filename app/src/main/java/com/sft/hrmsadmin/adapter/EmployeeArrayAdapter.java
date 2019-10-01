package com.sft.hrmsadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.sft.hrmsadmin.activity.AttendanceSummeryActivity;
import com.sft.hrmsadmin.pojo_calsses.EmployeeDetail;

import java.util.ArrayList;
import java.util.List;

public class EmployeeArrayAdapter extends ArrayAdapter<EmployeeDetail> {


    public EmployeeArrayAdapter(@NonNull Context context, int resource, @NonNull List<EmployeeDetail> employeeDetails) {
        super(context, resource, employeeDetails);
    }

   /* public EmployeeArrayAdapter(Activity activity, int resource List<EmployeeDetail> emplyee) {
        super(activity,0, emplyee);
    }*/

    /*public EmployeeArrayAdapter(@NonNull Context context, @LayoutRes ArrayList<EmployeeDetail> list) {

        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }*/
}
