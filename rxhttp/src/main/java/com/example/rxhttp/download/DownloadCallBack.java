package com.example.rxhttp.download;

import java.io.File;

/**
 * 下载回调
 */

public interface DownloadCallBack {

    /**
     * 进度，运行在主线程
     *
     * @param progress 下载进度
     * @param total    总大小
     * @param speed    下载速率
     */
    void onProgress(int progress, long total, long speed);

    /**
     * 运行在主线程
     *
     * @param file
     */
    void onCompleted(File file);

    /**
     * 运行在主线程
     *
     * @param e
     */
    void onError(Throwable e);

}

