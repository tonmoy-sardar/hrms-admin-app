package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_approval_list extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;
    UpdateData updateData;
    boolean upDateAgain = true;
    boolean updateLoader = true;
    ViewGroup parent;


    public Adapter_approval_list(ArrayList<JSONObject> arrayList, Context context) {
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_approval_list, parent, false);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

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
                ((DataObjectHolder) holder).tv_employee_name.setText(arrayList.get(position).getString("employee_name"));
                ((DataObjectHolder) holder).tv_request_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).getString("request_date")));
                ((DataObjectHolder) holder).tv_deviation_start_time.setText(arrayList.get(position).getString("duration_start")
                        .substring(arrayList.get(position).getString("duration_start").indexOf("T") + 1));
                ((DataObjectHolder) holder).tv_deviation_end_time.setText(arrayList.get(position).getString("duration_end")
                        .substring(arrayList.get(position).getString("duration_end").indexOf("T") + 1));
                ((DataObjectHolder) holder).tv_deviation_time.setText(arrayList.get(position).getString("duration") + " mins");

                if (arrayList.get(position).getString("request_type").equalsIgnoreCase("HD")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Half Day");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("FD")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Full Day");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("GR")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Grace Period");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("MP")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Mispunch");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("WO")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Week Off");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("OD")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Off Duty");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("FOD")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Full Day Official Duty");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("POD")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Partly Official Duty");
                } else if (arrayList.get(position).getString("request_type").equalsIgnoreCase("LC")) {
                    ((DataObjectHolder) holder).tv_leave_type.setText("Late Conveyance");
                } else {
                    ((DataObjectHolder) holder).tv_leave_type.setText("N/A");
                }


                ((DataObjectHolder) holder).tv_approval_justification.setText(arrayList.get(position).getString("justification"));

                ((DataObjectHolder) holder).tv_conveyance_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick != null) {
                            itemClick.onItemClick(position);
                        }
                    }
                });
            } catch (JSONException e) {
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
        TextView tv_conveyance_details, tv_employee_name, tv_request_date, tv_deviation_start_time, tv_deviation_end_time, tv_deviation_time, tv_leave_type,
                tv_approval_justification, tv_approval_submit;
        RadioButton rb_approve, rb_reject;
        EditText et_add_remarks;

        public DataObjectHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_conveyance_details = itemView.findViewById(R.id.tv_conveyance_details);
            tv_employee_name = itemView.findViewById(R.id.tv_employee_name);
            tv_request_date = itemView.findViewById(R.id.tv_request_date);
            tv_deviation_start_time = itemView.findViewById(R.id.tv_deviation_start_time);
            tv_deviation_end_time = itemView.findViewById(R.id.tv_deviation_end_time);
            tv_deviation_time = itemView.findViewById(R.id.tv_deviation_time);
            tv_leave_type = itemView.findViewById(R.id.tv_leave_type);
            tv_approval_justification = itemView.findViewById(R.id.tv_approval_justification);
            tv_approval_submit = itemView.findViewById(R.id.tv_approval_submit);
            rb_approve = itemView.findViewById(R.id.rb_approve);
            rb_reject = itemView.findViewById(R.id.rb_reject);
            et_add_remarks = itemView.findViewById(R.id.et_add_remarks);
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
        void onItemClick(int pos);
    }
}
