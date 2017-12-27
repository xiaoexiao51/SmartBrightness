package com.smartbrightness.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.smartbrightness.BRApplication;
import com.smartbrightness.R;
import com.smartbrightness.base.BaseActivity;
import com.smartbrightness.bean.UploadBean;
import com.smartbrightness.http.response.GsonResHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MMM on 2017/9/1.
 * 测试网络请求
 */
public class DetailActivity extends BaseActivity {

    // 文件下载存储卡的路径
//    private static String sd_path = Environment.getExternalStorageDirectory() + "/";
//    private static String filePath = sd_path + "EmojiDir/";
//    private static String saveFileAllName = filePath + "emoji.zip";
//    private static String downloadUrl = "http://54.65.154.177:8080/upLoadPath/ed7fc241-eb60-4fd0-88a0-8d9637dcbc6b.zip";

    // 文件上传存储卡的路径
    private static String sd_path = Environment.getExternalStorageDirectory() + "/";
    private static String filePath = sd_path + "EmojiDir/";
    private static String saveFileAllName = filePath + "123.jpg";
    private static String saveFileAllName2 = filePath + "121.jpg";
    private static String uploadUrl = "http://113.214.24.174:8080/governance_restful/service/order/uploadImage";
//    private static String uploadUrl = "http://118.31.45.21:8280/governance_restful/service/order/uploadImage";

    @Override
    protected int getViewId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 初始化控件
        setToolbarTitle("视频详情");
    }

    @Override
    protected void initData() {
        super.initData();
        // 登录加密
//        String loginUrl = "http://113.214.24.174:8180/IntelligentCountry_TV/service/login";
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("userName", "1");
//        jsonObject.addProperty("passWord", "1");
//        String jsonString = jsonObject.toString();
//
//        // 加密
//        String keyString = jsonString + "www.gaoruitv.com";// 拼接密钥
//        String encodeMD5 = MD5Utils.toMD5(keyString);// MD5加密明文
//
//        Map<String,String> params = new HashMap<>();
//        params.put("wsHead", jsonString);
//        params.put("key", encodeMD5);
//
//        BRApplication.mOkDroid.post().url(loginUrl).params(params).tag(this)
//                .enqueue(new RawResHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, String response) {
//                        ((TextView) findViewById(R.id.tv_result)).setText(response);
//                    }
//
//                    @Override
//                    public void onFailed(int statusCode, String errMsg) {
//                        ((TextView) findViewById(R.id.tv_result)).setText(errMsg);
//                    }
//                });

        // 通讯加密
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("bytalkjid", "");
//        jsonObject.addProperty("timestamp", Long.toString(System.currentTimeMillis()));
//
//        String jsonString = jsonObject.toString();
//        String encodeBase64 = BASE64Utils.encodeBase64(jsonString);
//        // 加密
//        String keyString = jsonString + "qZe60QZFxuirub2ey4+7+Q==";
//        String encodeMD5 = MD5Utils.encodeMD5(keyString);
//        String KeyEncodeBase64 = BASE64Utils.encodeBase64(encodeMD5);
//
//        Map<String,String> params = new HashMap<>();
//        params.put("logistics_interface", encodeBase64);
//        params.put("data_digest", KeyEncodeBase64);
//        params.put("msg_type", "JSON");
//        params.put("timestamp", Long.toString(System.currentTimeMillis()));
//        params.put("version", "1.0");
//
//        String url = "http://fo.wellchat.net/WellChatForBuddhism/httpAdsListAction";
//
//        BRApplication.mOkDroid.post().url(url).params(params).tag(this)
//                .enqueue(new RawResHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, String response) {
//                        ((TextView) findViewById(R.id.tv_result)).setText(response);
//                    }
//
//                    @Override
//                    public void onFailed(int statusCode, String errMsg) {
//                        ((TextView) findViewById(R.id.tv_result)).setText(errMsg);
//                    }
//                });

        // 文件下载
//        File file = new File(filePath);
//        // 判断文件目录是否存在
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        BRApplication.mOkDroid.download().url(downloadUrl).filePath(saveFileAllName)
//                .enqueue(new IResponseDownloadHandler() {
//                    @Override
//                    public void onFinish(File downloadFile) {
//                        ((TextView) findViewById(R.id.tv_result)).setText("下载完成：" + downloadFile.getPath());
//                    }
//
//                    @Override
//                    public void onProgress(long progress, long total) {
//                        long percent = progress * 100 / total;
//                        ((TextView) findViewById(R.id.tv_result)).setText("下载进度：" + percent + "%");
//                    }
//
//                    @Override
//                    public void onFailed(String errMsg) {
//                        ((TextView) findViewById(R.id.tv_result)).setText("下载失败：" + errMsg);
//                    }
//                });

        // 文件上传
        Map<String, File> fileMap = new HashMap<>();
//        File file = new File(saveFileAllName);
        File file2 = new File(saveFileAllName2);
//        fileMap.put("data", file);
        fileMap.put("data2", file2);

        Map<String, String> params = new HashMap<>();
        params.put("token", "7232afc4-5163-4edf-a37a-d91d11244175");
        showLoading("正在加载...");
        BRApplication.mOkDroid.upload().url(uploadUrl).params(params).files(fileMap)
                .enqueue(new GsonResHandler<UploadBean>() {
                    @Override
                    public void onFailed(int statusCode, String errMsg) {
                        dismissLoading();
                    }

                    @Override
                    public void onSuccess(int statusCode, UploadBean response) {
                        dismissLoading();
                        ((TextView) findViewById(R.id.tv_result)).setText(response.toString());
                    }
                });
    }
}
