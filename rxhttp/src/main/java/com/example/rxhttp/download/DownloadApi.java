package com.example.rxhttp.download;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadApi {

    /**
     * 下载Apk1文件
     *
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApkFile(@Url String url, @QueryMap Map<String, Object> parameter);
}
