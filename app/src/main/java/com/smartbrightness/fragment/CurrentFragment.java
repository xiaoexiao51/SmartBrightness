package com.smartbrightness.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.smartbrightness.BRApplication;
import com.smartbrightness.R;
import com.smartbrightness.activity.DetailActivity;
import com.smartbrightness.adapter.MainNewsAdapter;
import com.smartbrightness.base.BaseFragment;
import com.smartbrightness.base.BaseRecyclerAdapter;
import com.smartbrightness.bean.NewsBean;
import com.smartbrightness.http.handle.GsonHandler;
import com.smartbrightness.refreshview.LRecyclerAdapter;
import com.smartbrightness.refreshview.LRecyclerView;
import com.smartbrightness.refreshview.interfaces.OnLoadMoreListener;
import com.smartbrightness.refreshview.interfaces.OnNetWorkErrorListener;
import com.smartbrightness.refreshview.interfaces.OnRefreshListener;
import com.smartbrightness.utils.ActivityUtils;
import com.smartbrightness.utils.CommonUtils;
import com.smartbrightness.utils.LogUtils;
import com.smartbrightness.utils.MessageUtils;
import com.smartbrightness.utils.ScreenUtils;
import com.smartbrightness.view.BRPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MMM on 2017/9/1.
 * 实时视频
 */
public class CurrentFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_sub_title)
    TextView mTvSubTitle;
    @BindView(R.id.fl_sub_title)
    FrameLayout mFlSubTitle;
    @BindView(R.id.recycler_view)
    LRecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;
    @BindView(R.id.fab_button)
    FloatingActionButton mFabButton;

    private boolean isToBot;
    private boolean isToTop;
    private int mFirstPos;
    private int mSecondPos;

    private List<String> mFirstItems = new ArrayList<>();
    private List<String> mSecondItems = new ArrayList<>();

    {
        mFirstItems.add("西湖区");
        mFirstItems.add("上城区");
        mFirstItems.add("下城区");
        mFirstItems.add("江干区");
        mFirstItems.add("拱墅区");
        mFirstItems.add("滨江区");
        mFirstItems.add("萧山区");
        mFirstItems.add("余杭区");

        mSecondItems.add("凯旋街道");
        mSecondItems.add("采荷街道");
        mSecondItems.add("闸弄口街道");
        mSecondItems.add("四季青街道");
        mSecondItems.add("白杨街道");
    }

    private int mCurrentPage = 0;
    private int mTotlePage = 5;
    private boolean isRefreshing = true;
    private boolean isLoading = false;

    private MainNewsAdapter mAdapter;
    private LRecyclerAdapter mLRecyclerAdapter;
    private List<NewsBean.T1348648517839Bean> mNewsBeens = new ArrayList<>();

    public static CurrentFragment newInstance(String title) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_current;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initListener();// 必须先调用监听，才能自动刷新
        initRecyclerView();
    }

    private void initListener() {
        // 滚动监听
        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {
            }

            @Override
            public void onScrollDown() {
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {
                if (!isToBot && distanceY < CommonUtils.dip2px(activity, 300)) {
                    CommonUtils.startWebBar2Bottom(mFabButton);
                    isToBot = true;
                    isToTop = false;
                }
                if (!isToTop && distanceY >= CommonUtils.dip2px(activity, 300)) {
                    CommonUtils.startWebBar2Up(mFabButton);
                    isToTop = true;
                    isToBot = false;
                }
            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });

        // 刷新监听
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                mCurrentPage = 0;
                initData();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentPage < mTotlePage) {
                    isLoading = true;
                    initData();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });
    }

    private void initRecyclerView() {
        // 1、创建管理器和适配器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);// 交错排列的Grid布局
        mAdapter = new MainNewsAdapter(mNewsBeens);
        // 2、设置管理器和适配器
        mRecyclerView.setLayoutManager(manager);
        mLRecyclerAdapter = new LRecyclerAdapter(mAdapter);
        mRecyclerView.setAdapter(mLRecyclerAdapter);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);

        // 3、设置分割线
//        SpaceDecoration decoration = new SpaceDecoration(CommonUtils.dip2px(activity, 5));
//        decoration.setPaddingStart(true);
//        decoration.setPaddingEdgeSide(true);
//        decoration.setPaddingHeaderFooter(true);
//        decoration.isGroupRecyclerView(false);
//        mRecyclerView.addItemDecoration(decoration);

//        DividerDecoration decoration1 = new DividerDecoration(ContextCompat.getColor(this, R.color.deep_line), 2);
//        decoration1.setDrawLastItem(false);
//        decoration1.setDrawHeaderFooter(false);
//        mRecyclerView.addItemDecoration(decoration1);

//        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayout.HORIZONTAL,
//                dip2px(this, 1), ContextCompat.getColor(this, R.color.color_bg)));

        // 下拉刷新、自动加载
        mRecyclerView.setRefreshEnabled(true);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.refreshWithPull();

        //添加头部
//        View headerView = mInflater.inflate(R.layout.inflater_current_header, null);
//        mLRecyclerAdapter.addHeaderView(headerView);

        // 4、设置监听事件
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (CommonUtils.isFastDoubleClick()) {
                    MessageUtils.showInfo(activity, "点击太快，休息一会");
                } else {
                    MessageUtils.showInfo(activity, mNewsBeens.get(position).getTitle());
                    Bundle bundle = new Bundle();
                    bundle.putString("WEB_URL", mNewsBeens.get(position).getUrl_3w());
//                    bundle.putString("WEB_URL", "http://113.195.135.37:58080/NewShangRao/publish/126/2017-06-05/308.html");
                    bundle.putString("IMG_URL", mNewsBeens.get(position).getImgsrc());
                    bundle.putString("NEW_TIT", mNewsBeens.get(position).getTitle());
                    ActivityUtils.launchActivity(activity, DetailActivity.class, bundle);
                }
            }
        });

        mAdapter.setOnLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View itemView, int position) {
                mAdapter.delete(position);
            }
        });
    }

    @Override
    protected void initData() {
        LogUtils.log("加载第" + mCurrentPage + "页");
        showLoading("正在加载...");
        String url = "http://c.3g.163.com/nc/article/list/T1348648517839/" +
                mCurrentPage * 20 + "-20.html";
        BRApplication.mOkHttpUtils.get().url(url).tag(this).enqueue(new GsonHandler<NewsBean>() {
            @Override
            public void onSuccess(int statusCode, NewsBean response) {
//                LogUtils.log(response);
//                NewsBean newsBean = new Gson().fromJson(response, NewsBean.class);
                dismissLoading();
                List<NewsBean.T1348648517839Bean> bean = response.getT1348648517839();
                if (bean != null && bean.size() != 0) {

                    //绑定新闻列表数据
                    mCurrentPage++;
                    mRecyclerView.refreshComplete(20);

                    if (isRefreshing) {
                        isRefreshing = false;
                        mNewsBeens.clear();
                        mNewsBeens.addAll(bean);
                        mAdapter.notifyDataSetChanged();
                    }

                    if (isLoading) {
                        isLoading = false;
                        mAdapter.addAll(bean);
                    }
                } else {
                    mRecyclerView.setEmptyView(mInflater.inflate(R.layout.layout_empty, mEmptyView));
                    mRecyclerView.refreshComplete(20);
                    MessageUtils.showInfo(activity, "没有最新内容");
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LogUtils.log(error_msg);
                dismissLoading();
                mRecyclerView.setEmptyView(mInflater.inflate(R.layout.layout_error, mEmptyView));
                if (isRefreshing) {
                    mRecyclerView.refreshComplete(20);
                }
                if (isLoading) {
                    mRecyclerView.refreshComplete(20);
                    mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            isLoading = true;
                            initData();
                        }
                    });
                }
            }
        });
    }

    @OnClick({R.id.tv_more, R.id.tv_title, R.id.fl_sub_title, R.id.fab_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                ActivityUtils.launchActivity(activity, DetailActivity.class);
                break;
            case R.id.tv_title:
                BRPopWindow popWindow = new BRPopWindow(activity, mFirstItems, mFirstPos);
                popWindow.setPopWidth(ScreenUtils.getScreenWidth(activity));
                popWindow.showPopupWindow(mTvTitle, 0, -CommonUtils.dip2px(activity, 5));
                popWindow.setOnItemListener(new BRPopWindow.OnItemListener() {
                    @Override
                    public void OnItemListener(int position, String item) {
                        mFirstPos = position;
                        mTvTitle.setText(item);
                    }
                });
                break;
            case R.id.fl_sub_title:
                BRPopWindow sub_popWindow = new BRPopWindow(activity, mSecondItems, mSecondPos);
                sub_popWindow.setPopWidth(CommonUtils.dip2px(activity, 300));
                sub_popWindow.showPopupWindow(mFlSubTitle, 0, 0);
                sub_popWindow.setOnItemListener(new BRPopWindow.OnItemListener() {
                    @Override
                    public void OnItemListener(int position, String item) {
                        mSecondPos = position;
                        mTvSubTitle.setText(item);
                    }
                });
                break;
            case R.id.fab_button:
                mRecyclerView.scrollToPosition(0);
//                Snackbar.make(view, "FloatingActionButton", Snackbar.LENGTH_LONG)
//                        .setAction("点我置顶", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                            }
//                        }).show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.log("CurrentFragment-onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.log("CurrentFragment-onPause");
    }
}