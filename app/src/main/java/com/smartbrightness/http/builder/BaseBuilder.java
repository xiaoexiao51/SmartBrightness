package com.smartbrightness.http.builder;

import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.http.handle.BaseHandler;
import com.smartbrightness.utils.LogUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * 不带param的base request body
 */
public abstract class BaseBuilder<T extends BaseBuilder> {
    protected String mUrl;
    protected Object mTag;
    protected Map<String, String> mHeaders;

    protected OkHttpUtils mOkHttpUtils;

    /**
     * 异步执行
     *
     * @param responseHandler 自定义回调
     */
    abstract void enqueue(final BaseHandler responseHandler);

    public BaseBuilder(OkHttpUtils okHttpUtils) {
        mOkHttpUtils = okHttpUtils;
    }

    /**
     * set url
     *
     * @param url url
     * @return
     */
    public T url(String url) {
        // 打印url日志~
        LogUtils.log("url:" + url.toString());
        this.mUrl = url;
        return (T) this;
    }

    /**
     * set tag
     *
     * @param tag tag
     * @return
     */
    public T tag(Object tag) {
        this.mTag = tag;
        return (T) this;
    }

    /**
     * set headers
     *
     * @param headers headers
     * @return
     */
    public T headers(Map<String, String> headers) {
        this.mHeaders = headers;
        return (T) this;
    }

    /**
     * set one header
     *
     * @param key header key
     * @param val header val
     * @return
     */
    public T addHeader(String key, String val) {
        if (this.mHeaders == null) {
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, val);
        return (T) this;
    }

    //append headers into builder
    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;
        // 打印Header日志~
        LogUtils.log("Header:" + headers.toString());
        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }
}
