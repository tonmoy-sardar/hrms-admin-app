package com.sft.hrmsadmin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.pojo_calsses.AttendanceSummaryMonths;

import java.util.List;

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.MyViewHolder> {



    Activity activity;
    List<AttendanceSummaryMonths> months;
    OnItemClickListener itemClickListener;

    public MonthsAdapter(Activity activity, List<AttendanceSummaryMonths> months) {

        this.activity = activity;
        this.months = months;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_months, parent, false);

        return new MonthsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tvMonths.setText(months.get(position).getMonth()+", "+months.get(position).getYear());

        holder.tvMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(months.get(position).getId(),
                                months.get(position).getMonth(), months.get(position).getYear());
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvMonths;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMonths = itemView.findViewById(R.id.tvMonths);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(String id, String month, String year);
    }
}
