package com.smartbrightness.http.builder;

import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.http.callback.BaseCallback;
import com.smartbrightness.http.handle.BaseHandler;
import com.smartbrightness.utils.LogUtils;

import java.util.Map;

import okhttp3.Request;

/**
 * Get Builder
 */
public class GetBuilder extends BaseBuilderHasParam<GetBuilder> {

    public GetBuilder(OkHttpUtils okHttpUtils) {
        super(okHttpUtils);
    }

    @Override
    public void enqueue(final BaseHandler responseHandler) {
        try {
            if (mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            if (mParams != null && mParams.size() > 0) {
                // 打印Params日志~
                LogUtils.log("Params:" + mParams.toString());
                mUrl = appendParams(mUrl, mParams);
            }

            Request.Builder builder = new Request.Builder().url(mUrl).get();
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            Request request = builder.build();

            mOkHttpUtils.getOkHttpClient().newCall(request).enqueue(new BaseCallback(responseHandler));
        } catch (Exception e) {
            LogUtils.log("Get enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }

    //append params to url
    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
