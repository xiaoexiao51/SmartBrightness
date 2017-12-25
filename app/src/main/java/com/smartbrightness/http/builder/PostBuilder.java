package com.smartbrightness.http.builder;

import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.http.callback.BaseCallback;
import com.smartbrightness.http.handle.BaseHandler;
import com.smartbrightness.utils.LogUtils;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * post builder
 */
public class PostBuilder extends BaseBuilderHasParam<PostBuilder> {

    private String mJsonParams = "";

    public PostBuilder(OkHttpUtils okHttpUtils) {
        super(okHttpUtils);
    }

    /**
     * json格式参数
     *
     * @param json
     * @return
     */
    public PostBuilder jsonParams(String json) {
        this.mJsonParams = json;
        return this;
    }

    @Override
    public void enqueue(BaseHandler responseHandler) {
        try {
            if (mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            if (mJsonParams.length() > 0) { //上传json格式参数
                // 打印Params日志~
                LogUtils.log("JsonParams:" + mJsonParams.toString());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonParams);
                builder.post(body);
            } else {                        //普通参数
                // 打印Params日志~
                LogUtils.log("Params:" + mParams.toString());
                FormBody.Builder encodingBuilder = new FormBody.Builder();
                appendParams(encodingBuilder, mParams);
                builder.post(encodingBuilder.build());
            }

            Request request = builder.build();

            mOkHttpUtils.getOkHttpClient().newCall(request).enqueue(new BaseCallback(responseHandler));
        } catch (Exception e) {
            LogUtils.log("Post enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }

    //append params to form builder
    private void appendParams(FormBody.Builder builder, Map<String, String> params) {

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }
}
