package com.sft.hrmsadmin.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

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

import java.util.ArrayList;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_filter_leave_approval extends DialogFragment implements Adapter_filter_by_list.OnItemClick {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_cross;
    RecyclerView rv_filter_by_list;
    ArrayList<JSONObject> arrayList_filter_by;
    Adapter_filter_by_list adapter_filter_by_list;
    OnItemClickDialog itemClickDialog;
    String selected_filter_type = "";
    Button bt_apply_filter;
    ArrayList<JSONObject> temp_arrayList_filter_by;
    Button bt_clear_filter;


    public void setData(ArrayList<JSONObject> temp_arrayList_filter_by) {
        this.temp_arrayList_filter_by = temp_arrayList_filter_by;
    }


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
        v = inflater.inflate(R.layout.dialog_fragment_filter_by, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());
        System.out.println("temp_arrayList_filter_by===>>>" + temp_arrayList_filter_by);

        iv_df_cross = v.findViewById(R.id.iv_df_cross);
        rv_filter_by_list = v.findViewById(R.id.rv_filter_by_list);
        bt_apply_filter = v.findViewById(R.id.bt_apply_filter);
        bt_clear_filter = v.findViewById(R.id.bt_clear_filter);


        arrayList_filter_by = new ArrayList<JSONObject>();
        if (temp_arrayList_filter_by == null) {
            loadFilterData();
        } else {
            arrayList_filter_by = temp_arrayList_filter_by;
        }
        adapter_filter_by_list = new Adapter_filter_by_list(arrayList_filter_by, getActivity());
        adapter_filter_by_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_filter_by_list.setLayoutManager(layoutManager);
        rv_filter_by_list.setHasFixedSize(true);
        rv_filter_by_list.setAdapter(adapter_filter_by_list);


        iv_df_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        bt_apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selected_filter_type = "";
                    JSONArray jsonArray = new JSONArray(arrayList_filter_by);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.getJSONObject(i).getString("status").equalsIgnoreCase("true")) {
                            selected_filter_type = selected_filter_type + "," + jsonArray.getJSONObject(i).getString("request_type");
                        }
                    }
                    if (selected_filter_type.length() > 1) {
                        selected_filter_type = selected_filter_type.substring(1);
                        System.out.println("selected_filter_type====>>" + selected_filter_type);
                    }
                    if (itemClickDialog != null) {
                        itemClickDialog.onItemClick(selected_filter_type,arrayList_filter_by);
                    }
                    dismiss();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        bt_clear_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFilterData();
                adapter_filter_by_list.notifyDataSetChanged();
            }
        });

        return v;
    }


    public void loadFilterData() {
        try {
            arrayList_filter_by.clear();
            arrayList_filter_by.add(new JSONObject().put("id", 1).put("filter_by", "Earn Leave").put("request_type", "EL").put("status", false));
            arrayList_filter_by.add(new JSONObject().put("id", 2).put("filter_by", "Casual Leave").put("request_type", "CL").put("status", false));
            arrayList_filter_by.add(new JSONObject().put("id", 3).put("filter_by", "Absent").put("request_type", "AB").put("status", false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int pos) {
        try {
            System.out.println("pos===========>>>" + pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setOnDialogListener(OnItemClickDialog itemClickDialog) {
        this.itemClickDialog = itemClickDialog;
    }

    public interface OnItemClickDialog {
        void onItemClick(String request_type, ArrayList<JSONObject> arrayList_filter_by);
    }

}
