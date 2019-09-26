package com.sft.hrmsadmin.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sft.hrmsadmin.R;

/**
 * Created by ncrts on 9/9/17.
 */

public class MessageDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView bt_dialogOK;
    public TextView tv_dialogMsg;
    String message="",bt_text="";

    public MessageDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public MessageDialog setTitle(String message){
        this.message=message;
        return this;
    }
    public void setSubmitMsg(String button){
        this.bt_text=button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_message);
        tv_dialogMsg = findViewById(R.id.tv_dialogMsg);
        bt_dialogOK = findViewById(R.id.bt_dialogOK);
        bt_dialogOK.setOnClickListener(this);

        if(bt_text.length()>0){
            bt_dialogOK.setText(bt_text);
        }
        if (message.length()>0){
            tv_dialogMsg.setText(message);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dialogOK:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}