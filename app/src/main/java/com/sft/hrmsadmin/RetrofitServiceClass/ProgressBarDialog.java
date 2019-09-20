package com.sft.hrmsadmin.RetrofitServiceClass;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.sft.hrmsadmin.R;


/**
 * Created by ncrts on 18/1/18.
 */

public class ProgressBarDialog extends DialogFragment {

    private String dialogMessage = "Please Wait.....";
    private TextView tv_pbd_message;

    public void setMessage(String message) {
        dialogMessage = message;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(getActivity()) {
            @Override
            public void onBackPressed() {

            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        if (dialogMessage != null) {
            tv_pbd_message.setText(dialogMessage);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.layout_progress_bar_dialog, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_pbd_message = view.findViewById(R.id.tv_pbd_message);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_pbd_message.setText(dialogMessage);
    }

    public void dismiss() {
        getDialog().dismiss();
    }

    public void showDialog(FragmentManager fragmentManager) {
        show(fragmentManager, "ProgressBarDialog");
    }

    public boolean isShowing() {
        if (getDialog() != null)
            return getDialog().isShowing();
        return false;
    }

}
