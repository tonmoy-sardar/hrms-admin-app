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
import com.sft.hrmsadmin.adapter.Adapter_conveyance_details_list;
import com.sft.hrmsadmin.adapter.Adapter_filter_by_list;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_conveyance_details_list extends DialogFragment {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    JSONArray conveyance_details;
    RecyclerView rv_conveyance_details_list;
    Adapter_conveyance_details_list adapter_conveyance_details_list;
    ArrayList<JSONObject> arrayList_conveyance_details;
    ImageView iv_df_cross;


    public void setData(JSONArray conveyance_details) {
        this.conveyance_details = conveyance_details;
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
        v = inflater.inflate(R.layout.dialog_fragment_conveyance_details_list, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());
        System.out.println("conveyance_details_array===>>>" + conveyance_details);

        rv_conveyance_details_list = v.findViewById(R.id.rv_conveyance_details_list);
        iv_df_cross = v.findViewById(R.id.iv_df_cross);

        try {
            arrayList_conveyance_details = new ArrayList<JSONObject>();
            for (int i = 0; i < conveyance_details.length(); i++) {
                arrayList_conveyance_details.add(conveyance_details.getJSONObject(i));
            }
            adapter_conveyance_details_list = new Adapter_conveyance_details_list(arrayList_conveyance_details, getActivity());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rv_conveyance_details_list.setLayoutManager(layoutManager);
            rv_conveyance_details_list.setHasFixedSize(true);
            rv_conveyance_details_list.setAdapter(adapter_conveyance_details_list);
        } catch (Exception e){
            e.printStackTrace();
        }


        iv_df_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return v;
    }


}
