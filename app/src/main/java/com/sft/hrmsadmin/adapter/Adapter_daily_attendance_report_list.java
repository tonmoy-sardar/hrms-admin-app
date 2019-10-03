package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_daily_attendance_report_list extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Adapter_sub_daily_attendance_report_list.OnSubItemClick {

    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;
    String approval_status = "";
    Adapter_sub_daily_attendance_report_list adapter_sub_daily_attendance_report_list;
    ArrayList<JSONObject> arrayList_sub_daily_attendance;

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;
    UpdateData updateData;
    boolean upDateAgain = true;
    boolean updateLoader = true;
    ViewGroup parent;


    public Adapter_daily_attendance_report_list(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    public void paginate(UpdateData updateData) {
        this.updateData = updateData;
    }

    public void updateAgain(boolean upDateAgain) {
        this.upDateAgain = upDateAgain;
    }

    public void loader(boolean updateLoader) {
        this.updateLoader = updateLoader;
    }



    public interface UpdateData {
        void get(int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL:
                this.parent = parent;
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_daily_attendance_report, parent, false);
                DataObjectHolder holder = new DataObjectHolder(view);
                return holder;
            case FOOTER:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.pagination_loader, parent, false);
                FooterProgressbar footerProgressbar = new FooterProgressbar(view1);
                return footerProgressbar;
            default:
                throw new IllegalStateException("Unknown view type: " + viewType);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof FooterProgressbar) {
            if (updateLoader) {
                ((FooterProgressbar) holder).ll_loader.setVisibility(View.VISIBLE);
            } else {
                ((FooterProgressbar) holder).ll_loader.setVisibility(View.GONE);
            }
        } else {
            if (position > 0 && position == (arrayList.size() - 5)) {
                System.out.println("the Position pgnton======================>" + position);
                if (upDateAgain) {
                    System.out.println("the Position pgnton exact======================>" + position);
                    updateData.get(arrayList.size());
                }
            }

            try {
                if (arrayList.get(position).has("employee_name")) {
                    ((DataObjectHolder) holder).tv_employee_name.setText(arrayList.get(position).optString("employee_name"));
                } else {
                    ((DataObjectHolder) holder).tv_employee_name.setText("--");
                }

                ((DataObjectHolder) holder).tv_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).optString("date")));
                if (arrayList.get(position).getString("login_time").equalsIgnoreCase("null")) {
                    ((DataObjectHolder) holder).tv_login_time.setText("--");
                    ((DataObjectHolder) holder).tv_logout_time.setText("--");
                } else {
                    ((DataObjectHolder) holder).tv_login_time.setText(arrayList.get(position).getString("login_time")
                            .substring(arrayList.get(position).getString("login_time").indexOf("T") + 1));
                    ((DataObjectHolder) holder).tv_logout_time.setText(arrayList.get(position).getString("logout_time")
                            .substring(arrayList.get(position).getString("logout_time").indexOf("T") + 1));
                }


                arrayList_sub_daily_attendance = new ArrayList<JSONObject>();
                for (int i = 0; i < arrayList.get(position).getJSONArray("attendance_request").length(); i++) {
                    arrayList_sub_daily_attendance.add( arrayList.get(position).getJSONArray("attendance_request").getJSONObject(i));
                }
                adapter_sub_daily_attendance_report_list = new Adapter_sub_daily_attendance_report_list(arrayList_sub_daily_attendance, context);
                adapter_sub_daily_attendance_report_list.setOnSubItemListener(this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                ((DataObjectHolder) holder).rv_deviations.setLayoutManager(layoutManager);
                ((DataObjectHolder) holder).rv_deviations.setHasFixedSize(true);
                ((DataObjectHolder) holder).rv_deviations.setAdapter(adapter_sub_daily_attendance_report_list);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return FOOTER;
        }
        return NORMAL;
    }


    private boolean isPositionFooter(int position) {
        return position == arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        View viewItem;
        TextView tv_employee_name, tv_date, tv_login_time, tv_logout_time;
        RecyclerView rv_deviations;

        public DataObjectHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_employee_name = itemView.findViewById(R.id.tv_employee_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_login_time = itemView.findViewById(R.id.tv_login_time);
            tv_logout_time = itemView.findViewById(R.id.tv_logout_time);
            rv_deviations = itemView.findViewById(R.id.rv_deviations);
        }
    }


    public class FooterProgressbar extends RecyclerView.ViewHolder {

        LinearLayout ll_footer, ll_loader;

        public FooterProgressbar(View itemView) {
            super(itemView);
            ll_footer = itemView.findViewById(R.id.ll_footer);
            ll_loader = itemView.findViewById(R.id.ll_loader);

        }
    }


    public void setOnItemListener(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnItemClick {
        void onItemClick(JSONObject pos_obj);

        void onItemClickApproval(int pos, String approval_status, String conveyance_expense);

        void onItemClickDetails(int pos);
    }


    @Override
    public void onSubItemClick(JSONObject pos_obj) {
        if (itemClick != null) {
            itemClick.onItemClick(pos_obj);
        }
    }
}
