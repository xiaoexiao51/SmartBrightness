package com.smartbrightness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.smartbrightness.R;
import com.smartbrightness.base.BaseActivity;
import com.smartbrightness.utils.ActivityUtils;
import com.smartbrightness.utils.CommonUtils;
import com.smartbrightness.utils.SPUtils;
import com.smartbrightness.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MMM on 2017/9/1.
 * 程序启动页面
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_downtime)
    TextView mTvDownTime;

    @Override
    protected int getViewId() {
        // 解决重启问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return 0;
        }
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 页面全屏显示
        setStatebarGone();
        // 系统的倒计时
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvDownTime.setText(millisUntilFinished / 1000 + " S");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CommonUtils.getHandler().postDelayed(spRunnable, 3000);
    }

    private Runnable spRunnable = new Runnable() {
        @Override
        public void run() {
            String account = SPUtils.getString(SplashActivity.this, "account", "");
            String password = SPUtils.getString(SplashActivity.this, "password", "");
            if (!StringUtils.isNull(account) && !StringUtils.isNull(password)) {
                // 执行自动登录
            } else {
                // 跳转登录页面
//                ActivityUtils.launchActivity(SplashActivity.this, LoginActivity.class);
                ActivityUtils.launchActivity(SplashActivity.this, MainActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        CommonUtils.getHandler().removeCallbacks(spRunnable);
    }

    @OnClick(R.id.tv_downtime)
    public void onClick() {
        CommonUtils.getHandler().removeCallbacks(spRunnable);
        ActivityUtils.launchActivity(SplashActivity.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
