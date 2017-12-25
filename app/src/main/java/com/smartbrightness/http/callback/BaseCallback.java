package com.smartbrightness.http.callback;

import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.http.handle.BaseHandler;
import com.smartbrightness.utils.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * BaseCallback
 */
public class BaseCallback implements Callback {

    private BaseHandler mResponseHandler;

    public BaseCallback(BaseHandler responseHandler) {
        mResponseHandler = responseHandler;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        LogUtils.log("onFailure=" + e.getMessage());    // 打印异常日志~

        OkHttpUtils.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mResponseHandler.onFailure(0, e.toString());
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) {
        if (response.isSuccessful()) {
            mResponseHandler.onSuccess(response);
        } else {
            LogUtils.log("onResponse fail status=" + response.code());

            OkHttpUtils.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailure(response.code(), "fail status=" + response.code());
                }
            });
        }
    }
}
