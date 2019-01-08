package com.teknologi.labakaya.siosabsensiandroid.api;


import com.teknologi.labakaya.siosabsensiandroid.api.response.AbsensiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.AuthResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.InfoIPResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.UserResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("https://ipinfo.io/geo/")
    Call<InfoIPResponse> checkIP();

    @GET("display_data.php")
    Call<UserResponse> getDataUser(@Query("id") String id);

    @POST("config@niagait.com/api.php?f=proses_login")
    Call<AuthResponse> postDataLogin(@Body Map<String, String> data);

    @POST("config@niagait.com/api.php?f=is_logged")
    Call<AuthResponse> isLogged();

    @POST("config@niagait.com/api.php?f=check_code")
    Call<UserResponse> checkQr(@Body Map<String, String> data);

    @POST("config@niagait.com/api.php?f=proses_data")
    Call<UserResponse> getData(@Body Map<String, String> data);

    @POST("config@niagait.com/api.php?f=proses_data")
    Call<AbsensiResponse> getDataAbsensi(@Body Map<String, String> data);

    @Multipart
    @POST("config@niagait.com/api.php?f=proses_absen")
    Call<ApiResponse> absenProses(@PartMap Map<String, RequestBody> dataAdd);
}


