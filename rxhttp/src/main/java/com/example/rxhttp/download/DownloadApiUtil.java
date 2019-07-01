package com.example.rxhttp.download;

import com.example.rxhttp.Utils.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class DownloadApiUtil {

    private static DownloadApiUtil apiUtil = null;

    public static DownloadApiUtil instance() {
        if (apiUtil == null) {
            synchronized (DownloadApiUtil.class) {
                if (apiUtil == null) {
                    apiUtil = new DownloadApiUtil();
                }
            }
        }
        return apiUtil;
    }

    public Observable<ResponseBody> downloadApkFile(String url, Map<String, Object> parameter) {
        if (parameter == null) {
            parameter = new HashMap<>();
        }
        return RetrofitUtils.get().retrofit().create(DownloadApi.class).downloadApkFile(url, parameter);
    }
}
