package com.sft.hrmsadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
public class Adapter_approval_list extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
                ((DataObjectHolder) holder).tv_request_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).getString("duration_start")));
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


              /*  ((DataObjectHolder) holder).viewItem.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int p = position;
                        System.out.println("LongClick: " + p);
                        if (((DataObjectHolder) holder).v_selection.getVisibility() == View.VISIBLE) {
                            ((DataObjectHolder) holder).v_selection.setVisibility(View.GONE);
                        } else {
                            ((DataObjectHolder) holder).v_selection.setVisibility(View.VISIBLE);
                        }
                        return true;// returning true instead of false, works for me
                    }
                });*/


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
            tv_deviation_start_time = itemView.findViewById(R.id.tv_deviation_start_time);
            tv_deviation_end_time = itemView.findViewById(R.id.tv_deviation_end_time);
            tv_deviation_time = itemView.findViewById(R.id.tv_deviation_time);
            tv_leave_type = itemView.findViewById(R.id.tv_leave_type);
            tv_approval_justification = itemView.findViewById(R.id.tv_approval_justification);
            tv_approval_submit = itemView.findViewById(R.id.tv_approval_submit);
            rb_approve = itemView.findViewById(R.id.rb_approve);
            rb_reject = itemView.findViewById(R.id.rb_reject);
            et_add_remarks = itemView.findViewById(R.id.et_add_remarks);
            rb_free = itemView.findViewById(R.id.rb_free);
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
