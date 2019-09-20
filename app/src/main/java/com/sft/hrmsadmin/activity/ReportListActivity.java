package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.adapter.Adapter_conveyance_list;
import com.sft.hrmsadmin.adapter.Adapter_report_list;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_conveyance_details;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_by;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_report;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReportListActivity extends MainActivity implements Adapter_report_list.OnItemClick {

    public View view;
    Adapter_report_list adapter_report_list;
    RecyclerView rv_report_list;
    ArrayList<JSONObject> arrayList_report;
    LinearLayout ll_search_btn, ll_search_sort_section, ll_search_field, ll_sort_field, ll_filter_btn;
    EditText et_search_field;
    ImageView iv_search_close;
    int selected_pos = 0;
    Spinner sp_sort_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_report_list, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("REPORT");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);

        rv_report_list = findViewById(R.id.rv_report_list);
        ll_search_btn = findViewById(R.id.ll_search_btn);
        ll_search_sort_section = findViewById(R.id.ll_search_sort_section);
        ll_search_field = findViewById(R.id.ll_search_field);
        et_search_field = findViewById(R.id.et_search_field);
        iv_search_close = findViewById(R.id.iv_search_close);
        sp_sort_items = findViewById(R.id.sp_sort_items);
        ll_sort_field = findViewById(R.id.ll_sort_field);
        ll_filter_btn = findViewById(R.id.ll_filter_btn);


        arrayList_report = new ArrayList<JSONObject>();
        adapter_report_list = new Adapter_report_list(arrayList_report, this);
        adapter_report_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_report_list.setLayoutManager(layoutManager);
        rv_report_list.setHasFixedSize(true);
        rv_report_list.setAdapter(adapter_report_list);


        ll_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_search_sort_section.setVisibility(View.GONE);
                ll_search_field.setVisibility(View.VISIBLE);
                open_keyboard();
            }
        });


        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_search_sort_section.setVisibility(View.VISIBLE);
                ll_search_field.setVisibility(View.GONE);
                hideKeyBoard();
                et_search_field.setText("");
            }
        });


        et_search_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    if (et_search_field.getText().toString().length() > 0) {
                        hideKeyBoard();
                    }
                }
                return false;
            }
        });


        ll_sort_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_sort_items.performClick();
            }
        });


        ll_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked=============>>>");
                final Dialog_Fragment_filter_report dialog_fragment_filter_report = new Dialog_Fragment_filter_report();
                dialog_fragment_filter_report.show(getSupportFragmentManager(), "dialog_fragment_filter_by");
            }
        });


        custom_spinner();
    }

    @Override
    public void onItemClick(int pos) {
        System.out.println("clicked=============>>>");
        final Dialog_Fragment_conveyance_details dialog_fragment_conveyance_details = new Dialog_Fragment_conveyance_details();
        dialog_fragment_conveyance_details.show(getSupportFragmentManager(), "dialog_fragment_conveyance_details");
    }


    public void open_keyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) ReportListActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                et_search_field.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        et_search_field.requestFocus();
    }


    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) ReportListActivity.this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search_field.getApplicationWindowToken(), 0);
    }


    public void custom_spinner() {
        String[] plants = new String[]{
                "",
                "      DATE(ASC)                \n",
                "      DATE(DESC)               \n",
                "      DEVIATION TIME(ASC)      \n",
                "      DEVIATION TIME(DESC)     \n",
                "      REMOVE FILTER            \n"
        };


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plants) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else if (position == selected_pos) {
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.drawer_background));
                    tv.setTextSize(13F);
                } else {
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(13F);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sp_sort_items.setAdapter(spinnerArrayAdapter);

        sp_sort_items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                System.out.println("spinner pos=======>>>" + position);
                selected_pos = position;
                if (position == 1) {
                   /* field_name = "sort_name";
                    order_by = "asc";
                    page = 1;
                    get_vms_visit_list();*/
                } else if (position == 2) {

                } else if (position == 3) {

                } else if (position == 4) {

                } else if (position == 5) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
