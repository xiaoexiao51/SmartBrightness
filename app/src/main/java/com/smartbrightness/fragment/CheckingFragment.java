package com.smartbrightness.fragment;

import android.os.Bundle;

import com.smartbrightness.R;
import com.smartbrightness.base.BaseFragment;
import com.smartbrightness.utils.LogUtils;

/**
 * Created by MMM on 2017/9/1.
 * 视频巡查
 */
public class CheckingFragment extends BaseFragment {

    @Override
    protected int getViewId() {
        return R.layout.fragment_checking;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 初始化控件
        setToolbarTitle("巡查");
    }

    @Override
    protected void initData() {
        // http网络请求
        LogUtils.log("CheckingFragment-initData");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.log("CheckingFragment-onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.log("CheckingFragment-onPause");
    }
}
