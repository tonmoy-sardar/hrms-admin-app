package com.sft.hrmsadmin.RetrofitServiceClass;

import java.util.Arrays;
import java.util.Map;

import okhttp3.RequestBody;

public class Print {

    public static void makePrint(String message) {
        System.out.println(message);
    }

    public static void makePrint(Map<String, String> mHasMap) {
        makePrint("hasMapValue===>" + Arrays.asList(mHasMap));
    }

    public static void makePrintRequestBody(Map<String, RequestBody> mHasMap) {
        makePrint("hasMapValue===>" + Arrays.asList(mHasMap));
    }

    public static void makePrint(int message) {
        System.out.println(String.valueOf(message));
    }

    public static void makePrint(double message) {
        System.out.println(String.valueOf(message));
    }
}
