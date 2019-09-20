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

public interface ServiceClient {


    @GET(mServiceList.e_task_attendance_approval_list)
    Call<ResponseBody> get_e_task_attendance_approval_list(@Header("Authorization") String Bearer,
                                          @Header("Content-Type") String Content_type,
                                          @Query("page") int page);


    /*@GET(mServiceList.vms_employee_details_master_add)
    Call<ResponseBody> get_vms_employee_details_master_add(@Header("Authorization") String Bearer,
                                                           @Header("Content-Type") String Content_type,
                                                           @Query("name") String name);


    @GET(mServiceList.vms_available_card_list)
    Call<ResponseBody> get_vms_available_card_list(@Header("Authorization") String Bearer,
                                                   @Header("Content-Type") String Content_type,
                                                   @Query("status") boolean status,
                                                   @Query("page") int page,
                                                   @Query("card_no") String card_no);


    @PUT(mServiceList.vms_card_report_change)
    Call<ResponseBody> put_vms_card_report_change(@Header("Authorization") String Bearer,
                                                  @Path("id") String id,
                                                  @Body JsonObject object);


    @PUT(mServiceList.vms_visit_logout)
    Call<ResponseBody> put_vms_visit_logout(@Header("Authorization") String Bearer,
                                            @Path("id") String id);


    @GET(mServiceList.get_visitor_details_add)
    Call<ResponseBody> get_visitor_details_add(@Header("Authorization") String Bearer,
                                               @Header("Content-Type") String Content_type,
                                               @Query("phone_no") String phone_no);


    @GET(mServiceList.get_floor_details_master_add)
    Call<ResponseBody> get_floor_details_master_add(@Header("Authorization") String Bearer,
                                                    @Header("Content-Type") String Content_type);


    @GET(mServiceList.get_card_details_master_add)
    Call<ResponseBody> get_card_details_master_add(@Header("Authorization") String Bearer,
                                                   @Header("Content-Type") String Content_type,
                                                   @Query("report_arise") boolean report_arise,
                                                   @Query("status") boolean status,
                                                   @Query("card_current_status") String card_current_status,
                                                   @Query("floor_access") String floor_access,
                                                   @Query("card_friendly_no") String card_friendly_no);


    @Multipart
    @POST(mServiceList.post_visitor_details_add)
    Call<ResponseBody> post_visitor_details_add(@Part MultipartBody.Part multipartBody,
                                                @Part("name") RequestBody name,
                                                @Part("phone_no") RequestBody phone_no,
                                                @Part("email") RequestBody email,
                                                @Part("address") RequestBody address,
                                                @Part("organization") RequestBody organization);

    @Multipart
    @PUT(mServiceList.visitor_details_edit)
    Call<ResponseBody> put_visitor_details_edit(@Path("id") String id,
                                                @Part MultipartBody.Part multipartBody,
                                                @Part("name") RequestBody name,
                                                @Part("phone_no") RequestBody phone_no,
                                                @Part("email") RequestBody email,
                                                @Part("address") RequestBody address,
                                                @Part("organization") RequestBody organization);

    @POST(mServiceList.post_visitor_details_add)
    Call<ResponseBody> put_visitor_details_add(@Header("Authorization") String Bearer,
                                               @Header("Content-Type") String Content_type,
                                               @Body JsonObject object);


    @PUT(mServiceList.visitor_details_edit)
    Call<ResponseBody> put_visitor_details_edit(@Header("Authorization") String Bearer,
                                                @Body JsonObject object,
                                                @Path("id") String id);


    @POST(mServiceList.post_vms_visit_add)
    Call<ResponseBody> post_vms_visit_add(@Header("Authorization") String Bearer,
                                          @Body JsonObject object);


    @POST(mServiceList.post_login)
    Call<ResponseBody> post_login(@Header("Content-Type") String Content_type,
                                  @Body JsonObject object);


    @GET(mServiceList.get_logout)
    Call<ResponseBody> get_logout(@Header("Authorization") String Bearer);*/
}
