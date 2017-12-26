package com.smartbrightness.http.response;

import com.smartbrightness.http.OkDroid;
import com.smartbrightness.utils.LogUtils;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by：hcs on 2017/4/24 17:31
 * e_mail：aaron1539@163.com
 */
public abstract class RawResHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";
        try {
            responseBodyStr = responseBody.string();
            LogUtils.log(responseBodyStr);// 打印网络数据~
        } catch (IOException e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(response.code(), "failed read response body");
                }
            });
        } finally {
            responseBody.close();
        }
        final String finalResBodyStr = responseBodyStr;
        OkDroid.mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response.code(), finalResBodyStr);
            }
        });
    }

    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onProgress(long progress, long total) {

    }
}
