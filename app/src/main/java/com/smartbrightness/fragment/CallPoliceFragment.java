package com.smartbrightness.fragment;

import android.os.Bundle;

import com.smartbrightness.R;
import com.smartbrightness.base.BaseFragment;
import com.smartbrightness.utils.LogUtils;

/**
 * Created by MMM on 2017/9/1.
 * 一键报警
 */
public class CallPoliceFragment extends BaseFragment {

    @Override
    protected int getViewId() {
        return R.layout.fragment_callpolice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 初始化控件
        setToolbarTitle("报警");
    }

    @Override
    protected void initData() {
        // http网络请求
        LogUtils.log("CallPoliceFragment-initData");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.log("CallPoliceFragment-onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.log("CallPoliceFragment-onPause");
    }
}