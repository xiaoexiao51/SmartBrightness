package com.smartbrightness.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.smartbrightness.BRApplication;
import com.smartbrightness.R;
import com.smartbrightness.base.BaseActivity;
import com.smartbrightness.bean.UploadBean;
import com.smartbrightness.http.handle.GsonHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MMM on 2017/9/1.
 * 实时视频详情
 */
public class DetailActivity extends BaseActivity {

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
        // http网络请求
        showLoading("正在加载...");
//        String url = "http://118.31.45.21:8180/ShangRaoShi_WY/publish/column/2.json";
//        BRApplication.mOkHttpUtils.get().url(url).tag(this).enqueue(new GsonHandler<List<DangJianBean>>() {
//            @Override
//            public void onSuccess(int statusCode, List<DangJianBean> response) {
//                dismissLoading();
//                ((TextView) findViewById(R.id.tv_result)).setText(response.get(0).getColumnName());
//            }
//
//            @Override
//            public void onFailure(int statusCode, String error_msg) {
//                dismissLoading();
//            }
//        });

        // 文件上传
        File file = new File(saveFileAllName2);
        Map<String, String> params = new HashMap<>();
        params.put("token", "8e23e6dc-bd64-4549-b03a-d0a76d54a23d");
        BRApplication.mOkHttpUtils.upload().url(uploadUrl).params(params).addFile("data", file)
                .enqueue(new GsonHandler<UploadBean>() {
                    @Override
                    public void onSuccess(int statusCode, UploadBean response) {
                        dismissLoading();
                        ((TextView) findViewById(R.id.tv_result)).setText(response.getItems().get(0).getImageName());
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        dismissLoading();
                        ((TextView) findViewById(R.id.tv_result)).setText("statusCode:" + statusCode + ",error_msg" + error_msg);
                    }
                });
    }
}
