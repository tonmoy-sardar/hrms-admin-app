package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.adapter.ConveyanceDetailsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConveyanceDetails extends MainActivity {

    public View view;

    String logintym = "";
    String logouttym = "";
    String date = "";
    JSONArray jsonArray;
    ArrayList<JSONObject> arrayList_conveyance_list = new ArrayList<>();

    RecyclerView rvConveyance;

    ConveyanceDetailsListAdapter conveyanceDetailsListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(this, R.layout.activity_conveyance_details, null);
        addContentView(view);
        tv_universal_header.setText("CONVEYANCE DETAILS");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);

        rvConveyance = findViewById(R.id.rvConveyance);

        Intent intent  = getIntent();
        logintym = intent.getStringExtra("logintym");
        logouttym = intent.getStringExtra("logouttym");
        date = intent.getStringExtra("date");
        intent.getStringExtra("conveyance");
        System.out.println("check==========>>>"+ intent.getStringExtra("conveyance"));

        try {
            jsonArray = new JSONArray(intent.getStringExtra("conveyance"));
            for (int i = 0; i < jsonArray.length(); i++){

                arrayList_conveyance_list.add(jsonArray.getJSONObject(i));
            }

            setAdapter();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setAdapter() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvConveyance.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        conveyanceDetailsListAdapter = new ConveyanceDetailsListAdapter(ConveyanceDetails.this, arrayList_conveyance_list,logintym,logouttym,date);
        rvConveyance.setAdapter(conveyanceDetailsListAdapter);
    }
}
