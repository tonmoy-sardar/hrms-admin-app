package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.sft.hrmsadmin.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_conveyance_list extends RecyclerView.Adapter<Adapter_conveyance_list.View_Holder_menu> {


    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;

    public Adapter_conveyance_list(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View_Holder_menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_attendance_conveyance, parent, false);
        View_Holder_menu view_holder_menu = new View_Holder_menu(v);
        return view_holder_menu;
    }

    @Override
    public void onBindViewHolder(final View_Holder_menu holder, final int position) {


    }


    @Override
    public int getItemCount() {
        return 10;
    }


    public class View_Holder_menu extends RecyclerView.ViewHolder {

        View viewItem;


        public View_Holder_menu(View itemView) {
            super(itemView);
            viewItem = itemView;

        }
    }


    public void setOnItemListener(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnItemClick {
        void onItemClick(int pos);
    }
}
