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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 8/18/2016.
 */
public class Adapter_leave_approval_list extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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


    public Adapter_leave_approval_list(ArrayList<JSONObject> arrayList, Context context) {
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_leave_approval_list, parent, false);
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
                ((DataObjectHolder) holder).tv_employee_name.setText(arrayList.get(position).getString("employee_name"));
                ((DataObjectHolder) holder).tv_request_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).getString("created_at")));

                if (arrayList.get(position).has("start_date")) {
                    ((DataObjectHolder) holder).tv_start_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).optString("start_date")));
                    ((DataObjectHolder) holder).tv_start_title.setText("Start Date : ");
                } else {
                    ((DataObjectHolder) holder).tv_start_date.setText(arrayList.get(position).getString("duration_start")
                            .substring(arrayList.get(position).getString("duration_start").indexOf("T") + 1));
                    ((DataObjectHolder) holder).tv_start_title.setText("Deviation Start : ");
                }

                if (arrayList.get(position).has("end_date")) {
                    ((DataObjectHolder) holder).tv_end_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).optString("end_date")));
                    ((DataObjectHolder) holder).tv_end_title.setText("End Date : ");
                } else {
                    ((DataObjectHolder) holder).tv_end_date.setText(arrayList.get(position).getString("duration_end")
                            .substring(arrayList.get(position).getString("duration_end").indexOf("T") + 1));
                    ((DataObjectHolder) holder).tv_end_title.setText("Deviation End : ");
                }

                if (arrayList.get(position).has("reason")) {
                    ((DataObjectHolder) holder).tv_leave_reason.setText(arrayList.get(position).optString("reason"));
                } else {
                    ((DataObjectHolder) holder).tv_leave_reason.setText(arrayList.get(position).optString("justification"));
                }


                ((DataObjectHolder) holder).tv_leave_type.setText(arrayList.get(position).getString("leave_type"));


                ((DataObjectHolder) holder).tv_conveyance_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick != null) {
                            itemClick.onItemClick(position);
                        }
                    }
                });


                ((DataObjectHolder) holder).rb_approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approval_status = "approved";
                    }
                });


                ((DataObjectHolder) holder).rb_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approval_status = "reject";
                    }
                });

                ((DataObjectHolder) holder).rb_free.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approval_status = "relese";
                    }
                });


                ((DataObjectHolder) holder).rb_approve.setChecked(false);
                ((DataObjectHolder) holder).rb_reject.setChecked(false);
                ((DataObjectHolder) holder).rb_free.setChecked(false);
                ((DataObjectHolder) holder).et_add_remarks.setText("");


                ((DataObjectHolder) holder).tv_approval_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick != null) {
                            itemClick.onItemClickApproval(position, approval_status, ((DataObjectHolder) holder).et_add_remarks.getText().toString());
                        }
                    }
                });


                if (arrayList.get(position).getBoolean("is_selected") == false) {
                    ((DataObjectHolder) holder).v_selection.setVisibility(View.GONE);
                } else {
                    ((DataObjectHolder) holder).v_selection.setVisibility(View.VISIBLE);
                }


                ((DataObjectHolder) holder).cv_total.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            arrayList.get(position).put("is_selected",true);
                            notifyDataSetChanged();

                            if (itemClick != null) {
                                itemClick.onItemClickSelectedView(position);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


                ((DataObjectHolder) holder).v_selection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            arrayList.get(position).put("is_selected",false);
                            notifyDataSetChanged();

                            if (itemClick != null) {
                                itemClick.onItemClickSelectedView(position);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        TextView tv_conveyance_details, tv_employee_name, tv_request_date, tv_start_date, tv_end_date, tv_leave_type,
                tv_leave_reason, tv_approval_submit,tv_start_title,tv_end_title;
        RadioButton rb_approve, rb_reject, rb_free;
        EditText et_add_remarks;
        View v_selection;
        CardView cv_total;

        public DataObjectHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_conveyance_details = itemView.findViewById(R.id.tv_conveyance_details);
            tv_employee_name = itemView.findViewById(R.id.tv_employee_name);
            tv_request_date = itemView.findViewById(R.id.tv_request_date);
            tv_start_date = itemView.findViewById(R.id.tv_start_date);
            tv_end_date = itemView.findViewById(R.id.tv_end_date);
            tv_leave_type = itemView.findViewById(R.id.tv_leave_type);
            tv_leave_reason = itemView.findViewById(R.id.tv_leave_reason);
            tv_approval_submit = itemView.findViewById(R.id.tv_approval_submit);
            rb_approve = itemView.findViewById(R.id.rb_approve);
            rb_reject = itemView.findViewById(R.id.rb_reject);
            et_add_remarks = itemView.findViewById(R.id.et_add_remarks);
            rb_free = itemView.findViewById(R.id.rb_free);
            tv_start_title = itemView.findViewById(R.id.tv_start_title);
            tv_end_title = itemView.findViewById(R.id.tv_end_title);
            v_selection = itemView.findViewById(R.id.v_selection);
            cv_total = itemView.findViewById(R.id.cv_total);
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
        void onItemClickSelectedView(int pos);
        void onItemClickApproval(int pos, String approval_status, String add_remark);
    }
}
