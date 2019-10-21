package com.sft.hrmsadmin.dialog_fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.JsonObject;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.activity.LoginActivity;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePwdDialog extends Dialog {

    Activity activity;
    EditText etOldPwd, etNewPwd, etConfirmPwd;
    Button btSave;
    ChangePwdListner changePwdListner;
    MySharedPreferance mySharedPreferance;
    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;


    public ChangePwdDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = activity.getLayoutInflater().inflate(R.layout.change_pwd_dialog, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        WindowManager.LayoutParams wmParams = getWindow().getAttributes();
        wmParams.gravity = Gravity.CENTER;

        etOldPwd = findViewById(R.id.etOldPwd);
        etNewPwd = findViewById(R.id.etNewPwd);
        etConfirmPwd = findViewById(R.id.etConfirmPwd);
        btSave = findViewById(R.id.btSave);
        mySharedPreferance = new MySharedPreferance(activity);

        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        retrofitResponse = new RetrofitResponse(activity, ((LoginActivity) activity).getSupportFragmentManager());


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etOldPwd.getText().toString().trim().equals("")) {
                    etOldPwd.setError("Please enter Valid Old Password");
                    etOldPwd.requestFocus();

                } else if (etNewPwd.getText().toString().trim().equals("")) {
                    etNewPwd.setError("Please enter Valid New Password");
                    etNewPwd.requestFocus();

                } else if (etConfirmPwd.getText().toString().trim().equals("")) {
                    etConfirmPwd.setError("Please enter Valid Confirm Password");
                    etConfirmPwd.requestFocus();
                } else {

                    if (!etOldPwd.getText().toString().trim().equals(etNewPwd.getText().toString().trim())) {
                        if (etNewPwd.getText().toString().trim().equals(etConfirmPwd.getText().toString().trim())) {
                            savePwd();
                        } else {
                            etConfirmPwd.setError("New Password and Confirm Password must be same");
                            etConfirmPwd.requestFocus();
                        }
                    } else {
                        etConfirmPwd.setError("Old Password and New Password must be different");
                        etConfirmPwd.requestFocus();
                    }
                }
            }
        });
    }

    private void savePwd() {
        JsonObject object = new JsonObject();
        object.addProperty("old_password", etOldPwd.getText().toString().trim());
        object.addProperty("new_password", etConfirmPwd.getText().toString().trim());

        System.out.println("chngPwd: " + object + " " + mySharedPreferance.getPreferancceString(mySharedPreferance.login_token));

        String token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);

        retrofitResponse.getWebServiceResponse(serviceClient.put_change_pwd("Token " + token, "application/json", object),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            if (jsonObject != null) {
                                if (jsonObject.getInt("request_status") == 0) {
                                    if (changePwdListner != null) {
                                        changePwdListner.onClick(jsonObject.getInt("request_status"), jsonObject.getString("msg"));
                                        dismiss();
                                    }
                                }
                            } else {
                                Toast.makeText(activity, "Something went wrong!! Please try again", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void setItemResponseListner(ChangePwdListner changePwdListner) {
        this.changePwdListner = changePwdListner;
    }

    public interface ChangePwdListner {
        void onClick(int request_status, String msg);
    }
}
