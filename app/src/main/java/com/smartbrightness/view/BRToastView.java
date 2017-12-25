package com.smartbrightness.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smartbrightness.R;

/**
 * Created by MMM on 2017/9/1.
 */
public class BRToastView {

    private static BRToastView mCustomToast;
    private Toast mToast;

    private BRToastView() {
    }

    public static BRToastView getInstance() {
        if (mCustomToast == null) {
            synchronized (BRToastView.class) {
                if (mCustomToast == null) {
                    mCustomToast = new BRToastView();
                }
            }
        }
        return mCustomToast;
    }

    public void showToast(Context context, String message) {

        //加载Toast布局
        View toastView = LayoutInflater.from(context)
                .inflate(R.layout.layout_toast, null);
        //初始化布局控件
        TextView tv_toast = (TextView) toastView.findViewById(R.id.tv_toast);
        //为控件设置属性
        tv_toast.setText(message);
        //Toast的初始化
        if (mToast == null) {
            mToast = new Toast(context.getApplicationContext());
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(toastView);
        mToast.show();
    }
}
