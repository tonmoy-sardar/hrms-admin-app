package com.sft.hrmsadmin.RetrofitServiceClass;

public class mServiceList {

    public static final String Base_URL = "http://192.168.24.243:8000/";//local Server
    //public static final String Base_URL = "http://166.62.54.122:8001/"; //Live Server
    //public static final String Base_URL = "http://13.232.240.233:8000/"; //AWS Server

    public static final String e_task_attendance_approval_list = "e_task_attendance_approval_list/?";
    public static final String admin_attendance_advance_leave_pending_list = "admin_attendance_advance_leave_pending_list/?";
    public static final String attendance_conveyance_approval_list = "attendance_conveyance_approval_list/?";
    public static final String attendance_conveyance_report_list = "attendance_conveyance_report_list/?";
    public static final String attendance_advance_leave_report = "attendance_advance_leave_report/?";
    public static final String attendance_admin_summary_list = "attendance_admin_summary_list/?";
    public static final String e_task_attendance_approval = "e_task_attendance_approval/{id}/";
    public static final String admin_attendance_advance_leave_approval = "admin_attendance_advance_leave_approval/{id}/";
    public static final String post_login = "login/";
    public static final String get_logout = "logout/";
    public static final String t_core_designation_add = "t_core_designation_add/";
    public static final String t_core_department_add = "t_core_department_add/";
    public static final String employee_list_wo_pagination = "employee_list_wo_pagination/";

}
