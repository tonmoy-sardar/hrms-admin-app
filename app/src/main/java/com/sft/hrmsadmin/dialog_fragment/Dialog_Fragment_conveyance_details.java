package com.sft.hrmsadmin.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sft.hrmsadmin.R;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_conveyance_details extends DialogFragment {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_back;


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
        v = inflater.inflate(R.layout.dialog_fragment_conveyance_details, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());

        iv_df_back = v.findViewById(R.id.iv_df_back);


        iv_df_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}
