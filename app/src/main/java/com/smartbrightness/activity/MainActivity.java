package com.smartbrightness.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.smartbrightness.R;
import com.smartbrightness.base.BaseActivity;
import com.smartbrightness.base.BaseFragment;
import com.smartbrightness.fragment.CallPoliceFragment;
import com.smartbrightness.fragment.CheckingFragment;
import com.smartbrightness.fragment.CollectionFragment;
import com.smartbrightness.fragment.CurrentFragment;
import com.smartbrightness.fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MMM on 2017/9/1.
 * MainActivity主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    private List<BaseFragment> mFragments;
    private FragmentManager mFragmentManager;
    private int mLastFocusPosition = -1;

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initFragment();
        mFragmentManager = getSupportFragmentManager();
        mRadioGroup.setOnCheckedChangeListener(onRadioListener);
        mRadioGroup.check(R.id.tab_current);

        checkUpdate();  // 检测版本升级
    }

    private void initFragment() {

        mFragments = new ArrayList<>();
        mFragments.add(new CurrentFragment());
        mFragments.add(new HistoryFragment());
        mFragments.add(new CollectionFragment());
        mFragments.add(new CallPoliceFragment());
        mFragments.add(new CheckingFragment());
    }

    // RadioGroup 切换改变监听
    private RadioGroup.OnCheckedChangeListener onRadioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            final int position = group.indexOfChild(group.findViewById(checkedId));

            if (mLastFocusPosition != position && !MainActivity.this.isFinishing()) {
                mLastFocusPosition = position;
                mFragmentManager.beginTransaction().replace(R.id.fl_container, mFragments.get(position)).commit();
            }
        }
    };

    private void checkUpdate() {
        PgyUpdateManager.register(MainActivity.this, new UpdateManagerListener() {
            @Override
            public void onUpdateAvailable(final String result) {
                final AppBean appBean = getAppBeanFromString(result);
                new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle("新版本升级提醒")
                        .setMessage(appBean.getReleaseNote())
                        .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("现在升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDownloadTask(MainActivity.this, appBean.getDownloadURL());
                            }
                        }).show();
            }

            @Override
            public void onNoUpdateAvailable() {

            }
        });
    }

    private double exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) < 2000) {
                finish();
            } else {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        PgyCrashManager.unregister();
        PgyUpdateManager.unregister();
        super.onDestroy();
    }
}
