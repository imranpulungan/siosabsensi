package com.teknologi.sios.absensi.api;

import com.teknologi.sios.absensi.api.response.ApiResponse;
import com.teknologi.sios.absensi.api.response.AuthResponse;
import com.teknologi.sios.absensi.api.response.InfoIPResponse;
import com.teknologi.sios.absensi.api.response.UserResponse;

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
    @GET("display_data.php")
    Call<UserResponse> getDataUser(@Query("id") String id);

    @POST("sios_management/config@niagait.com/api.php?f=proses_login")
    Call<AuthResponse> postDataLogin(@Body Map<String,String> data);

    @POST("sios_management/config@niagait.com/api.php?f=is_logged")
    Call<AuthResponse> isLogged();

    @GET("https://ipinfo.io/geo/")
    Call<InfoIPResponse> checkIP();

    @Multipart
    @POST("sios_management/config@niagait.com/api.php?f=proses_absen")
    Call<ApiResponse> absenProses(@PartMap Map<String, RequestBody> dataAdd);


    @POST("sios_management/config@niagait.com/api.php?f=check_code")
    Call<UserResponse> checkQr(@Body Map<String, String> data);
}


