package com.example.rxhttp.Utils;

import com.example.rxhttp.ExceptionManage.HttpResultFunction;
import com.example.rxhttp.ExceptionManage.ServerResultFunction;
import com.example.rxhttp.Model.HttpResponse;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 适用Retrofit网络请求Observable(被监听者)
 *
 * @author Mtj
 */

public class HttpRxObservable {

    /*线程设置*/
    public static Observable<HttpResponse> getObservable(Observable<HttpResponse> apiObservable,LifecycleProvider lifecycle,ActivityEvent activityEvent) {
        return compose(apiObservable,lifecycle, activityEvent,null)
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*线程设置*/
    public static Observable<HttpResponse> getObservable(Observable<HttpResponse> apiObservable,LifecycleProvider lifecycle,FragmentEvent fragmentEvent) {
        return compose(apiObservable,lifecycle, null,fragmentEvent)
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /*map*/
    private static Observable map(Observable<HttpResponse> apiObservable) {
        return apiObservable
                .map(new ServerResultFunction());
    }

    /* compose 操作符 介于 map onErrorResumeNext */
    private static Observable compose(Observable<HttpResponse> apiObservable,LifecycleProvider lifecycle,
                               ActivityEvent activityEvent,FragmentEvent fragmentEvent) {
        Observable observable = map(apiObservable);
        if (lifecycle != null) {
            if (activityEvent != null || fragmentEvent != null) {
                //两个同时存在,以 activity 为准
                if (activityEvent != null && fragmentEvent != null) {
                    return observable.compose(lifecycle.bindUntilEvent(activityEvent));
                }
                if (activityEvent != null) {
                    return observable.compose(lifecycle.bindUntilEvent(activityEvent));
                }
                if (fragmentEvent != null) {
                    return observable.compose(lifecycle.bindUntilEvent(fragmentEvent));
                }
            } else {
                return observable.compose(lifecycle.bindToLifecycle());
            }
        }
        return observable;
    }
}
