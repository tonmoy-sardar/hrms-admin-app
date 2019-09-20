package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_report_list extends RecyclerView.Adapter<Adapter_report_list.View_Holder_menu> {


    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;

    public Adapter_report_list(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View_Holder_menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_report_list, parent, false);
        View_Holder_menu view_holder_menu = new View_Holder_menu(v);
        return view_holder_menu;
    }

    @Override
    public void onBindViewHolder(final View_Holder_menu holder, final int position) {

        holder.tv_conveyance_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null) {
                    itemClick.onItemClick(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return 10;
    }


    public class View_Holder_menu extends RecyclerView.ViewHolder {

        View viewItem;
        TextView tv_conveyance_details;

        public View_Holder_menu(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_conveyance_details = itemView.findViewById(R.id.tv_conveyance_details);
        }
    }


    public void setOnItemListener(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnItemClick {
        void onItemClick(int pos);
    }
}
