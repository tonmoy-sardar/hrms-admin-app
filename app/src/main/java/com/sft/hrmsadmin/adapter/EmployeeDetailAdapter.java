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

import java.util.List;

public class EmployeeDetailAdapter extends RecyclerView.Adapter<EmployeeDetailAdapter.MyViewHolder> {

    Activity activity;
    List<Result> resultListEmployee;

    OnItemClickListenerEmployee itemClickListener;

    public EmployeeDetailAdapter(Activity activity, List<Result> resultListEmployee) {

        this.activity = activity;
        this.resultListEmployee = resultListEmployee;
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

        holder.tvEmployee.setText(resultListEmployee.get(position).getFirstName()+" "+resultListEmployee.get(position).getLastName());

        holder.tvEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(resultListEmployee.get(position).getId(),
                                resultListEmployee.get(position).getFirstName()+" "+resultListEmployee.get(position).getLastName());
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultListEmployee.size();
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


}
