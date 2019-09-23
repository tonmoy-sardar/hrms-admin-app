package com.sft.hrmsadmin.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NCRTS on 1/16/2017.
 */

public class MySharedPreferance {

    public String login_token = "login_token";
    public String login_response = "login_response";
    public String remember_user_name = "remember_user_name";
    public String remember_password = "remember_password";

    Context context;

    public MySharedPreferance(Context context) {
        this.context = context;
    }

    public void savePreferancce(String key, String value) {
        System.out.println("Preference KEY===>>>    " + key + " Preference VALUE===>>>   " + value);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void savePreferancce(String key, boolean value) {
        System.out.println("Preference KEY===>>>    " + key + " Preference VALUE===>>>   " + value);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

   /* public void savePreferancce(String key, int value) {
        System.out.println("Preference KEY===>>>    " + key + " Preference VALUE===>>>   " + value);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }*/

    public String getPreferancceString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");

    }

    public boolean getPreferancceBoolean(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);

    }

    public int getPreferancceInt(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);

    }

    public String getPreferancceJSONArray(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "[]");

    }

    public void deletePreferenceValue(String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Preferance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY);
        editor.apply();
    }

   /* public String getCustomerID() {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LEAPreferance", Context.MODE_PRIVATE);

            JSONObject mObject = new JSONObject(sharedPreferences.getString(user_dtls, ""));
            return mObject.getString("customer_id");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }*/
}
