package com.sft.hrmsadmin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.activity.AttendanceSummeryActivity;
import com.sft.hrmsadmin.pojo_calsses.employee_detail.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailAdapter extends RecyclerView.Adapter<EmployeeDetailAdapter.MyViewHolder> {

    Activity activity;
    // List<Result> resultListEmployee;
    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    ArrayList<JSONObject> arrayList_Backup;

    OnItemClickListenerEmployee itemClickListener;

    public EmployeeDetailAdapter(Activity activity, ArrayList<JSONObject> arrayList) {

        this.activity = activity;
        //this.resultListEmployee = resultListEmployee;
        this.arrayList = arrayList;
        arrayList_Backup = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_employee, parent, false);

        return new EmployeeDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        try {
            holder.tvEmployee.setText(arrayList.get(position).getString("first_name") + " " + arrayList.get(position).getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.tvEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(arrayList.get(position).getInt("cu_user"),
                                arrayList.get(position).getString("first_name") + " " + arrayList.get(position).getString("last_name"));
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmployee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmployee = itemView.findViewById(R.id.tvEmployee);
        }
    }


    public void setOnItemClickListenerEmployee(OnItemClickListenerEmployee itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListenerEmployee {

        void OnItemClick(Integer id, String name);
    }


    public void refreshBackup(ArrayList<JSONObject> arrayList) {
        arrayList_Backup.clear();
        this.arrayList_Backup.addAll(arrayList);
    }

    public void Filter(String query) {
        System.out.println("Filter With===>>>" + query);
        arrayList.clear();
        try {
            if (query.length() > 0) {
                query = query.toString().toLowerCase();

                for (int i = 0; i < arrayList_Backup.size(); i++) {
                    String event_title = arrayList_Backup.get(i).getString("first_name")+" "+arrayList_Backup.get(i).getString("last_name");
                    System.out.println("==============>" + event_title);
                    String text = event_title.toLowerCase();
                    if (text.contains(query)) {
                        arrayList.add(arrayList_Backup.get(i));
                    }
                }
            } else {
                arrayList.addAll(arrayList_Backup);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
