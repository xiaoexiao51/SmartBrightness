package com.smartbrightness.http.builder;

import com.smartbrightness.http.OkHttpUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 带有param的base request body
 */
public abstract class BaseBuilderHasParam<T extends BaseBuilderHasParam> extends BaseBuilder<T> {

    protected Map<String, String> mParams;

    public BaseBuilderHasParam(OkHttpUtils okHttpUtils) {
        super(okHttpUtils);
    }

    /**
     * set Map params
     *
     * @param params
     * @return
     */
    public T params(Map<String, String> params) {
        this.mParams = params;
        return (T) this;
    }

    /**
     * add param
     *
     * @param key param key
     * @param val param val
     * @return
     */
    public T addParam(String key, String val) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return (T) this;
    }
}
