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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.ProgressBarDialog;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.adapter.Adapter_conveyance_list;
import com.sft.hrmsadmin.adapter.Adapter_leave_approval_list;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_add_remarks;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_conveyance_details;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_conveyance;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_leave_approval;
import com.sft.hrmsadmin.dialog_fragment.Dialog_Fragment_filter_report;
import com.sft.hrmsadmin.utils.MessageDialog;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConveyanceActivity extends MainActivity implements Adapter_conveyance_list.OnItemClick {

    public View view;
    Adapter_conveyance_list adapter_conveyance_list;
    RecyclerView rv_attendance_conveyance;
    ArrayList<JSONObject> arrayList_conveyance;
    LinearLayout ll_search_btn, ll_search_sort_section, ll_search_field, ll_sort_field, ll_filter_btn,ll_select_all,ll_approval_section;
    EditText et_search_field;
    ImageView iv_search_close;
    int selected_pos = 0;
    Spinner sp_sort_items;
    String field_name = "", order_by = "", start_date = "", end_date = "", department = "", designation = "";
    CheckBox chkbxSelectApproval;
    RadioButton rb_approve,rb_reject;
    JsonArray conveyance_approvals;
    TextView tv_approval_submit;

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    int page = 1;
    String token = "";
    MySharedPreferance mySharedPreferance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_conveyance, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("CONVEYANCE APPROVAL");
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
        ll_select_all = findViewById(R.id.ll_select_all);
        chkbxSelectApproval = findViewById(R.id.chkbxSelectApproval);
        ll_approval_section = findViewById(R.id.ll_approval_section);
        rb_approve = findViewById(R.id.rb_approve);
        rb_reject = findViewById(R.id.rb_reject);
        tv_approval_submit = findViewById(R.id.tv_approval_submit);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        retrofitResponse = new RetrofitResponse(this);
        //retrofitResponse = new RetrofitResponse(getApplicationContext(), getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(this);
        token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);

        set_conveyance_approval_adapter();

       /* arrayList_conveyance = new ArrayList<JSONObject>();
        adapter_conveyance_list = new Adapter_conveyance_list(arrayList_conveyance, this);
        adapter_conveyance_list.setOnItemListener(this);
        adapter_conveyance_list.paginate(new Adapter_conveyance_list.UpdateData() {
            @Override
            public void get(int position) {
                page = page + 1;
                get_attendance_conveyance_approval_list();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_attendance_conveyance.setLayoutManager(layoutManager);
        rv_attendance_conveyance.setHasFixedSize(true);
        rv_attendance_conveyance.setAdapter(adapter_conveyance_list);*/


        rv_attendance_conveyance.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (arrayList_conveyance.size() == 0) {
                    adapter_conveyance_list.updateAgain(false);
                } else {
                    adapter_conveyance_list.updateAgain(true);
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
                set_conveyance_approval_adapter();
                get_attendance_conveyance_approval_list();
            }
        });


        et_search_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    if (et_search_field.getText().toString().length() > 0) {
                        hideKeyBoard();
                        page = 1;
                        set_conveyance_approval_adapter();
                        get_attendance_conveyance_approval_list();
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
                        set_conveyance_approval_adapter();
                        get_attendance_conveyance_approval_list();
                    }
                });
                dialog_fragment_filter_conveyance.show(getSupportFragmentManager(), "dialog_fragment_filter_conveyance");
            }
        });


        ll_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (chkbxSelectApproval.isChecked() == false) {
                        chkbxSelectApproval.setChecked(true);
                        for (int i = 0; i < arrayList_conveyance.size(); i++) {
                            arrayList_conveyance.get(i).put("is_selected", true);
                            ll_approval_section.setVisibility(View.VISIBLE);
                        }
                    } else {
                        chkbxSelectApproval.setChecked(false);
                        for (int i = 0; i < arrayList_conveyance.size(); i++) {
                            arrayList_conveyance.get(i).put("is_selected", false);
                            ll_approval_section.setVisibility(View.GONE);
                        }
                    }
                    adapter_conveyance_list.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        rb_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    conveyance_approvals = new JsonArray();
                    for (int i = 0; i < arrayList_conveyance.size(); i++) {
                        if (arrayList_conveyance.get(i).getBoolean("is_selected") == true) {
                            JsonObject conveyance_approvals_name_obj = new JsonObject();
                            conveyance_approvals_name_obj.addProperty("req_id", arrayList_conveyance.get(i).getInt("id"));
                            conveyance_approvals_name_obj.addProperty("conveyance_approval", "2");
                            conveyance_approvals.add(conveyance_approvals_name_obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        rb_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    conveyance_approvals = new JsonArray();
                    for (int i = 0; i < arrayList_conveyance.size(); i++) {
                        if (arrayList_conveyance.get(i).getBoolean("is_selected") == true) {
                            JsonObject conveyance_approvals_name_obj = new JsonObject();
                            conveyance_approvals_name_obj.addProperty("req_id", arrayList_conveyance.get(i).getInt("id"));
                            conveyance_approvals_name_obj.addProperty("conveyance_approval", "1");
                            conveyance_approvals.add(conveyance_approvals_name_obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        tv_approval_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conveyance_approvals ==null){
                    showMessagePopup("Please Approve/Reject before submit");
                } else {
                    put_attendance_conveyance_approval_multiple();
                }
            }
        });


        custom_spinner();
        get_attendance_conveyance_approval_list();
    }


    public void set_conveyance_approval_adapter() {
        arrayList_conveyance = new ArrayList<JSONObject>();
        adapter_conveyance_list = new Adapter_conveyance_list(arrayList_conveyance, this);
        adapter_conveyance_list.setOnItemListener(this);
        adapter_conveyance_list.paginate(new Adapter_conveyance_list.UpdateData() {
            @Override
            public void get(int position) {
                page = page + 1;
                get_attendance_conveyance_approval_list();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_attendance_conveyance.setLayoutManager(layoutManager);
        rv_attendance_conveyance.setHasFixedSize(true);
        rv_attendance_conveyance.setAdapter(adapter_conveyance_list);
    }


    public void get_attendance_conveyance_approval_list() {
        adapter_conveyance_list.loader(true);

        retrofitResponse.getWebServiceResponse(serviceClient.get_attendance_conveyance_approval_list("Token " + token, "application/json", page,
                et_search_field.getText().toString(), start_date, end_date, department, designation, field_name, order_by,1),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        adapter_conveyance_list.loader(false);
                        adapter_conveyance_list.updateAgain(false);
                        if (jsonObject != null) {
                            if (page == 1) {
                                arrayList_conveyance.clear();
                            }
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    results.getJSONObject(i).put("is_selected", false);
                                    arrayList_conveyance.add(results.getJSONObject(i));
                                }
                                adapter_conveyance_list.notifyDataSetChanged();
                                if (results.length()<1){
                                    showMessagePopup("No data found.");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                adapter_conveyance_list.loader(false);
                                adapter_conveyance_list.updateAgain(false);
                            }
                        }
                    }
                });
    }


    public void put_attendance_conveyance_approval(final int position, String approval_status, String conveyance_expense, int id) {
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog();
        progressBarDialog.show(getSupportFragmentManager(), "progressBarDialog");

       /* JsonObject object = new JsonObject();
        object.addProperty("conveyance_approval", approval_status);
        object.addProperty("approved_expenses", conveyance_expense);
        System.out.println("created jsonobject========>>" + object);*/

        JsonObject object = new JsonObject();
        JsonArray conveyance_approvals = new JsonArray();

        JsonObject conveyance_approvals_name_obj = new JsonObject();
        conveyance_approvals_name_obj.addProperty("req_id", id);
        conveyance_approvals_name_obj.addProperty("conveyance_approval", approval_status);
        conveyance_approvals_name_obj.addProperty("approved_expenses", conveyance_expense);
        conveyance_approvals.add(conveyance_approvals_name_obj);

        object.add("conveyance_approvals", conveyance_approvals);
        System.out.println("object======>>>>" + object.toString());

        retrofitResponse.getWebServiceResponse(serviceClient.put_attendance_conveyance_approval("Token " + token, object),

                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            if (jsonObject != null) {
                               /* arrayList_leave_approval.remove(position);
                                adapter_leave_approval_list.notifyDataSetChanged();*/
                                Toast.makeText(ConveyanceActivity.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                                page = 1;
                                set_conveyance_approval_adapter();
                                get_attendance_conveyance_approval_list();
                            } else {
                                Toast.makeText(ConveyanceActivity.this, "Something went wrong!! Please try again", Toast.LENGTH_LONG).show();
                            }
                            progressBarDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void put_attendance_conveyance_approval_multiple() {
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog();
        progressBarDialog.show(getSupportFragmentManager(), "progressBarDialog");

       /* JsonObject object = new JsonObject();
        object.addProperty("conveyance_approval", approval_status);
        object.addProperty("approved_expenses", conveyance_expense);
        System.out.println("created jsonobject========>>" + object);*/

        JsonObject object = new JsonObject();
        object.add("conveyance_approvals", conveyance_approvals);
        System.out.println("object======>>>>" + object.toString());

        retrofitResponse.getWebServiceResponse(serviceClient.put_attendance_conveyance_approval("Token " + token, object),

                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            if (jsonObject != null) {
                                ll_approval_section.setVisibility(View.GONE);
                                rb_approve.setChecked(false);
                                rb_reject.setChecked(false);
                                arrayList_conveyance.clear();
                                adapter_conveyance_list.notifyDataSetChanged();
                                Toast.makeText(ConveyanceActivity.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                                page = 1;
                                set_conveyance_approval_adapter();
                                get_attendance_conveyance_approval_list();
                            } else {
                                Toast.makeText(ConveyanceActivity.this, "Something went wrong!! Please try again", Toast.LENGTH_LONG).show();
                            }
                            progressBarDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onItemClick(int pos) {
        System.out.println("clicked=============>>>");
    }


    @Override
    public void onItemClickSelectedView(int pos) {
        try {
            for (int i = 0; i < arrayList_conveyance.size(); i++) {
                if (arrayList_conveyance.get(i).getBoolean("is_selected") == false) {
                    chkbxSelectApproval.setChecked(false);
                    break;
                } else {
                    chkbxSelectApproval.setChecked(true);
                }
            }

            for (int i = 0; i < arrayList_conveyance.size(); i++) {
                if (arrayList_conveyance.get(i).getBoolean("is_selected") == true) {
                    ll_approval_section.setVisibility(View.VISIBLE);
                    break;
                } else {
                    ll_approval_section.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onItemClickApproval(int pos, String approval_status, String conveyance_expense) {
        try {
            System.out.println("approval_status=====>>>" + approval_status);
            if (approval_status.equalsIgnoreCase("")) {
                showMessagePopup("Please Approve/Reject before submit");
            } else {
                put_attendance_conveyance_approval(pos, approval_status, conveyance_expense, arrayList_conveyance.get(pos).getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                (InputMethodManager) ConveyanceActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                et_search_field.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        et_search_field.requestFocus();
    }


    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) ConveyanceActivity.this.getSystemService(
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
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 2) {
                    field_name = "date";
                    order_by = "desc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 3) {
                    field_name = "duration_start";
                    order_by = "asc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 4) {
                    field_name = "duration_start";
                    order_by = "desc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 5) {
                    field_name = "duration_end";
                    order_by = "asc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 6) {
                    field_name = "duration_end";
                    order_by = "desc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 7) {
                    field_name = "duration";
                    order_by = "asc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 8) {
                    field_name = "duration";
                    order_by = "desc";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
                } else if (position == 9) {
                    field_name = "";
                    order_by = "";
                    page = 1;
                    set_conveyance_approval_adapter();
                    get_attendance_conveyance_approval_list();
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
