package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.AppConfig;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.RetrofitServiceClass.mServiceList;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public DrawerLayout mDrawerLayout;
    RelativeLayout base_rl_contentview;
    ImageView iv_cross;
    public ImageView iv_close;
    public ImageButton img_topbar_menu, img_topbar_back;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView tvAccount, tv_universal_header, tv_user_name, tv_logout;
    RelativeLayout rl_menu;
    MySharedPreferance mySharedPreferance;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base_rl_contentview = findViewById(R.id.base_rl_contentview);
        img_topbar_menu = findViewById(R.id.img_topbar_menu);
        iv_cross = findViewById(R.id.iv_cross);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        tv_universal_header = findViewById(R.id.tv_universal_header);
        rl_menu = findViewById(R.id.rl_menu);
        img_topbar_back = findViewById(R.id.img_topbar_back);

        //tvAccount = findViewById(R.id.tvAccount);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_logout = findViewById(R.id.tv_logout);

        mySharedPreferance = new MySharedPreferance(this);
        try {
            jsonObject = new JSONObject(mySharedPreferance.getPreferancceString(mySharedPreferance.login_response));
            tv_user_name.setText(jsonObject.getString("first_name") + " " + jsonObject.getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        initializeDrawer();

        clickEvent();

        System.out.println("token: " + mySharedPreferance.getPreferancceString(mySharedPreferance.login_token));


        img_topbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void clickEvent() {

        img_topbar_menu.setOnClickListener(this);
        iv_cross.setOnClickListener(this);
        //tvAccount.setOnClickListener(this);
        rl_menu.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }


    public void addContentView(View view) {
        base_rl_contentview.removeAllViews();
        base_rl_contentview.addView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
    }


    private void initializeDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name
        ) {
            @Override
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        initializeDrawerToggle(mDrawerToggle);
    }


    public void initializeDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
        this.mDrawerToggle = mDrawerToggle;
    }


    private boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.LEFT);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_topbar_menu:
                if (isDrawerOpen())
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.iv_cross:
                if (isDrawerOpen()) {
                    mDrawerLayout.closeDrawers();
                }
                break;

           /* case R.id.tvAccount:
                if (isDrawerOpen()) {
                    mDrawerLayout.closeDrawers();
                }

                break;*/
            case R.id.tv_logout:
                if (isDrawerOpen()) {
                    mDrawerLayout.closeDrawers();
                }
                callLogOutApi();
                break;
        }

    }

    private void callLogOutApi() {
        System.out.println("tokenLogOut: " + mySharedPreferance.getPreferancceString(mySharedPreferance.login_token));

        Retrofit retrofit = AppConfig.getRetrofit(mServiceList.Base_URL);
        ServiceClient apiInterface = retrofit.create(ServiceClient.class);

        final Call<ResponseBody> register = apiInterface.call_logoutApi("Token " + mySharedPreferance.getPreferancceString(mySharedPreferance.login_token));
        register.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("url: " + response.raw().request().url() + " " + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    navigateToLogin();
                } else {
                    navigateToLogin();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                navigateToLogin();
            }
        });
    }

    private void navigateToLogin() {
        mySharedPreferance.deletePreferenceValue(mySharedPreferance.login_response);
        mySharedPreferance.deletePreferenceValue(mySharedPreferance.login_token);
        mySharedPreferance.deletePreferenceValue(mySharedPreferance.remember_user_name);
        mySharedPreferance.deletePreferenceValue(mySharedPreferance.remember_password);
        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
        logIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
