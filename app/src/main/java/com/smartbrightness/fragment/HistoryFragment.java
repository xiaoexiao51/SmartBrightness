package com.smartbrightness.fragment;

import android.os.Bundle;
import android.view.View;

import com.smartbrightness.R;
import com.smartbrightness.base.BaseFragment;
import com.smartbrightness.dialog.MessageDialog;
import com.smartbrightness.utils.LogUtils;

import butterknife.OnClick;

/**
 * Created by MMM on 2017/9/1.
 * 历史视频
 */
public class HistoryFragment extends BaseFragment {

    @Override
    protected int getViewId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 初始化控件
    }

    @Override
    protected void initData() {
        // http网络请求
        LogUtils.log("HistoryFragment-initData");
    }

    @OnClick({R.id.tv_more, R.id.fab_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                MessageDialog dialog = new MessageDialog(activity);
                dialog.build("提示", "修改阴影透明度", false);
                dialog.setOnChooseListener(new MessageDialog.OnChooseListener() {
                    @Override
                    public void onChooseResult(boolean confirm) {
                        showToast(confirm ? "选择确定" : "选择取消");
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.log("HistoryFragment-onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.log("HistoryFragment-onPause");
    }
}