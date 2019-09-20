package com.sft.hrmsadmin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by himadri on 28/11/16.
 */

public class GetFormatDateTime {


    public static String getFormatTime(String startTime) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
            Date dt;
            dt = sdf.parse(startTime);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return startTime;
        }

    }


    public static String getFormatDate(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd, yyyy");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }
    }


    public static String getFormatDateTime(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd, yyyy");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }

    public static String getFormatDateVH(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }



    public static String getFormatTodayDateDay(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
            SimpleDateFormat sdfs = new SimpleDateFormat("dd.MM.yyyy");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }

    public static String getFormatJoinDate(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }

    public static String getFormatJoinMonth(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("MM");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }

    public static String getFormatJoinYear(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }

    public static String getMonthDate(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("MMMM dd");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }

    public static String getDateYear(String date) {
        try {
            String formatDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("dd, yyyy");
            Date dt;
            dt = sdf.parse(date);
            formatDate = "" + sdfs.format(dt);
            System.out.println("Date Display: " + sdfs.format(dt)); // <-- I got result here
            return formatDate;
        } catch (ParseException e) {
            return date;
        }

    }


    public static String getTimeDifference(String str_time1, String str_time2) {
        try {
            String diffTime = null;
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            Date date1 = formatter.parse(str_time1);
            Date date2 = formatter.parse(str_time2);

// Get msec from each, and subtract.
            long diff = date2.getTime() - date1.getTime();
            diffTime = "" + (diff / (1000 * 60 * 60));
            System.out.println("Difference In Days: " + (diff / (1000 * 60 * 60 * 24)));
            return diffTime;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }


    }

    public static String getHrMinFromStr(String str) {
        System.out.println("getHrMinFromStr===>>>" + str);
        try {
            String hhmm = null;
            Double hours = Double.parseDouble(str);
            Double minute = ((hours * 60));
            int hh = 0;
            int mm = 0;
            if (minute > 60) {
                hh = (int) (minute / 60);
                mm = (int) (minute % 60);
            } else {
                mm = minute.intValue();
            }
            hhmm = "" + hh + "hr " + mm + "min";
            return hhmm;
        } catch (Exception e) {
            return str;
        }
    }
}
