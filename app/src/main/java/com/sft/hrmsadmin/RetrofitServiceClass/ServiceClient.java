package com.sft.hrmsadmin.RetrofitServiceClass;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.sft.hrmsadmin.RetrofitServiceClass.mServiceList.ATTENDANCE_GRACE_LEAVE_LIST;

public interface ServiceClient {


    @POST(mServiceList.post_login)
    Call<ResponseBody> post_login(@Header("Content-Type") String Content_type,
                                  @Body JsonObject object);


    @GET(mServiceList.get_logout)
    Call<ResponseBody> call_logoutApi(@Header("Authorization") String Bearer);


    @GET(mServiceList.e_task_attendance_approval_list)
    Call<ResponseBody> get_e_task_attendance_approval_list(@Header("Authorization") String Bearer,
                                                           @Header("Content-Type") String Content_type,
                                                           @Query("page") int page,
                                                           @Query("search") String search,
                                                           @Query("request_type") String request_type,
                                                           @Query("field_name") String field_name,
                                                           @Query("order_by") String order_by);


    @GET(mServiceList.admin_attendance_advance_leave_pending_list)
    Call<ResponseBody> get_admin_attendance_advance_leave_pending_list(@Header("Authorization") String Bearer,
                                                                       @Header("Content-Type") String Content_type,
                                                                       @Query("page") int page,
                                                                       @Query("search") String search,
                                                                       @Query("leave_type") String leave_type,
                                                                       @Query("field_name") String field_name,
                                                                       @Query("order_by") String order_by);


    @GET(mServiceList.attendance_leave_approval_list)
    Call<ResponseBody> get_attendance_leave_approval_list(@Header("Authorization") String Bearer,
                                                                       @Header("Content-Type") String Content_type,
                                                                       @Query("page") int page,
                                                                       @Query("search") String search,
                                                                       @Query("leave_type") String leave_type,
                                                                       @Query("field_name") String field_name,
                                                                       @Query("order_by") String order_by);


    @GET(mServiceList.attendance_advance_leave_report)
    Call<ResponseBody> get_attendance_advance_leave_report(@Header("Authorization") String Bearer,
                                                           @Header("Content-Type") String Content_type,
                                                           @Query("page") int page,
                                                           @Query("search") String search,
                                                           @Query("from_date") String from_date,
                                                           @Query("to_date") String to_date,
                                                           @Query("leave_type") String leave_type,
                                                           @Query("approved_type") String approved_type,
                                                           @Query("field_name") String field_name,
                                                           @Query("order_by") String order_by);


    @GET(mServiceList.attendance_approval_report)
    Call<ResponseBody> get_attendance_approval_report(@Header("Authorization") String Bearer,
                                                           @Header("Content-Type") String Content_type,
                                                           @Query("page") int page,
                                                           @Query("search") String search,
                                                           @Query("from_date") String from_date,
                                                           @Query("to_date") String to_date,
                                                           @Query("leave_type") String leave_type,
                                                           @Query("approved_type") String approved_type,
                                                           @Query("field_name") String field_name,
                                                           @Query("order_by") String order_by);


    @GET(mServiceList.attendance_conveyance_approval_list)
    Call<ResponseBody> get_attendance_conveyance_approval_list(@Header("Authorization") String Bearer,
                                                               @Header("Content-Type") String Content_type,
                                                               @Query("page") int page,
                                                               @Query("search") String search,
                                                               @Query("from_date") String from_date,
                                                               @Query("to_date") String to_date,
                                                               @Query("department") String department,
                                                               @Query("designation") String designation,
                                                               @Query("field_name") String field_name,
                                                               @Query("order_by") String order_by);


    @GET(mServiceList.attendance_conveyance_report_list)
    Call<ResponseBody> get_attendance_conveyance_report_list(@Header("Authorization") String Bearer,
                                                             @Header("Content-Type") String Content_type,
                                                             @Query("page") int page,
                                                             @Query("search") String search,
                                                             @Query("from_date") String from_date,
                                                             @Query("to_date") String to_date,
                                                             @Query("department") String department,
                                                             @Query("designation") String designation,
                                                             @Query("field_name") String field_name,
                                                             @Query("order_by") String order_by);


    @GET(mServiceList.attendance_admin_daily_list)
    Call<ResponseBody> get_attendance_admin_daily_list(@Header("Authorization") String Bearer,
                                                             @Header("Content-Type") String Content_type,
                                                             @Query("page") int page,
                                                             @Query("search") String search,
                                                             @Query("start_date") String start_date,
                                                             @Query("end_date") String end_date,
                                                             @Query("department") String department,
                                                             @Query("designation") String designation,
                                                             @Query("field_name") String field_name,
                                                             @Query("order_by") String order_by);


    @GET(mServiceList.attendance_admin_summary_list)
    Call<ResponseBody> get_attendance_admin_summary_list(@Header("Authorization") String Bearer,
                                                         @Header("Content-Type") String Content_type,
                                                         @Query("emp_id") int emp_id,
                                                         @Query("year") int year,
                                                         @Query("month") int month);

    @PUT(mServiceList.e_task_attendance_approval)
    Call<ResponseBody> put_e_task_attendance_approval(@Header("Authorization") String Bearer,
                                                      @Path("id") int id,
                                                      @Body JsonObject object);


    @PUT(mServiceList.admin_attendance_advance_leave_approval)
    Call<ResponseBody> put_admin_attendance_advance_leave_approval(@Header("Authorization") String Bearer,
                                                                   @Path("id") int id,
                                                                   @Body JsonObject object);


    @PUT(mServiceList.attendance_conveyance_approval_list)
    Call<ResponseBody> put_attendance_conveyance_approval_list(@Header("Authorization") String Bearer,
                                                               @Query("req_id") int req_id,
                                                               @Body JsonObject object);


    @GET(mServiceList.t_core_designation_add)
    Call<ResponseBody> get_t_core_designation_add(@Header("Authorization") String Bearer);


    @GET(mServiceList.t_core_department_add)
    Call<ResponseBody> get_t_core_department_add(@Header("Authorization") String Bearer);


    @GET(mServiceList.employee_list_wo_pagination)
    Call<ResponseBody> get_employee_list_wo_pagination(@Header("Authorization") String Bearer);


    @GET(ATTENDANCE_GRACE_LEAVE_LIST)
    Call<ResponseBody> call_attendance_grace_leave_list(@Header("Authorization") String Bearer,
                                                        @Header("Content-Type") String Content_type,
                                                        @Query("employee_id") int employee_id,
                                                        @Query("date") String date);


}
