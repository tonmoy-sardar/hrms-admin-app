package com.sft.hrmsadmin.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.sft.hrmsadmin.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodUtils {

    public static void setStickyBar(Activity activity) {

        if (activity.getActionBar() != null && activity.getActionBar().isShowing())
            activity.getActionBar().hide();

        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.statusBarColor));
        }
    }


    public static String profileDate(String date) {
        String converted_date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date0 = sdf.parse(date);
            DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
            converted_date = dateFormat.format(date0);
            System.out.println("Converted String: " + converted_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return converted_date;
    }


}
