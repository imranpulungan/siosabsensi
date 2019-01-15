package com.teknologi.labakaya.siosabsensiandroid.presenter;

import android.content.Context;

import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.api.ApiClient;
import com.teknologi.labakaya.siosabsensiandroid.api.response.AbsensiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.AuthResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.InfoIPResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.UserResponse;
import com.teknologi.labakaya.siosabsensiandroid.utils.Session;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class Presenter {

    public static final String RES_GET_DATA_USER = "res_get_data_user";
    public static final String RES_GET_DATA= "res_get_data";
    public static final String RES_LOGIN = "res_process_login";
    public static final String RES_CHANGE_PASSWORD = "res_process_change_password";
    public static final String RES_IS_LOGGED = "res_islogged";
    public static final String RES_CHECK_IP = "res_check_ip";
    public static final String RES_ABSEN_PROSES = "res_absen_proses";
    public static final String RES_CHECK_CODE = "res_check_code";

    protected static Context context;
    protected iPresenterResponse presenterresponse;

    public Presenter(Context context, iPresenterResponse iPresenterResponse){
        this.context = context;
        this.presenterresponse = iPresenterResponse;
    }

    public ApiResponse handleErrorBody(ResponseBody errorBody) throws IOException {
        Converter<ResponseBody, ApiResponse> converter = ApiClient.getInstance(context).getClient().responseBodyConverter(ApiResponse.class, new Annotation[0]);
        ApiResponse responBody = converter.convert(errorBody);
        return responBody;
    }

    protected void handleError(ResponseBody errorBody, int code) throws IOException{
        ApiResponse responBody = handleErrorBody(errorBody);
//        if (code == 422)
//            presenterresponse.doValidationError(responBody.error);
//        else
        if (code == 401)
            Session.with(context).clearLoginSession();
        else if (code >= 400 || code < 500)
            presenterresponse.doFail(responBody.message);
        else if (code >= 500)
            presenterresponse.doFail(context.getResources().getString(R.string.server_error));
        else
            presenterresponse.doConnectionError();
    }

    public void onFailure(Throwable t){
        if (t instanceof ConnectException)
            presenterresponse.doConnectionError();
        else
            presenterresponse.doFail("Error connecting to Server");
    }

    public void postLogin(Map<String, String> data){
        ApiClient.getInstance(context).getApi().postDataLogin(data).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_LOGIN);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void getDataUser(String id){
        ApiClient.getInstance(context).getApi().getDataUser(id).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful())
                    presenterresponse.doSuccess(response.body(), RES_GET_DATA_USER);
//                    else handleError(response.errorBody(), response.code());
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void isLogged(){
        ApiClient.getInstance(context).getApi().isLogged().enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_IS_LOGGED);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void checkIP() {
        ApiClient.getInstance(context).getApi().checkIP().enqueue(new Callback<InfoIPResponse>() {
            @Override
            public void onResponse(Call<InfoIPResponse> call, Response<InfoIPResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_CHECK_IP);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<InfoIPResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void checkQr(Map<String, String> data) {
        ApiClient.getInstance(context).getApi().checkQr(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_CHECK_CODE);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void absenProses(Map<String, RequestBody> dataAdd) {
        ApiClient.getInstance(context).getApi().absenProses(dataAdd).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_ABSEN_PROSES);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void getData(Map<String, String> data) {
        ApiClient.getInstance(context).getApi().getData(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_GET_DATA);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void getDataAbsensi(Map<String, String> data) {
        ApiClient.getInstance(context).getApi().getDataAbsensi(data).enqueue(new Callback<AbsensiResponse>() {
            @Override
            public void onResponse(Call<AbsensiResponse> call, Response<AbsensiResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_GET_DATA);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AbsensiResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }

    public void postChangePassword(Map<String, String> data) {
        ApiClient.getInstance(context).getApi().postChangePassword(data).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_CHANGE_PASSWORD);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }
}
