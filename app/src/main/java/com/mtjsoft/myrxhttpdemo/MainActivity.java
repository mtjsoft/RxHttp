package com.mtjsoft.myrxhttpdemo;

import android.os.Bundle;

import com.example.rxhttp.Api.ApiUtil;
import com.example.rxhttp.ExceptionManage.ApiException;
import com.example.rxhttp.Model.HttpResponse;
import com.example.rxhttp.Utils.HttpRxObservable;
import com.example.rxhttp.Utils.HttpRxObserver;
import com.example.rxhttp.download.DownloadApiUtil;
import com.example.rxhttp.download.DownloadProgressHandler;
import com.example.rxhttp.download.FileDownloader;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例
        //get/post请求
        //参数
        Map<String, Object> map = new HashMap<>();

        HttpRxObservable.getObservable(ApiUtil.instance().apiObservable("", ApiUtil.GET, map, map), this, ActivityEvent.DESTROY)
                .subscribe(new HttpRxObserver<HttpResponse>() {
                    @Override
                    public void onStart(Disposable d) {
                        //开启loding
                    }

                    @Override
                    public void onError(ApiException e) {
                        //错误
                    }

                    @Override
                    public void onSuccess(HttpResponse response) {
                        //成功
                    }
                });
        //上传文件
        List<File> fileList = new ArrayList<>();
        HttpRxObservable.getObservable(ApiUtil.instance().uploadFileObservable("", map, map, "", fileList), this, ActivityEvent.DESTROY)
                .subscribe(new HttpRxObserver<HttpResponse>() {
                    @Override
                    public void onStart(Disposable d) {
                        //开启loding
                    }

                    @Override
                    public void onError(ApiException e) {
                        //错误
                    }

                    @Override
                    public void onSuccess(HttpResponse response) {
                        //成功
                    }
                });
        //下载文件实例
        FileDownloader.downloadFile2(DownloadApiUtil.instance().downloadApkFile("", null), this, "", "",
                new DownloadProgressHandler() {
                    @Override
                    public void onProgress(int progress, long total, long speed) {

                    }

                    @Override
                    public void onCompleted(File file) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
