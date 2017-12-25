package com.smartbrightness.http.handle;

import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.utils.LogUtils;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 字符串结果回调
 */
public abstract class StringHandler implements BaseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
            LogUtils.log(responseBodyStr);          // 打印回调json格式数据~
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.log("onResponse fail read response body");
            OkHttpUtils.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;
        OkHttpUtils.mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response.code(), finalResponseBodyStr);
            }
        });

    }

    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
