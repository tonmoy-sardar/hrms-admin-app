package com.sft.hrmsadmin.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.sft.hrmsadmin.R;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitResponse;
import com.sft.hrmsadmin.RetrofitServiceClass.RetrofitServiceGenerator;
import com.sft.hrmsadmin.RetrofitServiceClass.ServiceClient;
import com.sft.hrmsadmin.adapter.Adapter_department_list;
import com.sft.hrmsadmin.adapter.Adapter_designation_list;
import com.sft.hrmsadmin.adapter.Adapter_filter_by_list;
import com.sft.hrmsadmin.utils.MessageDialog;
import com.sft.hrmsadmin.utils.MySharedPreferance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by USER on 28-Oct-16.
 */

public class Dialog_Fragment_filter_conveyance extends DialogFragment implements Adapter_department_list.OnItemClick, Adapter_designation_list.OnItemClick,
        CalendarDatePickerDialogFragment.OnDateSetListener {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_back;
    TextView tv_submit, tv_fr_from_date, tv_fr_to_date;
    OnItemClickDialog itemClickDialog;
    RecyclerView rv_filter_department, rv_filter_designation;
    Adapter_department_list adapter_department_list;
    Adapter_designation_list adapter_designation_list;
    ArrayList<JSONObject> arrayList_designation;
    ArrayList<JSONObject> arrayList_department;

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    String token = "", date_type = "", department_id = "", designation_id = "";
    MySharedPreferance mySharedPreferance;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.DialogCustomTheme_image);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogCustomTheme_image;
        System.out.println("Dialog ONcreate============>");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_fragment_filter_conveyance, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());

        iv_df_back = v.findViewById(R.id.iv_df_back);
        tv_submit = v.findViewById(R.id.tv_submit);
        tv_fr_from_date = v.findViewById(R.id.tv_fr_from_date);
        tv_fr_to_date = v.findViewById(R.id.tv_fr_to_date);
        rv_filter_department = v.findViewById(R.id.rv_filter_department);
        rv_filter_designation = v.findViewById(R.id.rv_filter_designation);

        rv_filter_department.setNestedScrollingEnabled(false);
        rv_filter_designation.setNestedScrollingEnabled(false);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        //retrofitResponse = new RetrofitResponse(getActivity());
        retrofitResponse = new RetrofitResponse(getActivity(), getActivity().getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(getActivity());
        //token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        token = "bee8ced4601fc53d7e1bfc79981a925234e0678a";


        arrayList_department = new ArrayList<JSONObject>();
        adapter_department_list = new Adapter_department_list(arrayList_department, getActivity());
        adapter_department_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManagerDepartment = new LinearLayoutManager(getActivity());
        rv_filter_department.setLayoutManager(layoutManagerDepartment);
        rv_filter_department.setHasFixedSize(true);
        rv_filter_department.setAdapter(adapter_department_list);


        arrayList_designation = new ArrayList<JSONObject>();
        adapter_designation_list = new Adapter_designation_list(arrayList_designation, getActivity());
        adapter_designation_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_filter_designation.setLayoutManager(layoutManager);
        rv_filter_designation.setHasFixedSize(true);
        rv_filter_designation.setAdapter(adapter_designation_list);


        tv_fr_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = "1";
                MonthAdapter.CalendarDay DEFAULT_START_DATE = new MonthAdapter.CalendarDay(1800, Calendar.DECEMBER, 31);
                Calendar calendarto = Calendar.getInstance();
                calendarto.add(Calendar.DATE, +30);
                MonthAdapter.CalendarDay DEFAULT_END_DATE = new MonthAdapter.CalendarDay(5000, Calendar.DECEMBER, 31);
                //MonthAdapter.CalendarDay DEFAULT_END_DATE = new MonthAdapter.CalendarDay(calendarto);
                CalendarDatePickerDialogFragment dp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(Dialog_Fragment_filter_conveyance.this)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDateRange(DEFAULT_START_DATE, DEFAULT_END_DATE)
                        .setThemeLight();
                dp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });


        tv_fr_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_type = "2";
                MonthAdapter.CalendarDay DEFAULT_START_DATE = new MonthAdapter.CalendarDay(1800, Calendar.DECEMBER, 31);
                Calendar calendarto = Calendar.getInstance();
                calendarto.add(Calendar.DATE, +30);
                MonthAdapter.CalendarDay DEFAULT_END_DATE = new MonthAdapter.CalendarDay(5000, Calendar.DECEMBER, 31);
                //MonthAdapter.CalendarDay DEFAULT_END_DATE = new MonthAdapter.CalendarDay(calendarto);
                CalendarDatePickerDialogFragment dp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(Dialog_Fragment_filter_conveyance.this)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDateRange(DEFAULT_START_DATE, DEFAULT_END_DATE)
                        .setThemeLight();
                dp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });




        iv_df_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (tv_fr_from_date.getText().toString().length() > 1 && tv_fr_to_date.getText().toString().length() > 1) {
                        if (CheckDates(tv_fr_from_date.getText().toString(), tv_fr_to_date.getText().toString())) {
                            if (itemClickDialog != null) {
                                itemClickDialog.onItemClick(tv_fr_from_date.getText().toString(), tv_fr_to_date.getText().toString(), department_id, designation_id);
                            }
                            dismiss();
                        }
                    } else {
                        if (itemClickDialog != null) {
                            itemClickDialog.onItemClick(tv_fr_from_date.getText().toString(), tv_fr_to_date.getText().toString(), department_id, designation_id);
                        }
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        get_t_core_department_add();


        return v;
    }


    public void get_t_core_department_add() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_t_core_department_add("Token " + token),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            JSONArray result = jsonObject.getJSONArray("result");
                            if (result.length() > 0) {
                                for (int i = 0; i < result.length(); i++) {
                                    arrayList_department.add(result.getJSONObject(i));
                                }
                                adapter_department_list.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        get_t_core_designation_add();
                    }
                });
    }


    public void get_t_core_designation_add() {

        retrofitResponse.getWebServiceResponse(serviceClient.get_t_core_designation_add("Token " + token),
                new RetrofitResponse.DataFetchResult() {
                    @Override
                    public void onDataFetchComplete(JSONObject jsonObject) {
                        try {
                            JSONArray result = jsonObject.getJSONArray("result");
                            if (result.length() > 0) {
                                for (int i = 0; i < result.length(); i++) {
                                    arrayList_designation.add(result.getJSONObject(i));
                                }
                                adapter_designation_list.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        if (date_type.equalsIgnoreCase("1")) {
            tv_fr_from_date.setText(changeDate((monthOfYear + 1) + "/" + dayOfMonth + "/" + year));
        } else {
            tv_fr_to_date.setText(changeDate((monthOfYear + 1) + "/" + dayOfMonth + "/" + year));
        }
    }


    private String changeDate(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            final Date dateObj = sdf.parse(date);
            return new SimpleDateFormat("yyyy-MM-dd").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean CheckDates(String d1, String d2) {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        boolean b = false;
        try {
            if (dfDate.parse(d1).before(dfDate.parse(d2))) {
                b = true;//If start date is before end date
            } else if (dfDate.parse(d1).equals(dfDate.parse(d2))) {
                b = true;//If two dates are equal
            } else {
                b = false; //If start date is after the end date
                showMessagePopup("End date must be greater than start date.");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }


    @Override
    public void onItemClickDepartment(int pos) {
        try {
            department_id = arrayList_department.get(pos).getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickDesignation(int pos) {
        try {
            designation_id = arrayList_designation.get(pos).getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setOnDialogListener(OnItemClickDialog itemClickDialog) {
        this.itemClickDialog = itemClickDialog;
    }


    public interface OnItemClickDialog {
        void onItemClick(String from_date, String to_date, String department_id, String designation_id);
    }


    MessageDialog messageDialogPopup;

    public void showMessagePopup(String msg) {
        messageDialogPopup = new MessageDialog(getActivity());
        messageDialogPopup.setTitle(msg);
        messageDialogPopup.show();
    }


}
