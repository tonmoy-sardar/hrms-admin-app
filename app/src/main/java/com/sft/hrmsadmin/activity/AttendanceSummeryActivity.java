package com.sft.hrmsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.ProgressBarDialog;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.adapter.AttendanceSummaryAdapter;
import com.sft.hrmsadmin.adapter.EmployeeArrayAdapter;
import com.sft.hrmsadmin.adapter.EmployeeDetailAdapter;
import com.sft.hrmsadmin.adapter.MonthsAdapter;
import com.sft.hrmsadmin.pojo_calsses.AttendanceSummaryMonths;
import com.sft.hrmsadmin.pojo_calsses.EmployeeDetail;
import com.sft.hrmsadmin.pojo_calsses.employee_detail.EmployeeDetailsPojo;
import com.sft.hrmsadmin.pojo_calsses.employee_detail.Result;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceSummeryActivity extends MainActivity implements EmployeeDetailAdapter.OnItemClickListenerEmployee,MonthsAdapter.OnItemClickListener {

    public View view;
    ArrayList<JSONObject> arrayList_attendance_summery = new ArrayList<>();
    ArrayList<JSONObject> arrayList_employee_list;

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    String token = "";
    MySharedPreferance mySharedPreferance;

    AttendanceSummaryAdapter attendanceSummaryAdapter;

    RecyclerView rvSummaryList,rvMonths;
    LinearLayoutManager linearLayoutManager;

    List<Result> resultListEmployee = new ArrayList<>();

    EmployeeDetailAdapter employeeDetailAdapter;
    List<AttendanceSummaryMonths> newListMonths = new ArrayList<>();





    TextView tvSelectEmployee,tvSelectMonth;
    LinearLayout llDropDownEmployee,llDropDown;
    RecyclerView rvEmployee;

    int employee_id = 0;
    String year = "";

    MonthsAdapter monthsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_attendance_summery, null);
        addContentView(view);
        System.out.println("className=======>>>" + getClass().getSimpleName());
        tv_universal_header.setText("ATTENDANCE SUMMERY");
        img_topbar_menu.setVisibility(View.GONE);
        img_topbar_back.setVisibility(View.VISIBLE);


        rvSummaryList = findViewById(R.id.rvSummaryList);
        rvMonths = findViewById(R.id.rvMonths);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        //retrofitResponse = new RetrofitResponse(this);
        retrofitResponse = new RetrofitResponse(this, getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(this);
        //token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        //token = "bee8ced4601fc53d7e1bfc79981a925234e0678a";
        token = "887e7ebcd525674733e8496ef2a991234d63b1e2";


        tvSelectEmployee = findViewById(R.id.tvSelectEmployee);
        tvSelectMonth = findViewById(R.id.tvSelectMonth);
        llDropDownEmployee = findViewById(R.id.llDropDownEmployee);
        llDropDown = findViewById(R.id.llDropDown);
        rvEmployee = findViewById(R.id.rvEmployee);


        Date d = new Date();
        System.out.println("Year: "+getYear(d)+" "+getMonth(d));
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        System.out.println("monthName: "+monthname);


        tvSelectEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llDropDownEmployee.getVisibility() == View.GONE) {

                    llDropDownEmployee.setVisibility(View.VISIBLE);
                } else {

                    llDropDownEmployee.setVisibility(View.GONE);
                }
            }
        });



        tvSelectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llDropDown.getVisibility() == View.GONE) {

                    llDropDown.setVisibility(View.VISIBLE);
                } else {

                    llDropDown.setVisibility(View.GONE);
                }
            }
        });


        get_employee_list_wo_pagination();

        year = String.valueOf(getYear(d));

        setAdapter();
        //get_attendance_admin_summary_list();


        for (int i = 0; i < getMonths().size(); i++){

            if (!getMonths().get(i).getMonth().equalsIgnoreCase(monthname)){

                AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
                attendanceSummaryMonths.setId(getMonths().get(i).getId());
                attendanceSummaryMonths.setMonth(getMonths().get(i).getMonth());
                attendanceSummaryMonths.setYear(getMonths().get(i).getYear());

                newListMonths.add(attendanceSummaryMonths);

            }else {

                AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
                attendanceSummaryMonths.setId(getMonths().get(i).getId());
                attendanceSummaryMonths.setMonth(getMonths().get(i).getMonth());
                attendanceSummaryMonths.setYear(getMonths().get(i).getYear());

                newListMonths.add(attendanceSummaryMonths);
                break;
            }

        }

        System.out.println("SizeNewMonthList: "+newListMonths.size());

        setAdapterForMonths();






    }

    private void setAdapterForMonths() {

        monthsAdapter = new MonthsAdapter(this, newListMonths);
        monthsAdapter.setOnItemClickListener(this);
        //monthsAdapter.setOnItemClickListener(this);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(AttendanceSummeryActivity.this, RecyclerView.VERTICAL, false);
        rvMonths.setHasFixedSize(true);
        rvMonths.setLayoutManager(linearLayoutManager);
        rvMonths.setAdapter(monthsAdapter);

    }


    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }


    private void setAdapter() {


        employeeDetailAdapter = new EmployeeDetailAdapter(this, resultListEmployee);
        employeeDetailAdapter.setOnItemClickListenerEmployee(this);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(AttendanceSummeryActivity.this, RecyclerView.VERTICAL, false);
        rvEmployee.setHasFixedSize(true);
        rvEmployee.setLayoutManager(linearLayoutManager);
        rvEmployee.setAdapter(employeeDetailAdapter);





        attendanceSummaryAdapter = new AttendanceSummaryAdapter(AttendanceSummeryActivity.this,
                arrayList_attendance_summery);
        linearLayoutManager = new LinearLayoutManager(this);
        rvSummaryList.setLayoutManager(linearLayoutManager);
        rvSummaryList.setHasFixedSize(true);
        rvSummaryList.setAdapter(attendanceSummaryAdapter);
    }


    public void get_attendance_admin_summary_list() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_attendance_admin_summary_list("Token " +
                        token, "application/json",
                employee_id, yearInt, monthID),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        if (jsonObject != null) {
                            try {
                                JSONArray results = jsonObject.getJSONArray("result");
                                System.out.println("results: "+results);
                                for (int i = 0; i < results.length(); i++) {
                                    arrayList_attendance_summery.add(results.getJSONObject(i));
                                }
                                attendanceSummaryAdapter.notifyDataSetChanged();

                                //get_employee_list_wo_pagination();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }




    public List<AttendanceSummaryMonths> getMonths(){


        List<AttendanceSummaryMonths> listMonths = new ArrayList<>();

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("1");
            attendanceSummaryMonths.setMonth("January");
            attendanceSummaryMonths.setYear(year);
            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("2");
            attendanceSummaryMonths.setMonth("February");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("3");
            attendanceSummaryMonths.setMonth("March");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("4");
            attendanceSummaryMonths.setMonth("April");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("5");
            attendanceSummaryMonths.setMonth("May");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("6");
            attendanceSummaryMonths.setMonth("June");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("7");
            attendanceSummaryMonths.setMonth("July");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("8");
            attendanceSummaryMonths.setMonth("August");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("9");
            attendanceSummaryMonths.setMonth("September");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("10");
            attendanceSummaryMonths.setMonth("October");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("11");
            attendanceSummaryMonths.setMonth("November");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        {
            AttendanceSummaryMonths attendanceSummaryMonths = new AttendanceSummaryMonths();
            attendanceSummaryMonths.setId("12");
            attendanceSummaryMonths.setMonth("December");
            attendanceSummaryMonths.setYear(year);

            listMonths.add(attendanceSummaryMonths);
        }

        return listMonths;
    }











    public void get_employee_list_wo_pagination() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_employee_list_wo_pagination("Token " + token),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {



                            String responseString = jsonObject.toString();

                            System.out.println("responseString: "+responseString);

                            EmployeeDetailsPojo employeeDetailsPojo;
                            Gson gson = new Gson();

                            employeeDetailsPojo = gson.fromJson(responseString,EmployeeDetailsPojo.class);
                            resultListEmployee.addAll(employeeDetailsPojo.getResult());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void OnItemClick(Integer id, String name) {

        System.out.println("getResL: "+id+" "+name);

        employee_id = id;

        tvSelectEmployee.setText(name);

        llDropDownEmployee.setVisibility(View.GONE);
        tvSelectMonth.setVisibility(View.VISIBLE);
    }


    int monthID;
    int yearInt;

    @Override
    public void OnItemClick(String id, String month, String year) {

        System.out.println("monthsDetails: "+id+" "+month+" "+year);
        monthID = Integer.parseInt(id);
        yearInt = Integer.parseInt(year);
        tvSelectMonth.setText(month+", "+year);
        llDropDown.setVisibility(View.GONE);
        //llDropDown.setVisibility(View.GONE);

        get_attendance_admin_summary_list();

        //todaysDate = "";

    }
}
