package com.sft.hrmsadmin.RetrofitServiceClass;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitResponse {

    private Context context;
    private ProgressBarDialog progressBarDialog;
    private View view;
    private FragmentManager fragmentManager;


    public RetrofitResponse(Context context) {
        this.context = context;
        progressBarDialog = new ProgressBarDialog();

    }

    public RetrofitResponse(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        progressBarDialog = new ProgressBarDialog();


    }


    public void showProgressDialog() {
        if (!progressBarDialog.isShowing() && fragmentManager != null) {
            progressBarDialog = new ProgressBarDialog();
            progressBarDialog.showDialog(fragmentManager);
        }
    }

    public void hideProgressDialog() {
        if (progressBarDialog != null && progressBarDialog.isShowing())
            progressBarDialog.dismiss();
    }

        /*public void setUpProgressDialog(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }*/

    public void setDialogMessage(String message) {
        if (progressBarDialog != null)
            progressBarDialog.setMessage(message);
    }


    public void setUpSnackBar(View view) {
        this.view = view;
    }

    public void getWebServiceResponse(Call<ResponseBody> callBackResponse, final DataFetchResult mDataFetchResult) {
        if (InternetConnectionCheck.isInternetAvailable(context)) {
            if (progressBarDialog != null)
                showProgressDialog();
            callBackResponse.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Print.makePrint("Service URL==>" + response.raw().request().url());
                    try {
                        Print.makePrint("before Service Response ==>" + response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (response.body() != null) {
                            String serviceResponse = response.body().string();
                            Print.makePrint("Service Response ==>" + serviceResponse);
                            hideProgressDialog();
                            JSONObject jsonResponse = new JSONObject(serviceResponse);
                            mDataFetchResult.onDataFetchComplete(jsonResponse);
                        } else {
                            //showMessage("Server Error!!");
                            hideProgressDialog();
                        }
                    } catch (IOException e) {
                        showMessage("Something Went Wrong!!");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hideProgressDialog();
                    //showMessage("Server Error!!");
                }
            });
        } else {
            showMessage("No Internet Connection!!");
        }

    }

    private void showMessage(String message) {
        if (view != null)
            showSnackMessage(view, message);
        else
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void showSnackMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public interface DataFetchResult {
        void onDataFetchComplete(JSONObject jsonResponse);
    }
}
