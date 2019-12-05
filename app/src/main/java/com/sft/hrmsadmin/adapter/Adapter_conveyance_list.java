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
public class Adapter_conveyance_list extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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


    public Adapter_conveyance_list(ArrayList<JSONObject> arrayList, Context context) {
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_conveyance_approval, parent, false);
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
                ((DataObjectHolder) holder).tv_request_date.setText(GetFormatDateTime.getFormatDate(arrayList.get(position).getString("duration_start")));


                ((DataObjectHolder) holder).rb_approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approval_status = "2";
                        ((DataObjectHolder) holder).et_modified_amount.setText("");
                        ((DataObjectHolder) holder).et_modified_amount.setVisibility(View.INVISIBLE);
                    }
                });


                ((DataObjectHolder) holder).rb_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approval_status = "1";
                        ((DataObjectHolder) holder).et_modified_amount.setText("");
                        ((DataObjectHolder) holder).et_modified_amount.setVisibility(View.INVISIBLE);
                    }
                });

                ((DataObjectHolder) holder).rb_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approval_status = "3";
                        ((DataObjectHolder) holder).et_modified_amount.setVisibility(View.VISIBLE);
                    }
                });


                ((DataObjectHolder) holder).rb_approve.setChecked(false);
                ((DataObjectHolder) holder).rb_reject.setChecked(false);
                ((DataObjectHolder) holder).rb_modify.setChecked(false);
                ((DataObjectHolder) holder).et_modified_amount.setText("");


                ((DataObjectHolder) holder).tv_approval_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemClick != null) {
                                if (((DataObjectHolder) holder).et_modified_amount.getText().toString().equalsIgnoreCase("")) {
                                    itemClick.onItemClickApproval(position, approval_status, arrayList.get(position).getString("conveyance_expense"));
                                } else {
                                    itemClick.onItemClickApproval(position, approval_status, ((DataObjectHolder) holder).et_modified_amount.getText().toString());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                ((DataObjectHolder) holder).tv_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick != null) {
                            itemClick.onItemClickDetails(position);
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
                            arrayList.get(position).put("is_selected", true);
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
                            arrayList.get(position).put("is_selected", false);
                            notifyDataSetChanged();

                            if (itemClick != null) {
                                itemClick.onItemClickSelectedView(position);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                setAnimation(((DataObjectHolder) holder).cv_total, position);

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
        TextView tv_employee_name, tv_request_date, tvFrom, tvTo, tv_approval_submit, tvEligibility, tvAmount, tv_details;
        RadioButton rb_approve, rb_reject, rb_modify;
        EditText et_modified_amount;
        View v_selection;
        CardView cv_total;

        public DataObjectHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            tv_employee_name = itemView.findViewById(R.id.tv_employee_name);
            tv_request_date = itemView.findViewById(R.id.tv_request_date);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
            tv_approval_submit = itemView.findViewById(R.id.tv_approval_submit);
            rb_approve = itemView.findViewById(R.id.rb_approve);
            rb_reject = itemView.findViewById(R.id.rb_reject);
            et_modified_amount = itemView.findViewById(R.id.et_modified_amount);
            rb_modify = itemView.findViewById(R.id.rb_modify);
            tvEligibility = itemView.findViewById(R.id.tvEligibility);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tv_details = itemView.findViewById(R.id.tv_details);
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

        void onItemClickApproval(int pos, String approval_status, String conveyance_expense);

        void onItemClickDetails(int pos);
    }
}
