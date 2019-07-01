package com.example.rxhttp.Utils;

import com.example.rxhttp.ExceptionManage.ApiException;
import com.example.rxhttp.ExceptionManage.ExceptionEngine;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 适用Retrofit网络请求Observer(监听者)
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 * 4.重写cancel，取消请求
 *
 * @author ZhongDaFeng
 */
public abstract class HttpRxObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onStart(d);
    }


    public abstract void onStart(Disposable d);

    /**
     * 错误/异常回调
     *
     * @author ZhongDaFeng
     */
    public abstract void onError(ApiException e);

    /**
     * 成功回调
     *
     * @author ZhongDaFeng
     */
    public abstract void onSuccess(T response);

}
