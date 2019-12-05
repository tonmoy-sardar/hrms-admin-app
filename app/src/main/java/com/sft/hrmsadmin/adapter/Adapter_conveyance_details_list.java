package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_conveyance_details_list extends RecyclerView.Adapter<Adapter_conveyance_details_list.View_Holder_menu> {


    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;

    public Adapter_conveyance_details_list(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View_Holder_menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_conveyance_details_list, parent, false);
        View_Holder_menu view_holder_menu = new View_Holder_menu(v);
        return view_holder_menu;
    }

    @Override
    public void onBindViewHolder(final View_Holder_menu holder, final int position) {

        try {
            holder.tv_cd_from.setText(arrayList.get(position).getString("from_place"));
            holder.tv_cd_to.setText(arrayList.get(position).getString("to_place"));
            holder.tv_cd_vehicle_type.setText(arrayList.get(position).getString("vehicle_type"));
            holder.tv_cd_purpose.setText(arrayList.get(position).getString("conveyance_purpose"));
            holder.tv_cd_amount.setText("INR" + arrayList.get(position).getString("conveyance_expense"));
            holder.tv_cd_start_time.setText(arrayList.get(position).getString("duration_start")
                    .substring(arrayList.get(position).getString("duration_start").indexOf("T") + 1));
            holder.tv_cd_end_time.setText(arrayList.get(position).getString("duration_end")
                    .substring(arrayList.get(position).getString("duration_end").indexOf("T") + 1));
            holder.tv_cd_duration.setText(arrayList.get(position).getString("conveyance_durations") + "mins");
            if (arrayList.get(position).getBoolean("is_late_conveyance") == true) {
                holder.tv_cd_type.setText("Late Conveyance");
            } else {
                holder.tv_cd_type.setText("Other Conveyance");
            }
            holder.tv_cd_approval_status.setText(arrayList.get(position).getString("conveyance_approval_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class View_Holder_menu extends RecyclerView.ViewHolder {

        View viewItem;
        TextView tv_cd_from, tv_cd_to, tv_cd_vehicle_type, tv_cd_purpose, tv_cd_amount, tv_cd_start_time, tv_cd_end_time, tv_cd_duration, tv_cd_type, tv_cd_approval_status;

        public View_Holder_menu(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_cd_from = itemView.findViewById(R.id.tv_cd_from);
            tv_cd_to = itemView.findViewById(R.id.tv_cd_to);
            tv_cd_vehicle_type = itemView.findViewById(R.id.tv_cd_vehicle_type);
            tv_cd_purpose = itemView.findViewById(R.id.tv_cd_purpose);
            tv_cd_amount = itemView.findViewById(R.id.tv_cd_amount);
            tv_cd_start_time = itemView.findViewById(R.id.tv_cd_start_time);
            tv_cd_end_time = itemView.findViewById(R.id.tv_cd_end_time);
            tv_cd_duration = itemView.findViewById(R.id.tv_cd_duration);
            tv_cd_type = itemView.findViewById(R.id.tv_cd_type);
            tv_cd_approval_status = itemView.findViewById(R.id.tv_cd_approval_status);
        }
    }


    public void setOnItemListener(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnItemClick {
        void onItemClick(int pos);
    }
}
