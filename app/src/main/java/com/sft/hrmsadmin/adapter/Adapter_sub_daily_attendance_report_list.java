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
public class Adapter_sub_daily_attendance_report_list extends RecyclerView.Adapter<Adapter_sub_daily_attendance_report_list.View_Holder_menu> {


    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnSubItemClick subitemClick;

    public Adapter_sub_daily_attendance_report_list(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View_Holder_menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_sub_daily_attendance_report_list, parent, false);
        View_Holder_menu view_holder_menu = new View_Holder_menu(v);
        return view_holder_menu;
    }

    @Override
    public void onBindViewHolder(final View_Holder_menu holder, final int position) {
        try {
            holder.tv_sda_approval_status.setText(arrayList.get(position).getString("approved_status"));
            if (arrayList.get(position).getBoolean("is_late_conveyance") == true) {
                holder.tv_late_conveyance.setVisibility(View.VISIBLE);
            } else {
                holder.tv_late_conveyance.setVisibility(View.GONE);
            }
            holder.tv_deviation_time.setText(arrayList.get(position).getString("duration_start")
                    .substring(arrayList.get(position).getString("duration_start").indexOf("T") + 1)+" - "+
                    arrayList.get(position).getString("duration_end")
                    .substring(arrayList.get(position).getString("duration_end").indexOf("T") + 1));


            holder.tv_late_conveyance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (subitemClick != null) {
                        subitemClick.onSubItemClick(arrayList.get(position));
                    }
                }
            });
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
        TextView tv_sda_approval_status, tv_late_conveyance, tv_deviation_time;

        public View_Holder_menu(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_sda_approval_status = itemView.findViewById(R.id.tv_sda_approval_status);
            tv_late_conveyance = itemView.findViewById(R.id.tv_late_conveyance);
            tv_deviation_time = itemView.findViewById(R.id.tv_deviation_time);
        }
    }


    public void setOnSubItemListener(OnSubItemClick subitemClick) {
        this.subitemClick = subitemClick;
    }

    public interface OnSubItemClick {
        void onSubItemClick(JSONObject pos_obj);
    }
}
