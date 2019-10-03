package com.sft.hrmsadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.AppConfig;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.RetrofitServiceClass.mServiceList;
import com.sft.hrmsadmin.utils.MethodUtils;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {

    TextView tvForgotPWD;
    Button btLogin;
    EditText etEmail, etPWD;


    Button btn_login;
    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    EditText et_user_name, et_password;
    MySharedPreferance mySharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MethodUtils.setStickyBar(LoginActivity.this);
        System.out.println("className======>>>" + getClass().getSimpleName());
        tvForgotPWD = findViewById(R.id.tvForgotPWD);

       /* tvForgotPWD = findViewById(R.id.tvForgotPWD);
        btLogin = findViewById(R.id.btLogin);
        etEmail = findViewById(R.id.etEmail);
        etPWD = findViewById(R.id.etPWD);*/

        tvForgotPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






        btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);

        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        retrofitResponse = new RetrofitResponse(LoginActivity.this, getSupportFragmentManager());

        /*et_user_name.setText("manoj_kumar_das");
        et_password.setText("Shyam@123");*/

       /* et_user_name.setText("admin");
        et_password.setText("Shyam2019");*/

        mySharedPreferance = new MySharedPreferance(this);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLoginApi();
            }
        });


    }



    private void callLoginApi() {
        if (checkValidation()) {
            retrofitResponse.showProgressDialog();
            JsonObject object = new JsonObject();
            object.addProperty("username", et_user_name.getText().toString().trim());
            object.addProperty("password", et_password.getText().toString().trim());

            Retrofit retrofit = AppConfig.getRetrofit(mServiceList.Base_URL);
            ServiceClient apiInterface = retrofit.create(ServiceClient.class);

            final Call<ResponseBody> register = apiInterface.post_login("application/json", object);
            register.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println("Service URL==>" + response.raw().request().url());
                    try {
                        if (response.code() == 200 || response.code() == 201) {
                            String responseString = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.optInt("request_status") == 1) {
                                System.out.println("jsonObject: " + jsonObject);
                                mySharedPreferance.savePreferancce(mySharedPreferance.login_token, jsonObject.getString("token"));
                                mySharedPreferance.savePreferancce(mySharedPreferance.remember_user_name, et_user_name.getText().toString().trim());
                                mySharedPreferance.savePreferancce(mySharedPreferance.remember_password, et_password.getText().toString().trim());
                                mySharedPreferance.savePreferancce(mySharedPreferance.login_response, jsonObject.toString());
                                Toast.makeText(LoginActivity.this, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (jsonObject.optInt("request_status") == 0) {
                                Toast.makeText(LoginActivity.this, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            String responseString = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(responseString);
                            Toast.makeText(LoginActivity.this, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        System.out.println("exception: " + e.toString());
                    }
                    retrofitResponse.hideProgressDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    retrofitResponse.hideProgressDialog();
                }
            });
        }
    }


    public boolean checkValidation() {
        if (et_user_name.getText().toString().trim().length() < 1) {
            et_user_name.setError("Please enter your user name.");
            et_user_name.requestFocus();
            return false;
        } else if (et_password.getText().toString().trim().length() < 1) {
            et_password.setError("Please enter your password.");
            et_password.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
