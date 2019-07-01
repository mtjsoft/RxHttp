package com.example.rxhttp.ExceptionManage;

import com.example.rxhttp.Model.HttpResponse;
import com.google.gson.Gson;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 在.map(new ServerResultFunction())操作符中处理服务器返回的结果是否正确（这里指逻辑正确，即code==0），
 * 如果code!=0，就抛出ServerException
 * <p>
 * 实例
 * *
 * *     Observable observable = apiObservable
 * *                 .map(new ServerResultFunction())
 * *                 .onErrorResumeNext(new HttpResultFunction<>())
 * *                 .subscribeOn(Schedulers.io())
 * *                 .observeOn(AndroidSchedulers.mainThread());
 */
public class ServerResultFunction implements Function<HttpResponse, HttpResponse> {
    @Override
    public HttpResponse apply(@NonNull HttpResponse response) throws Exception {
        if (!response.isSuccess()) {
            throw new ServerException(response.getCode(), response.getMsg());
        }
        return response;
    }
}
