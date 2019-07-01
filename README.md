# RxHttp
rxjava2+retrofit2+rxlifecycle2，有生命周期感知的网络请求框架封装（包含多文件上传及文件下载和下载进度）

# 1、Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
# 2、Add the dependency
```
dependencies {
	        implementation 'com.github.mtjsoft:RxHttp:v1.0'
	}
```
# 实例TestActivity（Fragment一样的用法）：
```
import android.os.Bundle;
import android.support.annotation.Nullable;

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

public class TestActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get/post请求
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
```
