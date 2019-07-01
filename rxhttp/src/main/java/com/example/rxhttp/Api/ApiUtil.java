package com.example.rxhttp.Api;

import com.example.rxhttp.Model.HttpResponse;
import com.example.rxhttp.Utils.RetrofitUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiUtil {

    public static final int POST = 0;
    public static final int GET = 1;
    private static ApiUtil apiUtil = null;

    public static ApiUtil instance() {
        if (apiUtil == null) {
            synchronized (ApiUtil.class) {
                if (apiUtil == null) {
                    apiUtil = new ApiUtil();
                }
            }
        }
        return apiUtil;
    }

    public Observable<HttpResponse> apiObservable(String url, int getOrPost, Map<String, Object> parameter, Map<String, Object> header) {
        if (parameter == null) {
            parameter = new HashMap<>();
        }
        if (header == null) {
            header = new HashMap<>();
        }
        if (getOrPost == POST) {
            return RetrofitUtils.get().retrofit().create(Api.class).post(url, parameter, header);
        }
        return RetrofitUtils.get().retrofit().create(Api.class).get(url, parameter, header);
    }

    public Observable<HttpResponse> uploadFileObservable(String url, Map<String, Object> parameter, Map<String, Object> header, String name, List<File> fileList) {
        if (parameter == null) {
            parameter = new HashMap<>();
        }
        if (header == null) {
            header = new HashMap<>();
        }
        if (fileList == null) {
            return null;
        }
        if (fileList.size() == 1) {
            //单文件上传
            File file = fileList.get(0);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData(name, file.getName(), requestFile1);
            return RetrofitUtils.get().retrofit().create(Api.class).upload(url, parameter, header, body);
        } else {
            //多文件上传
            List<MultipartBody.Part> listPart = new ArrayList<>();
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                listPart.add(MultipartBody.Part.createFormData(name, file.getName(), requestFile1));
            }
            return RetrofitUtils.get().retrofit().create(Api.class).upload(url, parameter, header, listPart);
        }
    }
}
