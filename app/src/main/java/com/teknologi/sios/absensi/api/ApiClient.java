package com.teknologi.sios.absensi.api;

import android.content.Context;

import com.teknologi.sios.absensi.BuildConfig;
import com.teknologi.sios.absensi.utils.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient instance;
    private ApiInterface api;
    private Retrofit retrofit;
    private Context context;

    private ApiClient(Context context){
        this.context = context;
        api = getClient().create(ApiInterface.class);
    }

    public static String getURI(){
        return BuildConfig.API_URL;
    }

    public static String getApiUri(){
        return getURI();
    }

    public static String getAssets(String path){
        return getURI() + "storage/" + path;
    }

    public Retrofit getClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder().build();
                Request request = chain.request().newBuilder().addHeader("Authorization", Session.with(context).getToken()).build();
                return chain.proceed(request);
            }
        });

        httpClient.addInterceptor(logging);

        if (retrofit == null){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(getApiUri())
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }

    public ApiInterface getApi() {
        return api;
    }

    public static ApiClient getInstance(Context context){
        return (instance == null) ? new ApiClient(context) : instance;
    }
}
