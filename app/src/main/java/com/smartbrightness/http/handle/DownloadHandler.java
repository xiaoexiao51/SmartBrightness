package com.smartbrightness.http.handle;

import java.io.File;

/**
 * 下载回调
 */
public abstract class DownloadHandler {

    public void onStart(long totalBytes) {

    }

    public void onCancel() {

    }

    public abstract void onFinish(File downloadFile);

    public abstract void onProgress(long currentBytes, long totalBytes);

    public abstract void onFailure(String error_msg);
}
