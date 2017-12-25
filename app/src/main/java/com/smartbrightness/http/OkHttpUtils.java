package com.smartbrightness.http;

import android.os.Handler;
import android.os.Looper;

import com.smartbrightness.http.builder.DownloadBuilder;
import com.smartbrightness.http.builder.GetBuilder;
import com.smartbrightness.http.builder.PostBuilder;
import com.smartbrightness.http.builder.UploadBuilder;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * OkHttpUtils
 */
public class OkHttpUtils {

    public static Handler mHandler = new Handler(Looper.getMainLooper());

    private static OkHttpClient mOkHttpClient;

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public OkHttpUtils() {
        this(null);
    }

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (mOkHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkHttpClient == null) {
                    if (okHttpClient == null) {
                        mOkHttpClient = new OkHttpClient();
                    } else {
                        mOkHttpClient = okHttpClient;
                    }
                }
            }
        }
    }

    public GetBuilder get() {
        return new GetBuilder(this);
    }

    public PostBuilder post() {
        return new PostBuilder(this);
    }

    public UploadBuilder upload() {
        return new UploadBuilder(this);
    }

    public DownloadBuilder download() {
        return new DownloadBuilder(this);
    }

    public void cancel(Object tag) {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
