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
import android.widget.RelativeLayout;
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
import com.sft.hrmsadmin.adapter.Adapter_leave_report_list;
import com.sft.hrmsadmin.adapter.Adapter_leave_report_list;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_conveyance_details;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_conveyance;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_leave_approval;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_leave_report;
import com.sft.hrmsadmin.utils.MessageDialog;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeaveReportListActivity extends MainActivity implements Adapter_leave_report_list.OnItemClick {

    public View view;
    Adapter_leave_report_list adapter_leave_report_list;
    RecyclerView rv_approval_list;
    ArrayList<JSONObject> arrayList_leave_approval;
    LinearLayout ll_search_btn, ll_search_sort_section, ll_search_field, ll_sort_field, ll_filter_btn;
    EditText et_search_field;
    ImageView iv_search_close;
    int selected_pos = 0;
    Spinner sp_sort_items;
    String field_name = "", order_by = "", request_types = "", start_date = "", end_date = "", approval_filter = "";
    int select_type = 1; //1= advance leave, 2 = normal leave
    RelativeLayout rl_advance_leave, rl_normal_leave;
    TextView tv_advance_leave, tv_normal_leave;
    View v_advance_leave, v_normal_leave;

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    int page = 1;
    String token = "";
    MySharedPreferance mySharedPreferance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_leave_report_list, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("LEAVE REPORT");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);

        rv_approval_list = findViewById(R.id.rv_approval_list);
        ll_search_btn = findViewById(R.id.ll_search_btn);
        ll_search_sort_section = findViewById(R.id.ll_search_sort_section);
        ll_search_field = findViewById(R.id.ll_search_field);
        et_search_field = findViewById(R.id.et_search_field);
        iv_search_close = findViewById(R.id.iv_search_close);
        sp_sort_items = findViewById(R.id.sp_sort_items);
        ll_sort_field = findViewById(R.id.ll_sort_field);
        ll_filter_btn = findViewById(R.id.ll_filter_btn);
        rl_advance_leave = findViewById(R.id.rl_advance_leave);
        rl_normal_leave = findViewById(R.id.rl_normal_leave);
        tv_advance_leave = findViewById(R.id.tv_advance_leave);
        tv_normal_leave = findViewById(R.id.tv_normal_leave);
        v_advance_leave = findViewById(R.id.v_advance_leave);
        v_normal_leave = findViewById(R.id.v_normal_leave);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        retrofitResponse = new RetrofitResponse(this);
        //retrofitResponse = new RetrofitResponse(getApplicationContext(), getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(this);
        token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        //token = "bee8ced4601fc53d7e1bfc79981a925234e0678a";


        arrayList_leave_approval = new ArrayList<JSONObject>();
        adapter_leave_report_list = new Adapter_leave_report_list(arrayList_leave_approval, this);
        adapter_leave_report_list.setOnItemListener(this);
        adapter_leave_report_list.paginate(new Adapter_leave_report_list.UpdateData() {
            @Override
            public void get(int position) {
                page = page + 1;
                if (select_type == 1) {
                    get_attendance_advance_leave_report();
                } else {
                    get_attendance_normal_leave_report();
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_approval_list.setLayoutManager(layoutManager);
        rv_approval_list.setHasFixedSize(true);
        rv_approval_list.setAdapter(adapter_leave_report_list);


        rv_approval_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (arrayList_leave_approval.size() == 0) {
                    adapter_leave_report_list.updateAgain(false);
                } else {
                    adapter_leave_report_list.updateAgain(true);
                }
            }
        });


        rl_advance_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_advance_leave.setTextColor(ContextCompat.getColor(LeaveReportListActivity.this, R.color.color_black));
                tv_normal_leave.setTextColor(ContextCompat.getColor(LeaveReportListActivity.this, R.color.light_text_color));

                v_advance_leave.setVisibility(View.VISIBLE);
                v_normal_leave.setVisibility(View.GONE);

                select_type = 1;
                clear_local_value();
                arrayList_leave_approval.clear();
                adapter_leave_report_list.notifyDataSetChanged();
                get_attendance_advance_leave_report();

            }
        });


        rl_normal_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_advance_leave.setTextColor(ContextCompat.getColor(LeaveReportListActivity.this, R.color.light_text_color));
                tv_normal_leave.setTextColor(ContextCompat.getColor(LeaveReportListActivity.this, R.color.color_black));

                v_advance_leave.setVisibility(View.GONE);
                v_normal_leave.setVisibility(View.VISIBLE);

                select_type = 2;
                clear_local_value();
                arrayList_leave_approval.clear();
                adapter_leave_report_list.notifyDataSetChanged();
                get_attendance_normal_leave_report();
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
                if (select_type == 1) {
                    get_attendance_advance_leave_report();
                } else {
                    get_attendance_normal_leave_report();
                }
            }
        });


        et_search_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    if (et_search_field.getText().toString().length() > 0) {
                        hideKeyBoard();
                        page = 1;
                        if (select_type == 1) {
                            get_attendance_advance_leave_report();
                        } else {
                            get_attendance_normal_leave_report();
                        }
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
                final Dialog_Fragment_filter_leave_report dialog_fragment_filter_leave_report = new Dialog_Fragment_filter_leave_report();
                dialog_fragment_filter_leave_report.setOnDialogListener(new Dialog_Fragment_filter_leave_report.OnItemClickDialog() {
                    @Override
                    public void onItemClick(String from_date, String to_date, String leave_type, String approved_type) {
                        start_date = from_date;
                        end_date = to_date;
                        request_types = leave_type;
                        approval_filter = approved_type;
                        page = 1;
                        if (select_type == 1) {
                            get_attendance_advance_leave_report();
                        } else {
                            get_attendance_normal_leave_report();
                        }
                    }
                });
                dialog_fragment_filter_leave_report.show(getSupportFragmentManager(), "dialog_fragment_filter_conveyance");
            }
        });


        custom_spinner();
        if (select_type == 1) {
            get_attendance_advance_leave_report();
        } else {
            get_attendance_normal_leave_report();
        }
    }


    public void get_attendance_advance_leave_report() {
        adapter_leave_report_list.loader(true);

        retrofitResponse.getWebServiceResponse(serviceClient.get_attendance_advance_leave_report("Token " + token, "application/json", page,
                et_search_field.getText().toString(), start_date, end_date, request_types, approval_filter, field_name, order_by,1),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        adapter_leave_report_list.loader(false);
                        adapter_leave_report_list.updateAgain(false);
                        if (jsonObject != null) {
                            if (page == 1) {
                                rv_approval_list.scrollToPosition(0);
                            }
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    arrayList_leave_approval.add(results.getJSONObject(i));
                                }
                                adapter_leave_report_list.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                adapter_leave_report_list.loader(false);
                                adapter_leave_report_list.updateAgain(false);
                            }
                        }
                    }
                });
    }


    public void get_attendance_normal_leave_report() {
        adapter_leave_report_list.loader(true);

        retrofitResponse.getWebServiceResponse(serviceClient.get_attendance_approval_report("Token " + token, "application/json", page,
                et_search_field.getText().toString(), start_date, end_date, request_types, approval_filter, field_name, order_by,1),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        adapter_leave_report_list.loader(false);
                        adapter_leave_report_list.updateAgain(false);
                        if (jsonObject != null) {
                            if (page == 1) {
                                arrayList_leave_approval.clear();
                                adapter_leave_report_list.notifyDataSetChanged();
                            }
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    arrayList_leave_approval.add(results.getJSONObject(i));
                                }
                                adapter_leave_report_list.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                adapter_leave_report_list.loader(false);
                                adapter_leave_report_list.updateAgain(false);
                            }
                        }
                    }
                });
    }



    @Override
    public void onItemClick(int pos) {
        System.out.println("clicked=============>>>");
        final Dialog_Fragment_conveyance_details dialog_fragment_conveyance_details = new Dialog_Fragment_conveyance_details();
        dialog_fragment_conveyance_details.show(getSupportFragmentManager(), "dialog_fragment_conveyance_details");
    }

    @Override
    public void onItemClickApproval(int pos, String approval_status, String add_remark) {

    }


    public void open_keyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) LeaveReportListActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                et_search_field.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        et_search_field.requestFocus();
    }


    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) LeaveReportListActivity.this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search_field.getApplicationWindowToken(), 0);
    }


    public void custom_spinner() {
        String[] plants = new String[]{
                "",
                "      DATE(ASC)                 \n",
                "      DATE(DESC)                \n",
                "      START DATE(ASC)           \n",
                "      START DATE(DESC)          \n",
                "      END DATE(ASC)             \n",
                "      END DATE(DESC)            \n",
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
                    field_name = "date_of_application";
                    order_by = "asc";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                } else if (position == 2) {
                    field_name = "date_of_application";
                    order_by = "desc";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                } else if (position == 3) {
                    field_name = "start_date";
                    order_by = "asc";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                } else if (position == 4) {
                    field_name = "start_date";
                    order_by = "desc";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                } else if (position == 5) {
                    field_name = "end_date";
                    order_by = "asc";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                } else if (position == 6) {
                    field_name = "end_date";
                    order_by = "desc";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                } else if (position == 7) {
                    field_name = "";
                    order_by = "";
                    page = 1;
                    if (select_type == 1) {
                        get_attendance_advance_leave_report();
                    } else {
                        get_attendance_normal_leave_report();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void clear_local_value() {
        et_search_field.setText("");
        start_date = "";
        end_date = "";
        request_types = "";
        approval_filter = "";
        field_name = "";
        order_by = "";
    }


    MessageDialog messageDialogPopup;

    public void showMessagePopup(String msg) {
        messageDialogPopup = new MessageDialog(this);
        messageDialogPopup.setTitle(msg);
        messageDialogPopup.show();
    }

}
