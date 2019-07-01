package com.example.rxhttp.Utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.example.rxhttp.Interceptor.BasicParamsInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.http.params.HttpParams;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 构建Retrofit工具类获取retrofit实例
 */
public class RetrofitUtils {
    /**
     * 接口地址
     */
    private String BASE_API = "https://www.wanandroid.com/";
    private static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    private static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    private static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒
    private static RetrofitUtils mInstance = null;
    private OkHttpClient okHttpClient = null;
    private Retrofit retrofit = null;

    private RetrofitUtils() {
    }

    public static RetrofitUtils get() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置okHttp
     *
     * @author Mtj
     */
    private OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            //开启Log
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("okHttp:", message);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();
        }
        return okHttpClient;
    }

    /**
     * 设置baseApi
     *
     * @param baseApi
     */
    public void setBaseApi(String baseApi) {
        if (!TextUtils.isEmpty(baseApi)) {
            BASE_API = baseApi;
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    /**
     * 设置公共参数
     */
    public void setBasicParams(Map<String, String> map) {
        //添加post公共请求参数  Constants.PACKAGE 和 Constants.CFROM
        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addQueryParamsMap(map) // 可以添加 Map 格式的参数
                .build();
        //开启Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("okHttp:", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(basicParamsInterceptor)
                .addInterceptor(logging)
                .build();
    }

    /**
     * 获取Retrofit
     *
     * @author ZhongDaFeng
     */
    public Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
