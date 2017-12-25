package com.smartbrightness.http.handle;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Gson类型的回调接口
 */
public abstract class GsonHandler<T> implements BaseHandler {

    private Type mType;

    public GsonHandler() {
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    private Type getType() {
        return mType;
    }

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

        try {
            OkHttpUtils.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    onSuccess(response.code(), (T) gson.fromJson(finalResponseBodyStr, getType()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.log("onResponse fail parse gson, body=" + finalResponseBodyStr);
            OkHttpUtils.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail parse gson, body=" + finalResponseBodyStr);
                }
            });

        }
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}