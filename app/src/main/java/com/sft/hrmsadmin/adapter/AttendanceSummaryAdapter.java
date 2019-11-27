package com.sft.hrmsadmin.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.activity.AttendanceSummeryActivity;
import com.sft.hrmsadmin.activity.ConveyanceDetails;
import com.sft.hrmsadmin.utils.GetFormatDateTime;
import com.sft.hrmsadmin.utils.MethodUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class AttendanceSummaryAdapter extends RecyclerView.Adapter<AttendanceSummaryAdapter.MyViewHolder> {


    Activity activity;
    ArrayList<JSONObject> arrayList_attendance_summery;
    AttendanceSummarySubAdapter attendanceSummarySubAdapter;

    ArrayList<JSONObject> arrayList_attendance_request;
    ArrayList<JSONObject> conveyanceDetailList = new ArrayList<>();

    private int lastPosition = -1;


    public AttendanceSummaryAdapter(Activity activity, ArrayList<JSONObject> arrayList_attendance_summery) {

        this.activity = activity;
        this.arrayList_attendance_summery = arrayList_attendance_summery;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_attendance_summary, parent, false);

        return new AttendanceSummaryAdapter.MyViewHolder(itemView, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        try {
            holder.tvGrace.setText(Html.fromHtml("<b>"+"Grace: "+"</b>"+
                    String.valueOf(arrayList_attendance_summery.get(position).getString("daily_grace"))+" mins"));

            holder.tvDate.setText(MethodUtils.profileDate(arrayList_attendance_summery.get(position).getString("date")));
            holder.tv_al_date.setText(MethodUtils.profileDate(arrayList_attendance_summery.get(position).getString("date")));






            if (!arrayList_attendance_summery.get(position).getString("login_time").equalsIgnoreCase("null")){

                holder.tvLoginTime.setText(Html.fromHtml("<b>" + "Log In: " + "</b>" +arrayList_attendance_summery.get(position).getString("login_time")
                        .substring(arrayList_attendance_summery.get(position).getString("login_time").indexOf("T") + 1)));
            }else {


            }


            if (!arrayList_attendance_summery.get(position).getString("logout_time").equalsIgnoreCase("null")){
                holder.tvLogoutTime.setText(Html.fromHtml("<b>" + "Log Out: " + "</b>" +arrayList_attendance_summery.get(position).getString("logout_time")
                        .substring(arrayList_attendance_summery.get(position).getString("logout_time").indexOf("T") + 1)));

            }


            if (!arrayList_attendance_summery.get(position).getString("day_remarks").equalsIgnoreCase("null")) {
                holder.tvRemarks.setText(Html.fromHtml("<b>" + "Remarks: " + "</b>" + arrayList_attendance_summery.get(position).getString("day_remarks")));
            }



            if (!arrayList_attendance_summery.get(position).getString("daily_leave_type").equalsIgnoreCase("null")) {
                if (arrayList_attendance_summery.get(position).getString("daily_leave_type").equalsIgnoreCase("cl")) {

                    if (!arrayList_attendance_summery.get(position).getString("daily_leave_period").equalsIgnoreCase("null")) {
                        holder.tvCL.setText("CL: " + String.valueOf(arrayList_attendance_summery.get(position).getString("daily_leave_period")));
                    }

                } else if (arrayList_attendance_summery.get(position).getString("daily_leave_type").equalsIgnoreCase("el")) {
                    if (!arrayList_attendance_summery.get(position).getString("daily_leave_period").equalsIgnoreCase("null")) {
                        holder.tvEL.setText("EL: " + String.valueOf(arrayList_attendance_summery.get(position).getString("daily_leave_period")));
                    }

                } else if (arrayList_attendance_summery.get(position).getString("daily_leave_type").equalsIgnoreCase("sl")) {
                    if (!arrayList_attendance_summery.get(position).getString("daily_leave_period").equalsIgnoreCase("null")) {
                        holder.tvSL.setText("SL: " + String.valueOf(arrayList_attendance_summery.get(position).getString("daily_leave_period")));
                    }

                } else if (arrayList_attendance_summery.get(position).getString("daily_leave_type").equalsIgnoreCase("ab")) {
                    if (!arrayList_attendance_summery.get(position).getString("daily_leave_period").equalsIgnoreCase("null")) {
                        holder.tvAbsent.setText("Absent: " + String.valueOf(arrayList_attendance_summery.get(position).getString("daily_leave_period")));
                    }
                }
            }






            if (arrayList_attendance_summery.get(position).getJSONArray("attendance_request").length() > 0){

                holder.llLeaveDetails.setVisibility(View.VISIBLE);


                holder.llLeaveDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (holder.rvLeaveType.getVisibility() == View.VISIBLE){

                            holder.rvLeaveType.setVisibility(View.GONE);
                            holder.ivArrowup.setVisibility(View.GONE);
                            holder.ivArrowDown.setVisibility(View.VISIBLE);
                        }else {

                            holder.rvLeaveType.setVisibility(View.VISIBLE);
                            holder.ivArrowup.setVisibility(View.VISIBLE);
                            holder.ivArrowDown.setVisibility(View.GONE);
                        }
                    }
                });


                /*JSONArray attendance_request = arrayList_attendance_summery.get(position).getJSONArray("attendance_request");

                for (int i = 0; i < attendance_request.length(); i++){


                    arrayList_attendance_request.add(attendance_request.getJSONObject(i));
                }

                attendanceSummarySubAdapter = new AttendanceSummarySubAdapter(activity, arrayList_attendance_request);

                //monthsAdapter.setOnItemClickListener(this);
                LinearLayoutManager linearLayoutManager =
                        new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                holder.rvLeaveType.setHasFixedSize(true);
                holder.rvLeaveType.setLayoutManager(linearLayoutManager);
                holder.rvLeaveType.setAdapter(attendanceSummarySubAdapter);*/



                arrayList_attendance_request = new ArrayList<JSONObject>();
                for (int i = 0; i < arrayList_attendance_summery.get(position).getJSONArray("attendance_request").length(); i++) {
                    arrayList_attendance_request.add( arrayList_attendance_summery.get(position).getJSONArray("attendance_request").getJSONObject(i));
                }
                attendanceSummarySubAdapter = new AttendanceSummarySubAdapter(activity, arrayList_attendance_request);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                holder.rvLeaveType.setLayoutManager(layoutManager);
                holder.rvLeaveType.setHasFixedSize(true);
                holder.rvLeaveType.setAdapter(attendanceSummarySubAdapter);

            }else {
                holder.llLeaveDetails.setVisibility(View.GONE);
                holder.rvLeaveType.setVisibility(View.GONE);

            }


            if (arrayList_attendance_summery.get(position).getJSONArray("conveyance_details").length() > 0){

                JSONArray conveyance_details = arrayList_attendance_summery.get(position).getJSONArray("conveyance_details");

                for (int i = 0; i < conveyance_details.length(); i++){

                    conveyanceDetailList.add(conveyance_details.getJSONObject(i));
                }


                holder.tvConveyance.setVisibility(View.VISIBLE);

                holder.tvConveyance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, ConveyanceDetails.class);
                        intent.putExtra("conveyance",conveyanceDetailList.toString());
                        try {
                            intent.putExtra("logintym",arrayList_attendance_summery.get(position).getString("login_time")
                                    .substring(arrayList_attendance_summery.get(position).getString("login_time").indexOf("T") + 1));

                            intent.putExtra("logouttym",arrayList_attendance_summery.get(position).getString("logout_time")
                                    .substring(arrayList_attendance_summery.get(position).getString("logout_time").indexOf("T") + 1));
                            intent.putExtra("date",MethodUtils.profileDate(arrayList_attendance_summery.get(position).getString("date")));
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }else {
                holder.tvConveyance.setVisibility(View.GONE);
            }

            setAnimation(holder.viewItem,position);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.item_animation_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return arrayList_attendance_summery.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate,tvLoginTime,tvLogoutTime,tvGrace,tvRemarks,
                tvCL,tvEL,tvSL,tvAbsent,tvConveyance,tv_al_date;

        RecyclerView rvLeaveType;
        ImageView ivArrowup,ivArrowDown;

        LinearLayout llLeaveDetails;
        View viewItem;

        public MyViewHolder(@NonNull View itemView, Activity activity) {
            super(itemView);
            viewItem = itemView;
            tvGrace = itemView.findViewById(R.id.tvGrace);
            tvLogoutTime = itemView.findViewById(R.id.tvLogoutTime);
            tvLoginTime = itemView.findViewById(R.id.tvLoginTime);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvRemarks = itemView.findViewById(R.id.tvRemarks);

            tvCL = itemView.findViewById(R.id.tvCL);
            tvEL = itemView.findViewById(R.id.tvEL);
            tvSL = itemView.findViewById(R.id.tvSL);
            tvAbsent = itemView.findViewById(R.id.tvAbsent);
            tvConveyance = itemView.findViewById(R.id.tvConveyance);

            rvLeaveType = itemView.findViewById(R.id.rvLeaveType);
            ivArrowup = itemView.findViewById(R.id.ivArrowup);
            ivArrowDown = itemView.findViewById(R.id.ivArrowDown);

            llLeaveDetails = itemView.findViewById(R.id.llLeaveDetails);
            tv_al_date = itemView.findViewById(R.id.tv_al_date);
        }
    }



}
