package com.smartbrightness.http.handle;

import okhttp3.Response;

/**
 * BaseHandler
 */
public interface BaseHandler {

    void onSuccess(Response response);

    void onFailure(int statusCode, String error_msg);

    void onProgress(long currentBytes, long totalBytes);
}
