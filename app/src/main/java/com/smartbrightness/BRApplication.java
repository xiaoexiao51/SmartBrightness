package com.smartbrightness;

import android.app.Application;
import android.content.Context;

import com.smartbrightness.http.OkHttpUtils;
import com.smartbrightness.utils.LogUtils;
import com.smartbrightness.utils.SPUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by MMM on 2017/9/1.
 * BRApplication
 */
public class BRApplication extends Application {

    public static String ip = "";
    public static String port = "";

    public static Context applicationContext;
    public static OkHttpUtils mOkHttpUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        ip = SPUtils.getString(getApplicationContext(), "ip", "113.214.24.174");
        port = SPUtils.getString(getApplicationContext(), "port", "8080");

        initConfig();
    }

    private void initConfig() {
        if (BuildConfig.DEBUG) {
            LogUtils.setDebugMode("LogUtils");// 初始化日志管理
//            if (!LeakCanary.isInAnalyzerProcess(this)) {
//                LeakCanary.install(this);     // 初始化内存管理
//            }
        }
        // 初始化网络请求
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15000L, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)    // 设置开启logging
                .build();
        mOkHttpUtils = new OkHttpUtils(okHttpClient);

//        PgyCrashManager.register(this);// 初始化蒲公英
    }
}
