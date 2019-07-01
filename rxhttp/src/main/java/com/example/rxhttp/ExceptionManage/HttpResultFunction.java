package com.example.rxhttp.ExceptionManage;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 使用onErrorResumeNext(new HttpResultFunction<>())操作符对Retrofit网络请求抛出的Exception进行处理，
 * 我们定义HttpResultFunction处理Retrofit抛出的Exception，通过ExceptionEngine转化为统一的错误类型ApiException
 *
 * @param <T>
 *
 *
 *     实例
 *
 *     Observable observable = apiObservable
 *                 .map(new ServerResultFunction())
 *                 .onErrorResumeNext(new HttpResultFunction<>())
 *                 .subscribeOn(Schedulers.io())
 *                 .observeOn(AndroidSchedulers.mainThread());
 */
public class HttpResultFunction<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
