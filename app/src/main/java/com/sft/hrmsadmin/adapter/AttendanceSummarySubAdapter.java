package com.sft.hrmsadmin.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendanceSummarySubAdapter extends RecyclerView.Adapter<AttendanceSummarySubAdapter.MyViewHolder> {


    Activity activity;
    ArrayList<JSONObject> attendanceRequest;

    public AttendanceSummarySubAdapter(Activity activity, ArrayList<JSONObject> attendanceRequest) {

        this.activity = activity;
        this.attendanceRequest = attendanceRequest;
    }

    @NonNull
    @Override
    public AttendanceSummarySubAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_leave_type, parent, false);

        return new AttendanceSummarySubAdapter.MyViewHolder(itemView, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceSummarySubAdapter.MyViewHolder holder, int position) {

        try {
            if (!attendanceRequest.get(position).getString("leave_type_changed_period").equalsIgnoreCase("null")){

                holder.tvLeaveType.setText(Html.fromHtml("<b>"+"Leave Type:  "+"</b>"+attendanceRequest.get(position).getString("leave_type_changed_period")));

            }else if (!attendanceRequest.get(position).getString("leave_type_changed").equalsIgnoreCase("null")){

                holder.tvLeaveType.setText(Html.fromHtml("<b>"+"Leave Type:  "+"</b>"+attendanceRequest.get(position).getString("leave_type_changed")));
                //holder.tvLeaveType.setText("Leave Type:  "+attendanceRequest.get(position).getLeaveTypeChanged());

            }else {

                if (!attendanceRequest.get(position).getString("leave_type").equalsIgnoreCase("null")) {
                    //holder.tvLeaveType.setText("Leave Type:  " + attendanceRequest.get(position).getLeaveType());
                    holder.tvLeaveType.setText(Html.fromHtml("<b>"+"Leave Type:  "+"</b>"+attendanceRequest.get(position).getString("leave_type")));
                }else {
                    //holder.tvLeaveType.setText("Leave Type:  " + "N/A");
                    holder.tvLeaveType.setText(Html.fromHtml("<b>"+"Leave Type:  "+"</b>"));

                }
            }


            if (!attendanceRequest.get(position).getString("duration").equalsIgnoreCase("null")){

                holder.tvDuration.setText(Html.fromHtml("<b>"+"Duration:  "+"</b>"+String.valueOf(attendanceRequest.get(position).getString("duration"))+" mins")  );
            }


            if (!attendanceRequest.get(position).getString("approved_status").equalsIgnoreCase("null")){

                if (attendanceRequest.get(position).getString("approved_status").equalsIgnoreCase("Pending")) {

                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.pending_yellow));
                }else if (attendanceRequest.get(position).getString("approved_status").equalsIgnoreCase("approved")){
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.approved_green));
                }else if (attendanceRequest.get(position).getString("approved_status").equalsIgnoreCase("reject")){
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.reject_red));
                }else if (attendanceRequest.get(position).getString("approved_status").equalsIgnoreCase("regular")){
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                }
                holder.tvStatus.setText(attendanceRequest.get(position).getString("approved_status"));
            }


            if (!attendanceRequest.get(position).getString("remarks").equalsIgnoreCase("null")){

                holder.rlRemarks.setVisibility(View.VISIBLE);
                holder.tvRemarks.setText(attendanceRequest.get(position).getString("remarks"));
            }else {

                holder.rlRemarks.setVisibility(View.GONE);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return attendanceRequest.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvLeaveType,tvRemarks,tvStatus,tvDuration;
        RelativeLayout rlRemarks;

        public MyViewHolder(@NonNull View itemView, Activity activity) {
            super(itemView);

            tvLeaveType = itemView.findViewById(R.id.tvLeaveType);
            tvRemarks = itemView.findViewById(R.id.tvRemarks);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            rlRemarks = itemView.findViewById(R.id.rlRemarks);
        }
    }
}
