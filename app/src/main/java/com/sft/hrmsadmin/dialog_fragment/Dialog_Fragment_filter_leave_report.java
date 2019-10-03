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

public class Dialog_Fragment_filter_leave_report extends DialogFragment implements Adapter_department_list.OnItemClick, Adapter_designation_list.OnItemClick,
        CalendarDatePickerDialogFragment.OnDateSetListener {

    Dialog dialog;
    View v;
    Animation animation_zoom_in;
    Animation slide_out_buttom;
    ImageView iv_df_back;
    TextView tv_submit, tv_fr_from_date, tv_fr_to_date;
    OnItemClickDialog itemClickDialog;
    RecyclerView rv_filter_leave_type, rv_filter_approved_type;
    Adapter_department_list adapter_leave_type_list;
    Adapter_designation_list adapter_approved_type_list;
    ArrayList<JSONObject> arrayList_leave_type;
    ArrayList<JSONObject> arrayList_approved_type;

    RetrofitServiceGenerator retrofitServiceGenerator;
    ServiceClient serviceClient;
    RetrofitResponse retrofitResponse;
    String token = "", date_type = "", leave_type = "", approved_type = "";
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
        v = inflater.inflate(R.layout.dialog_fragment_filter_leave_report, container, false);
        animation_zoom_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        slide_out_buttom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_bottom);
        System.out.println("Current CLASS===>>>" + getClass().getSimpleName());

        iv_df_back = v.findViewById(R.id.iv_df_back);
        tv_submit = v.findViewById(R.id.tv_submit);
        tv_fr_from_date = v.findViewById(R.id.tv_fr_from_date);
        tv_fr_to_date = v.findViewById(R.id.tv_fr_to_date);
        rv_filter_leave_type = v.findViewById(R.id.rv_filter_leave_type);
        rv_filter_approved_type = v.findViewById(R.id.rv_filter_approved_type);

        rv_filter_leave_type.setNestedScrollingEnabled(false);
        rv_filter_approved_type.setNestedScrollingEnabled(false);


        retrofitServiceGenerator = new RetrofitServiceGenerator();
        serviceClient = retrofitServiceGenerator.createService(ServiceClient.class);
        //retrofitResponse = new RetrofitResponse(getActivity());
        retrofitResponse = new RetrofitResponse(getActivity(), getActivity().getSupportFragmentManager());

        mySharedPreferance = new MySharedPreferance(getActivity());
        token = mySharedPreferance.getPreferancceString(mySharedPreferance.login_token);
        //token = "bee8ced4601fc53d7e1bfc79981a925234e0678a";


        arrayList_leave_type = new ArrayList<JSONObject>();
        adapter_leave_type_list = new Adapter_department_list(arrayList_leave_type, getActivity());
        adapter_leave_type_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManagerDepartment = new LinearLayoutManager(getActivity());
        rv_filter_leave_type.setLayoutManager(layoutManagerDepartment);
        rv_filter_leave_type.setHasFixedSize(true);
        rv_filter_leave_type.setAdapter(adapter_leave_type_list);
        loadFilterData();


        arrayList_approved_type = new ArrayList<JSONObject>();
        adapter_approved_type_list = new Adapter_designation_list(arrayList_approved_type, getActivity());
        adapter_approved_type_list.setOnItemListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_filter_approved_type.setLayoutManager(layoutManager);
        rv_filter_approved_type.setHasFixedSize(true);
        rv_filter_approved_type.setAdapter(adapter_approved_type_list);
        loadFilterApprovedData();


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
                        .setOnDateSetListener(Dialog_Fragment_filter_leave_report.this)
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
                        .setOnDateSetListener(Dialog_Fragment_filter_leave_report.this)
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
                                itemClickDialog.onItemClick(tv_fr_from_date.getText().toString(), tv_fr_to_date.getText().toString(), leave_type, approved_type);
                            }
                            dismiss();
                        }
                    } else {
                        if (itemClickDialog != null) {
                            itemClickDialog.onItemClick(tv_fr_from_date.getText().toString(), tv_fr_to_date.getText().toString(), leave_type, approved_type);
                        }
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return v;
    }


    public void loadFilterData() {
        try {
            arrayList_leave_type.clear();
            arrayList_leave_type.add(new JSONObject().put("id", 1).put("cd_name", "Earn Leave").put("request_type", "EL").put("status", false));
            arrayList_leave_type.add(new JSONObject().put("id", 2).put("cd_name", "Casual Leave").put("request_type", "CL").put("status", false));
            arrayList_leave_type.add(new JSONObject().put("id", 3).put("cd_name", "Absent").put("request_type", "AB").put("status", false));
            adapter_leave_type_list.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void loadFilterApprovedData() {
        try {
            arrayList_approved_type.clear();
            arrayList_approved_type.add(new JSONObject().put("id", 1).put("cod_name", "Approved Leaves").put("request_type", "approved").put("status", false));
            arrayList_approved_type.add(new JSONObject().put("id", 2).put("cod_name", "Rejected Leaves").put("request_type", "reject").put("status", false));
            adapter_approved_type_list.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            leave_type = arrayList_leave_type.get(pos).getString("request_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickDesignation(int pos) {
        try {
            approved_type = arrayList_approved_type.get(pos).getString("request_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setOnDialogListener(OnItemClickDialog itemClickDialog) {
        this.itemClickDialog = itemClickDialog;
    }


    public interface OnItemClickDialog {
        void onItemClick(String from_date, String to_date, String leave_type, String approved_type);
    }


    MessageDialog messageDialogPopup;

    public void showMessagePopup(String msg) {
        messageDialogPopup = new MessageDialog(getActivity());
        messageDialogPopup.setTitle(msg);
        messageDialogPopup.show();
    }


}
