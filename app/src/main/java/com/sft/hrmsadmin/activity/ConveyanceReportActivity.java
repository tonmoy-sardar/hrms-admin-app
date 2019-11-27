package com.sft.hrmsadmin.activity;

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
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.ProgressBarDialog;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.adapter.Adapter_conveyance_report_list;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_conveyance_details;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_conveyance;
import com.sft.hrmsadmin.utils.MessageDialog;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConveyanceReportActivity extends MainActivity implements Adapter_conveyance_report_list.OnItemClick {

    public View view;
    Adapter_conveyance_report_list adapter_conveyance_report_list;
    RecyclerView rv_attendance_conveyance;
    ArrayList<JSONObject> arrayList_conveyance;
    LinearLayout ll_search_btn, ll_search_sort_section, ll_search_field, ll_sort_field, ll_filter_btn;
    EditText et_search_field;
    ImageView iv_search_close;
    int selected_pos = 0;
    Spinner sp_sort_items;
    String field_name = "", order_by = "", start_date = "", end_date = "", department = "", designation = "";

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    int page = 1;
    String token = "";
    MySharedPreferance mySharedPreferance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_conveyance_report, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("CONVEYANCE REPORT");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);

        rv_attendance_conveyance = findViewById(R.id.rv_attendance_conveyance);
        ll_search_btn = findViewById(R.id.ll_search_btn);
        ll_search_sort_section = findViewById(R.id.ll_search_sort_section);
        ll_search_field = findViewById(R.id.ll_search_field);
        et_search_field = findViewById(R.id.et_search_field);
        iv_search_close = findViewById(R.id.iv_search_close);
        sp_sort_items = findViewById(R.id.sp_sort_items);
        ll_sort_field = findViewById(R.id.ll_sort_field);
        ll_filter_btn = findViewById(R.id.ll_filter_btn);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        retrofitResponse = new RetrofitResponse(this);
        //retrofitResponse = new RetrofitResponse(getApplicationContext(), getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(this);
        token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        //token = "bee8ced4601fc53d7e1bfc79981a925234e0678a";


        arrayList_conveyance = new ArrayList<JSONObject>();
        adapter_conveyance_report_list = new Adapter_conveyance_report_list(arrayList_conveyance, this);
        adapter_conveyance_report_list.setOnItemListener(this);
        adapter_conveyance_report_list.paginate(new Adapter_conveyance_report_list.UpdateData() {
            @Override
            public void get(int position) {
                page = page + 1;
                get_attendance_conveyance_report_list();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_attendance_conveyance.setLayoutManager(layoutManager);
        rv_attendance_conveyance.setHasFixedSize(true);
        rv_attendance_conveyance.setAdapter(adapter_conveyance_report_list);


        rv_attendance_conveyance.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (arrayList_conveyance.size() == 0) {
                    adapter_conveyance_report_list.updateAgain(false);
                } else {
                    adapter_conveyance_report_list.updateAgain(true);
                }
            }
        });


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
                page = 1;
                get_attendance_conveyance_report_list();
            }
        });


        et_search_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    if (et_search_field.getText().toString().length() > 0) {
                        hideKeyBoard();
                        page = 1;
                        get_attendance_conveyance_report_list();
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
                final Dialog_Fragment_filter_conveyance dialog_fragment_filter_conveyance = new Dialog_Fragment_filter_conveyance();
                dialog_fragment_filter_conveyance.setData(start_date, end_date, department, designation);
                dialog_fragment_filter_conveyance.setOnDialogListener(new Dialog_Fragment_filter_conveyance.OnItemClickDialog() {
                    @Override
                    public void onItemClick(String from_date, String to_date, String department_id, String designation_id) {
                        start_date = from_date;
                        end_date = to_date;
                        department = department_id;
                        designation = designation_id;
                        page = 1;
                        get_attendance_conveyance_report_list();
                    }
                });
                dialog_fragment_filter_conveyance.show(getSupportFragmentManager(), "dialog_fragment_filter_conveyance");
            }
        });


        custom_spinner();
        get_attendance_conveyance_report_list();
    }


    public void get_attendance_conveyance_report_list() {
        adapter_conveyance_report_list.loader(true);

        retrofitResponse.getWebServiceResponse(serviceClient.get_attendance_conveyance_report_list("Token " + token, "application/json", page,
                et_search_field.getText().toString(), start_date, end_date, department, designation, field_name, order_by, 1),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        adapter_conveyance_report_list.loader(false);
                        adapter_conveyance_report_list.updateAgain(false);
                        if (jsonObject != null) {
                            if (page == 1) {
                                arrayList_conveyance.clear();
                            }
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    arrayList_conveyance.add(results.getJSONObject(i));
                                }
                                adapter_conveyance_report_list.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                adapter_conveyance_report_list.loader(false);
                                adapter_conveyance_report_list.updateAgain(false);
                            }
                        }
                    }
                });
    }


    @Override
    public void onItemClick(int pos) {
        System.out.println("clicked=============>>>");
    }

    @Override
    public void onItemClickApproval(int pos, String approval_status, String conveyance_expense) {

    }

    @Override
    public void onItemClickDetails(int pos) {
        System.out.println("clicked=============>>>");
        final Dialog_Fragment_conveyance_details dialog_fragment_conveyance_details = new Dialog_Fragment_conveyance_details();
        dialog_fragment_conveyance_details.setData(arrayList_conveyance.get(pos));
        dialog_fragment_conveyance_details.show(getSupportFragmentManager(), "dialog_fragment_conveyance_details");
    }


    public void open_keyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) ConveyanceReportActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                et_search_field.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        et_search_field.requestFocus();
    }


    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) ConveyanceReportActivity.this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search_field.getApplicationWindowToken(), 0);
    }


    public void custom_spinner() {
        String[] plants = new String[]{
                "",
                "      DATE(ASC)                 \n",
                "      DATE(DESC)                \n",
                "      DEVIATION START(ASC)      \n",
                "      DEVIATION START(DESC)     \n",
                "      DEVIATION END(ASC)        \n",
                "      DEVIATION END(DESC)       \n",
                "      DEVIATION TIME(ASC)       \n",
                "      DEVIATION TIME(DESC)      \n",
                "      REMOVE FILTER             \n"
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
                    field_name = "date";
                    order_by = "asc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 2) {
                    field_name = "date";
                    order_by = "desc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 3) {
                    field_name = "duration_start";
                    order_by = "asc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 4) {
                    field_name = "duration_start";
                    order_by = "desc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 5) {
                    field_name = "duration_end";
                    order_by = "asc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 6) {
                    field_name = "duration_end";
                    order_by = "desc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 7) {
                    field_name = "duration";
                    order_by = "asc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 8) {
                    field_name = "duration";
                    order_by = "desc";
                    page = 1;
                    get_attendance_conveyance_report_list();
                } else if (position == 9) {
                    field_name = "";
                    order_by = "";
                    page = 1;
                    get_attendance_conveyance_report_list();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    MessageDialog messageDialogPopup;

    public void showMessagePopup(String msg) {
        messageDialogPopup = new MessageDialog(this);
        messageDialogPopup.setTitle(msg);
        messageDialogPopup.show();
    }


}
