package com.smartbrightness.fragment;

import android.os.Bundle;

import com.smartbrightness.R;
import com.smartbrightness.base.BaseFragment;
import com.smartbrightness.utils.LogUtils;

/**
 * Created by MMM on 2017/9/1.
 * 我的收藏
 */
public class CollectionFragment extends BaseFragment {

    @Override
    protected int getViewId() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 初始化控件
        setToolbarTitle("收藏");
    }

    @Override
    protected void initData() {
        // http网络请求
        LogUtils.log("CollectionFragment-initData");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.log("CollectionFragment-onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.log("CollectionFragment-onPause");
    }
}