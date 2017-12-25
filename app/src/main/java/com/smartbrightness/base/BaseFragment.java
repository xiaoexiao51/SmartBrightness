package com.smartbrightness.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smartbrightness.R;
import com.smartbrightness.activity.DetailActivity;
import com.smartbrightness.utils.StringUtils;
import com.smartbrightness.view.BRStateLayout;
import com.smartbrightness.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by MMM on 2017/9/1.
 * BaseFragment基类
 */
public abstract class BaseFragment extends Fragment {

    protected LayoutInflater mInflater;
    private View mRootView;
    protected AppCompatActivity activity;
    private BRStateLayout mStateView;

    private Toolbar mToolbar;
    private TextView mTxtTitle;
    private TextView mTxtMore;

    private LoadingDialog mLoadingDialog = null;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((AppCompatActivity) context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_base, container, false);
            mStateView = (BRStateLayout) mRootView.findViewById(R.id.state_view);
            int layout = getViewId();
            if (layout != 0) {
                mStateView.addStatLayout(BRStateLayout.STATE_SUCCESS, getViewId());
            }
            View view = mStateView.getStateView(BRStateLayout.STATE_SUCCESS);
            ButterKnife.bind(this, view);
            // 默认展示加载成功状态
            showSuccessStateLayout();
            // 初始化Toolbar标题栏
            initToolbar();
            // 初始化子类页面各个控件
            initView(savedInstanceState);

            // 标题栏用户中心图标
            initToolbarListener();
        }
        return mRootView;
    }

    private void initToolbarListener() {
        setToolMoreIcon(R.drawable.ic_toolbar_user, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, DetailActivity.class));
            }
        });
    }

    /**
     * 设置资源ID，子类必须重写
     *
     * @return
     */
    protected abstract int getViewId();

    /**
     * 设置初始化控件，子类必须重写
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置网络数据，子类必须重写
     */
    protected abstract void initData();

    /**
     * 显示加载进行中布局
     */
    protected void showLodingStateLayout() {
        mStateView.showStateView(BRStateLayout.STATE_LOADING);
    }

    /**
     * 显示加载成功布局
     */
    protected void showSuccessStateLayout() {
        mStateView.showStateView(BRStateLayout.STATE_SUCCESS);
    }

    /**
     * 显示加载错误/网络出错布局
     */
    protected void showErrorStateLayout() {
        mStateView.showStateView(BRStateLayout.STATE_ERROR);
    }

    /**
     * 显示数据为空布局
     */
    protected void showEmptyStateLayout() {
        mStateView.showStateView(BRStateLayout.STATE_EMPTY);
    }

    /**
     * 显示需要登录布局
     */
    protected void showLoginStateLayout() {
        mStateView.showStateView(BRStateLayout.STATE_LOGIN);
    }

    /**
     * 初始化Toolbar相关
     *
     * @return
     */
    public Toolbar initToolbar() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mTxtTitle = (TextView) mRootView.findViewById(R.id.txt_title);
        mTxtMore = (TextView) mRootView.findViewById(R.id.txt_more);
        return mToolbar;
    }

    /**
     * 设置Toolbar中间的标题
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (!StringUtils.isNull(title)) {
            mTxtTitle.setText(title);
            mToolbar.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置Toolbar右边为文字及监听
     *
     * @param moreTxt
     * @param listener
     */
    public void setToolMoreText(String moreTxt, View.OnClickListener listener) {
        if (!StringUtils.isNull(moreTxt) && listener != null) {
            mTxtMore.setText(moreTxt);
            mTxtMore.setOnClickListener(listener);
        }
    }

    /**
     * 设置Toolbar右边为图标及监听
     *
     * @param moreRes
     * @param listener
     */
    public void setToolMoreIcon(int moreRes, View.OnClickListener listener) {
        if (moreRes != 0 && listener != null) {
            Drawable drawable = getResources().getDrawable(moreRes);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTxtMore.setCompoundDrawables(drawable, null, null, null);
            mTxtMore.setOnClickListener(listener);
        }
    }

    /**
     * 显示加载loading对话框
     *
     * @param message
     */
    public void showLoading(String message) {
        if (mLoadingDialog == null)
            mLoadingDialog = new LoadingDialog(activity);
        mLoadingDialog.setTvLoading(message);
        mLoadingDialog.show();
    }

    /**
     * 消失加载loading对话框
     */
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    /**
     * 提示用户的吐司信息
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    //Fragment当前状态是否可见
    private boolean isVisible = false;
    //视图是否加载完成
    private boolean isInitView = false;
    //是否已经加载过数据
    private boolean isInitData = false;

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            isVisible = true;
//            preLoadData();
//        } else {
//            isVisible = false;
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
            preLoadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
    }

    public void preLoadData() {
        if (isVisible && isInitView && !isInitData) {
            // 初始化子类页面数据请求
            initData();
            isInitData = true;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInitView = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInitView = false;
    }
}
