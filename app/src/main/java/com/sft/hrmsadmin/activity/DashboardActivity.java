package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.sft.hrmsadmin.BuildConfig;
import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.utils.MessageDialog;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends MainActivity {

    public View view;
    LinearLayout ll_attendance;
    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    String token = "";
    String app_name = "HRMS Admin";
    int current_app_version = -1;
    JSONObject jsonObject;
    CardView cv_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_dashboard, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("HRMS DASHBOARD");

        ll_attendance = findViewById(R.id.ll_attendance);
        cv_attendance = findViewById(R.id.cv_attendance);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        //retrofitResponse = new RetrofitResponse(getActivity());
        retrofitResponse = new RetrofitResponse(this, getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(this);
        token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        try {
            jsonObject = new JSONObject(mySharedPreferance.getPreferancceString(mySharedPreferance.login_response));
            /*JSONArray current_module_access = jsonObject.getJSONArray("module_access");
            for (int i = 0; i < current_module_access.length(); i++) {
                if (current_module_access.getJSONObject(i).getJSONObject("module").getString("cm_name").equalsIgnoreCase("HRMS")) {
                    module_id = current_module_access.getJSONObject(i).getJSONObject("module").getInt("id");
                }
            }
            System.out.println("module_id==========>>>" + module_id);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ll_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AttendanceCategoryActivity.class);
                startActivity(intent);
            }
        });


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int versionCode = BuildConfig.VERSION_CODE;
            current_app_version = versionCode;
            System.out.println("versionCode==========>>>" + versionCode);
            //get_app_version();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        setAnimation(cv_attendance);

    }


    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(DashboardActivity.this, R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }


    @Override
    protected void onResume() {
        super.onResume();
        get_app_version();
    }

    public void get_app_version() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_app_version("Token " + token, "application/json", app_name,
                current_app_version),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            setAnimation(cv_attendance);
                            if (jsonObject.getJSONObject("result").getBoolean("version_upgraded") == true) {
                                showMessagePopup("New updated version of HRMS Admin is available in Play Store now. Please install the latest version.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    MessageDialog messageDialogPopup;

    public void showMessagePopup(String msg) {
        messageDialogPopup = new MessageDialog(this);
        messageDialogPopup.setTitle(msg);
        messageDialogPopup.show();

        messageDialogPopup.setCancelable(false);

        messageDialogPopup.bt_dialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToPlaystore();
                messageDialogPopup.dismiss();
            }
        });
    }

    public void redirectToPlaystore() {
        final String appPackageName = getPackageName(); // package name of the app
        System.out.println("packageID: " + appPackageName);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
