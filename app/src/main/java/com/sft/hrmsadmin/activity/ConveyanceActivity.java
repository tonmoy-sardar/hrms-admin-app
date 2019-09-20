package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.adapter.Adapter_conveyance_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConveyanceActivity extends MainActivity implements Adapter_conveyance_list.OnItemClick{

    public View view;
    RecyclerView rv_attendance_conveyance;
    ArrayList<JSONObject> arrayList_conveyance;
    Adapter_conveyance_list adapter_conveyance_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_conveyance, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("CONVEYANCE");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);

        rv_attendance_conveyance = findViewById(R.id.rv_attendance_conveyance);


        arrayList_conveyance = new ArrayList<JSONObject>();
        adapter_conveyance_list = new Adapter_conveyance_list(arrayList_conveyance, this);
        adapter_conveyance_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_attendance_conveyance.setLayoutManager(layoutManager);
        rv_attendance_conveyance.setHasFixedSize(true);
        rv_attendance_conveyance.setAdapter(adapter_conveyance_list);
    }



    @Override
    public void onItemClick(int pos) {

    }


}
