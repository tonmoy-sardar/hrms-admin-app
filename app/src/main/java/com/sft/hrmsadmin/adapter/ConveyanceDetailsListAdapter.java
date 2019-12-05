package com.sft.hrmsadmin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.activity.ConveyanceDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConveyanceDetailsListAdapter extends RecyclerView.Adapter<ConveyanceDetailsListAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<JSONObject> conveyanceDetailList;
    String logintym;
    String logouttym;
    String date;



    public ConveyanceDetailsListAdapter(Activity activity, ArrayList<JSONObject> conveyanceDetailList,
                                        String logintym, String logouttym, String date) {

        this.activity = activity;
        this.conveyanceDetailList = conveyanceDetailList;
        this.logintym = logintym;
        this.logouttym = logouttym;
        this.date = date;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_conveyance, parent, false);

        return new ConveyanceDetailsListAdapter.MyViewHolder(itemView, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvDate.setText(date);
        holder.tvLogInTym.setText(logintym);
        holder.tvLogOutTym.setText(logouttym);

        try {
            if (conveyanceDetailList.get(position).getString("from_place")!= null) {
                holder.tvFrom.setText(conveyanceDetailList.get(position).getString("from_place"));
            }


            if (conveyanceDetailList.get(position).getString("to_place")!= null){

                holder.tvTo.setText(conveyanceDetailList.get(position).getString("to_place"));
            }


            if (conveyanceDetailList.get(position).getString("vehicle_type")!= null){

                holder.tvVehicleType.setText(conveyanceDetailList.get(position).getString("vehicle_type"));
            }

            if (conveyanceDetailList.get(position).getString("conveyance_purpose")!= null){

                holder.tvPurpose.setText(conveyanceDetailList.get(position).getString("conveyance_purpose"));
            }

            if (conveyanceDetailList.get(position).getString("conveyance_alloted_by")!= null){

                holder.tvJobAllotedBy.setText(conveyanceDetailList.get(position).getString("conveyance_alloted_by"));
            }

            if (conveyanceDetailList.get(position).getString("conveyance_expense")!= null){

                holder.tvAmount.setText("INR "+conveyanceDetailList.get(position).getString("conveyance_expense"));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return conveyanceDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate,tvLogInTym,tvLogOutTym,tvFrom,tvTo,tvVehicleType,tvPurpose,tvJobAllotedBy,tvAmount;

        public MyViewHolder(@NonNull View itemView, Activity activity) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvLogInTym = itemView.findViewById(R.id.tvLogInTym);
            tvLogOutTym = itemView.findViewById(R.id.tvLogOutTym);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
            tvVehicleType = itemView.findViewById(R.id.tvVehicleType);
            tvPurpose = itemView.findViewById(R.id.tvPurpose);
            tvJobAllotedBy = itemView.findViewById(R.id.tvJobAllotedBy);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
