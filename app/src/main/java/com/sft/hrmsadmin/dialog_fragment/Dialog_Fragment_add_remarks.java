package com.sft.hrmsadmin.dialog_fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.activity.LoginActivity;
import com.sft.hrmsadmin.activity.SplashScreen;
import com.sft.hrmsadmin.utils.GetFormatDateTime;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_add_remarks extends DialogFragment {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_cross;
    EditText et_add_remarks;
    TextView tv_approval_submit;
    OnItemClickDialog itemClickDialog;
    CardView cv_add_remarks;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.DialogCustomStyle);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogCustomStyle;
        System.out.println("Dialog ONcreate============>");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_fragment_add_remarks, container, false);
        //animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        //slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_up);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());

        iv_df_cross = v.findViewById(R.id.iv_df_cross);
        et_add_remarks = v.findViewById(R.id.et_add_remarks);
        tv_approval_submit = v.findViewById(R.id.tv_approval_submit);
        cv_add_remarks = v.findViewById(R.id.cv_add_remarks);


        tv_approval_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickDialog != null) {
                    itemClickDialog.onItemClick(et_add_remarks.getText().toString());
                    dismiss();
                }
            }
        });


        iv_df_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimationExit(cv_add_remarks);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 700);
            }
        });

        setAnimation(cv_add_remarks);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    setAnimationExit(cv_add_remarks);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 700);
                }
                return true;
            }
        });

        return v;
    }





    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_from_up);
        viewToAnimate.startAnimation(animation);
    }


    private void setAnimationExit(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        viewToAnimate.startAnimation(animation);
    }


    public void setOnDialogListener(OnItemClickDialog itemClickDialog) {
        this.itemClickDialog = itemClickDialog;
    }


    public interface OnItemClickDialog {
        void onItemClick(String et_add_remarks);
    }


}
