package com.sft.hrmsadmin.RetrofitServiceClass;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnectionCheck {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&
                conMgr.getActiveNetworkInfo().isConnected();

    }
}
