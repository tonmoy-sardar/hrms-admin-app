package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_conveyance_report_list extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
    Context context;
    OnItemClick itemClick;
    String approval_status = "";

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;
    UpdateData updateData;
    boolean upDateAgain = true;
    boolean updateLoader = true;
    ViewGroup parent;

    private int lastPosition = -1;


    public Adapter_conveyance_report_list(ArrayList<JSONObject> arrayList, Context context) {
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_conveyance_report, parent, false);
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
                ((DataObjectHolder) holder).tv_employee_name.setText(arrayList.get(position).getString("name"));
                ((DataObjectHolder) holder).tvFrom.setText(arrayList.get(position).getString("from_place"));
                ((DataObjectHolder) holder).tvTo.setText(arrayList.get(position).getString("to_place"));
                ((DataObjectHolder) holder).tvEligibility.setText("INR " + arrayList.get(position).getString("eligibility") + "/day");

                ((DataObjectHolder) holder).tvAmount.setText("INR " + arrayList.get(position).getString("conveyance_expense"));
                ((DataObjectHolder) holder).tvApprovedAmount.setText("INR " + arrayList.get(position).getString("approved_expenses"));

                if (arrayList.get(position).getString("approved_expenses").equalsIgnoreCase("null")){
                    ((DataObjectHolder) holder).tvApprovedAmount.setText("INR " + arrayList.get(position).getString("conveyance_expense"));
                } else {
                    ((DataObjectHolder) holder).tvApprovedAmount.setText("INR " + arrayList.get(position).getString("approved_expenses"));
                }

                ((DataObjectHolder) holder).tv_request_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).getString("duration_start")));
                ((DataObjectHolder) holder).tv_al_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).getString("duration_start")));

                if (arrayList.get(position).getInt("conveyance_approval") == 0) {
                    ((DataObjectHolder) holder).tv_approval_status.setText("Pending Approval");
                    ((DataObjectHolder) holder).tv_approval_status.setTextColor(ContextCompat.getColor(context,R.color.color_approval_status));
                    ((DataObjectHolder) holder).llApprovedAmount.setVisibility(View.GONE);
                } else if (arrayList.get(position).getInt("conveyance_approval") == 1) {
                    ((DataObjectHolder) holder).tv_approval_status.setText("Rejected by "+arrayList.get(position).getString("conveyance_approved_by"));
                    ((DataObjectHolder) holder).tv_approval_status.setTextColor(ContextCompat.getColor(context,R.color.red_color));
                    ((DataObjectHolder) holder).llApprovedAmount.setVisibility(View.GONE);
                } else if (arrayList.get(position).getInt("conveyance_approval") == 2) {
                    ((DataObjectHolder) holder).tv_approval_status.setText("Approved by "+arrayList.get(position).getString("conveyance_approved_by"));
                    ((DataObjectHolder) holder).tv_approval_status.setTextColor(ContextCompat.getColor(context,R.color.color_green));
                    ((DataObjectHolder) holder).llApprovedAmount.setVisibility(View.VISIBLE);
                } else if (arrayList.get(position).getInt("conveyance_approval") == 3) {
                    ((DataObjectHolder) holder).tv_approval_status.setText("Modified by "+arrayList.get(position).getString("conveyance_approved_by"));
                    ((DataObjectHolder) holder).tv_approval_status.setTextColor(ContextCompat.getColor(context,R.color.color_green));
                    ((DataObjectHolder) holder).llApprovedAmount.setVisibility(View.VISIBLE);
                } else {
                    ((DataObjectHolder) holder).tv_approval_status.setText("Pending Approval");
                    ((DataObjectHolder) holder).tv_approval_status.setTextColor(ContextCompat.getColor(context,R.color.color_approval_status));
                    ((DataObjectHolder) holder).llApprovedAmount.setVisibility(View.GONE);
                }

                ((DataObjectHolder) holder).tv_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick != null) {
                            itemClick.onItemClickDetails(position);
                        }
                    }
                });

                setAnimation(((DataObjectHolder) holder).viewItem,position);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
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
        TextView tv_employee_name, tv_request_date, tvFrom, tvTo, tv_approval_submit, tvEligibility, tvAmount, tv_details, tv_approval_status,tv_al_date,tvApprovedAmount;
        LinearLayout llApprovedAmount;

        public DataObjectHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_employee_name = itemView.findViewById(R.id.tv_employee_name);
            tv_request_date = itemView.findViewById(R.id.tv_request_date);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
            tv_approval_submit = itemView.findViewById(R.id.tv_approval_submit);
            tvEligibility = itemView.findViewById(R.id.tvEligibility);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tv_details = itemView.findViewById(R.id.tv_details);
            tv_approval_status = itemView.findViewById(R.id.tv_approval_status);
            tv_al_date = itemView.findViewById(R.id.tv_al_date);
            tvApprovedAmount = itemView.findViewById(R.id.tvApprovedAmount);
            llApprovedAmount = itemView.findViewById(R.id.llApprovedAmount);
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

        void onItemClickApproval(int pos, String approval_status, String conveyance_expense);

        void onItemClickDetails(int pos);
    }
}
