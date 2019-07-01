package com.example.rxhttp.Api;

import com.example.rxhttp.Model.HttpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Api接口
 *
 * @author ZhongDaFeng
 */
public interface Api {

    /**
     * GET 请求
     *
     * @param url       api接口url
     * @param parameter 请求参数map
     * @param header    请求头map
     * @return
     */
    @GET
    Observable<HttpResponse> get(@Url String url, @QueryMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header);


    /**
     * POST 请求
     *
     * @param url       api接口url
     * @param parameter 请求参数map
     * @param header    请求头map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<HttpResponse> post(@Url String url, @FieldMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header);


    /**
     * @param requestBody 用于String/JSON格式数据
     */
    @POST
    Observable<HttpResponse> post(@Url String url, @Body RequestBody requestBody, @HeaderMap Map<String, Object> header);


    /**
     * DELETE 请求
     *
     * @param url       api接口url
     * @param parameter 请求参数map
     * @param header    请求头map
     * @return
     */
    @DELETE
    Observable<HttpResponse> delete(@Url String url, @QueryMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header);


    /**
     * PUT 请求
     *
     * @param url       api接口url
     * @param parameter 请求参数map
     * @param header    请求头map
     * @return
     */
    @FormUrlEncoded
    @PUT
    Observable<HttpResponse> put(@Url String url, @FieldMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header);

    /**
     * 单文件上传
     *
     * @param url       api接口url
     * @param parameter 请求接口参数
     * @param header    请求头map
     * @param file      文件
     * @return
     * @Multipart 文件上传注解 multipart/form-data
     */
    @Multipart
    @POST
    Observable<HttpResponse> upload(@Url String url, @PartMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header, @Part MultipartBody.Part file);

    /**
     * 多文件上传
     *
     * @param url       api接口url
     * @param parameter 请求接口参数
     * @param header    请求头map
     * @param fileList  文件列表
     * @return
     * @Multipart 文件上传注解 multipart/form-data
     */
    @Multipart
    @POST
    Observable<HttpResponse> upload(@Url String url, @PartMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header, @Part List<MultipartBody.Part> fileList);

}
