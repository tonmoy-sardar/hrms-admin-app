package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
public class Adapter_department_list extends RecyclerView.Adapter<Adapter_department_list.View_Holder_menu> {


    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;
    int mSelectedPos = -1;
    String leave_type = "";

    public Adapter_department_list(ArrayList<JSONObject> arrayList, Context context, String leave_type) {
        this.arrayList = arrayList;
        this.context = context;
        this.leave_type = leave_type;
    }

    @Override
    public View_Holder_menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_department_list, parent, false);
        View_Holder_menu view_holder_menu = new View_Holder_menu(v);
        return view_holder_menu;
    }

    @Override
    public void onBindViewHolder(final View_Holder_menu holder, final int position) {

        try {
            holder.tv_filter_by.setText(arrayList.get(position).getString("cd_name"));

            holder.ll_total_cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedPos = position;
                    notifyDataSetChanged();
                    if (itemClick != null) {
                        itemClick.onItemClickDepartment(position);
                    }
                }
            });

            if (leave_type.equalsIgnoreCase(arrayList.get(position).getString("request_type"))) {
                holder.chkbxSelect.setChecked(true);
                leave_type = "";
            } else {
                if (mSelectedPos == position) {
                    holder.chkbxSelect.setChecked(true);
                } else {
                    holder.chkbxSelect.setChecked(false);
                }
            }

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
        void onItemClickDepartment(int pos);
    }
}
