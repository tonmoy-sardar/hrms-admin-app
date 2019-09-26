package com.sft.hrmsadmin.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.adapter.Adapter_filter_by_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_filter_report extends DialogFragment implements Adapter_filter_by_list.OnItemClick {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_back;
    TextView tv_submit;
    OnItemClickDialog itemClickDialog;
    RecyclerView rv_filter_department, rv_filter_designation, rv_filter_leave_type;
    Adapter_filter_by_list adapter_filter_by_list;
    ArrayList<JSONObject> arrayList_designation;
    ArrayList<JSONObject> arrayList_department;
    ArrayList<JSONObject> arrayList_leave_type;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.DialogCustomTheme_image);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogCustomTheme_image;
        System.out.println("Dialog ONcreate============>");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_fragment_filter_report, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());

        iv_df_back = v.findViewById(R.id.iv_df_back);
        tv_submit = v.findViewById(R.id.tv_submit);
        rv_filter_department = v.findViewById(R.id.rv_filter_department);
        rv_filter_designation = v.findViewById(R.id.rv_filter_designation);
        rv_filter_leave_type = v.findViewById(R.id.rv_filter_leave_type);

        rv_filter_department.setNestedScrollingEnabled(false);
        rv_filter_designation.setNestedScrollingEnabled(false);
        rv_filter_leave_type.setNestedScrollingEnabled(false);


        arrayList_designation = new ArrayList<JSONObject>();
        adapter_filter_by_list = new Adapter_filter_by_list(arrayList_designation, getActivity());
        adapter_filter_by_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_filter_department.setLayoutManager(layoutManager);
        rv_filter_department.setHasFixedSize(true);
        rv_filter_department.setAdapter(adapter_filter_by_list);


        arrayList_department = new ArrayList<JSONObject>();
        adapter_filter_by_list = new Adapter_filter_by_list(arrayList_department, getActivity());
        adapter_filter_by_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManagerProjects = new LinearLayoutManager(getActivity());
        rv_filter_designation.setLayoutManager(layoutManagerProjects);
        rv_filter_designation.setHasFixedSize(true);
        rv_filter_designation.setAdapter(adapter_filter_by_list);


        arrayList_leave_type = new ArrayList<JSONObject>();
        adapter_filter_by_list = new Adapter_filter_by_list(arrayList_leave_type, getActivity());
        adapter_filter_by_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManagerLeaveType = new LinearLayoutManager(getActivity());
        rv_filter_designation.setLayoutManager(layoutManagerLeaveType);
        rv_filter_designation.setHasFixedSize(true);
        rv_filter_designation.setAdapter(adapter_filter_by_list);
        loadFilterData();


        iv_df_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;
    }


    public void loadFilterData() {
        try {
            arrayList_designation.clear();
            arrayList_designation.add(new JSONObject().put("id", 1).put("filter_by", "Approve O/D"));
            arrayList_designation.add(new JSONObject().put("id", 2).put("filter_by", "Approve H/D"));
            arrayList_designation.add(new JSONObject().put("id", 3).put("filter_by", "Approve F/D"));
            arrayList_designation.add(new JSONObject().put("id", 4).put("filter_by", "Approve Mispunch"));
            arrayList_designation.add(new JSONObject().put("id", 5).put("filter_by", "Approve Grace"));
            adapter_filter_by_list.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onItemClick(int pos) {

    }


    public void setOnDialogListener(OnItemClickDialog itemClickDialog) {
        this.itemClickDialog = itemClickDialog;
    }

    public interface OnItemClickDialog {
        void onItemClick(String designation, String projects);
    }

}
