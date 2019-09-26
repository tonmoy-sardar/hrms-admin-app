package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.ProgressBarDialog;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendanceSummeryActivity extends MainActivity {

    public View view;
    ArrayList<JSONObject> arrayList_attendance_summery;
    ArrayList<JSONObject> arrayList_employee_list;

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    String token = "";
    MySharedPreferance mySharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_attendance_summery, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("ATTENDANCE SUMMERY");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        //retrofitResponse = new RetrofitResponse(this);
        retrofitResponse = new RetrofitResponse(this, getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(this);
        //token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        token = "bee8ced4601fc53d7e1bfc79981a925234e0678a";


        get_attendance_admin_summary_list();
    }


    public void get_attendance_admin_summary_list() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_attendance_admin_summary_list("Token " + token, "application/json",
                9, 2019, 9),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        if (jsonObject != null) {
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    //arrayList_conveyance.add(results.getJSONObject(i));
                                }
                                //adapter_conveyance_list.notifyDataSetChanged();

                                get_employee_list_wo_pagination();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    public void get_employee_list_wo_pagination() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_employee_list_wo_pagination("Token " + token),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            JSONArray result = jsonObject.getJSONArray("result");
                            if (result.length() > 0) {
                                for (int i = 0; i < result.length(); i++) {
                                   // arrayList_employee_list.add(result.getJSONObject(i));
                                }
                                //adapter_department_list.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
