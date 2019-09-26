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

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_conveyance_details extends DialogFragment {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_cross;
    JSONObject conveyance_details;
    TextView tv_df_employee_name, tv_df_date, tv_df_deviation_start, tv_df_deviation_end_time, tv_df_deviation_time, tv_vehicle_type, tv_purpose, tv_job_alloted_by;


    public void setData(JSONObject jsonObject) {
        this.conveyance_details = jsonObject;
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
        v = inflater.inflate(R.layout.dialog_fragment_conveyance_sub_details, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());
        System.out.println("conveyance_details===>>>" + conveyance_details);

        iv_df_cross = v.findViewById(R.id.iv_df_cross);
        tv_df_employee_name = v.findViewById(R.id.tv_df_employee_name);
        tv_df_date = v.findViewById(R.id.tv_df_date);
        tv_df_deviation_start = v.findViewById(R.id.tv_df_deviation_start);
        tv_df_deviation_end_time = v.findViewById(R.id.tv_df_deviation_end_time);
        tv_df_deviation_time = v.findViewById(R.id.tv_df_deviation_time);
        tv_vehicle_type = v.findViewById(R.id.tv_vehicle_type);
        tv_purpose = v.findViewById(R.id.tv_purpose);
        tv_job_alloted_by = v.findViewById(R.id.tv_job_alloted_by);


        try {
            tv_df_employee_name.setText(conveyance_details.getString("name"));
            tv_df_date.setText(GetFormatDateTime.getFormatDate(conveyance_details.getString("duration_start")));
            tv_df_deviation_start.setText(GetFormatDateTime.getFormatDate(conveyance_details.getString("duration_start")
                    .substring(conveyance_details.getString("duration_start").indexOf("T") + 1)));
            tv_df_deviation_end_time.setText(GetFormatDateTime.getFormatDate(GetFormatDateTime.getFormatDate(conveyance_details.getString("duration_end")
                    .substring(conveyance_details.getString("duration_end").indexOf("T") + 1))));
            tv_df_deviation_time.setText(GetFormatDateTime.getFormatDate(conveyance_details.getString("duration"))+" mins");
            tv_vehicle_type.setText(GetFormatDateTime.getFormatDate(conveyance_details.getString("vehicle_type")));
            tv_purpose.setText(GetFormatDateTime.getFormatDate(conveyance_details.getString("conveyance_purpose")));
            tv_job_alloted_by.setText(GetFormatDateTime.getFormatDate(conveyance_details.getString("conveyance_alloted_by")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        iv_df_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }


}
