package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_filter_by_list extends RecyclerView.Adapter<Adapter_filter_by_list.View_Holder_menu> {


    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;

    public Adapter_filter_by_list(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View_Holder_menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_filter_by, parent, false);
        View_Holder_menu view_holder_menu = new View_Holder_menu(v);
        return view_holder_menu;
    }

    @Override
    public void onBindViewHolder(final View_Holder_menu holder, final int position) {

        try {
            holder.tv_filter_by.setText(arrayList.get(position).getString("filter_by"));
            holder.chkbxSelect.setChecked(arrayList.get(position).getBoolean("status"));

            holder.ll_total_cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (arrayList.get(position).getBoolean("status") == false) {
                            arrayList.get(position).put("status", true);
                            notifyDataSetChanged();
                        } else {
                            arrayList.get(position).put("status", false);
                            notifyDataSetChanged();
                        }
                        if (itemClick != null) {
                            itemClick.onItemClick(position);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
        TextView tv_filter_by;
        LinearLayout ll_total_cell;
        CheckBox chkbxSelect;

        public View_Holder_menu(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_filter_by = itemView.findViewById(R.id.tv_filter_by);
            ll_total_cell = itemView.findViewById(R.id.ll_total_cell);
            chkbxSelect = itemView.findViewById(R.id.chkbxSelect);
        }
    }


    public void setOnItemListener(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnItemClick {
        void onItemClick(int pos);
    }
}
