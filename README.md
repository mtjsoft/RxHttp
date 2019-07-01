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

### 可在Application中设置bascUrl
```
  public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUtils.get().setBaseApi("https://www.baidu.com/");
    }
}
```

```
public class TestActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //网络请求
	//参数map
        Map<String, Object> map = new HashMap<>();
        //请求头header
        Map<String, Object> header = new HashMap<>();
	
	//ActivityEvent.DESTROY，感知界面销毁时，取消订阅（FragmentEvent.DESTROY自行设置）
	//ApiUtil.GET、ApiUtil.POST自行设置
	
        HttpRxObservable.getObservable(ApiUtil.instance().apiObservable("/login", ApiUtil.POST, map, header), this, ActivityEvent.DESTROY)
                .subscribe(new HttpRxObserver<HttpResponse>() {
                    @Override
                    public void onStart(Disposable d) {
                        //请求开始，可用于开启loding
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
	//fileList多文件上传
        List<File> fileList = new ArrayList<>();
	String file = "file";
        HttpRxObservable.getObservable(ApiUtil.instance().uploadFileObservable("/upload", map, header, file, fileList), this, ActivityEvent.DESTROY)
                .subscribe(new HttpRxObserver<HttpResponse>() {
                    @Override
                    public void onStart(Disposable d) {
                        //请求开始，可用于开启loding
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
	String destDir = "保存地址";
        String fileName = "文件名称.apk";
        FileDownloader.downloadFile2(DownloadApiUtil.instance().downloadApkFile("/download", null), this, destDir, fileName,
                new DownloadProgressHandler() {
                    @Override
                    public void onProgress(int progress, long total, long speed) {
		         // 下载进度、大小...（可用于更新进度条）
                    }

                    @Override
                    public void onCompleted(File file) {
                        // 下载完成
                    }

                    @Override
                    public void onError(Throwable e) {
                       // 下载错误，失败
                    }
                });
    }
}
```
