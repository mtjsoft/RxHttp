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
# 使用方法（Fragment一样的用法）：

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
### Activity实例

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
### HttpResponse,http响应参数实体类（实体类无法满足需要，就在源码里修改吧）
```
/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * 
 */
public class HttpResponse {

    /**
     * 描述信息
     */
    @SerializedName("message")
    private String msg;

    /**
     * 状态码
     */
    @SerializedName("code")
    private int code;

    @SerializedName("errNo")
    private String errNo;

    /**
     * 状态
     */
    @SerializedName("status")
    private boolean status;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    private Object result;

    /**
     * 是否成功
     * 这里需要和服务器约定好，isSuccess的条件。
     * @return
     */
    public boolean isSuccess() {
        return status || "0".equals(errNo);
    }

    public String toString() {
        String response = "[http response]" + "{\"code\": " + code + ",\"msg\":" + msg + ",\"result\":" + new Gson().toJson(result) + "}";
        return response;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return new Gson().toJson(result);
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }
}
```
