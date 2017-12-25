package com.smartbrightness.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.smartbrightness.R;
import com.smartbrightness.utils.ScreenUtils;

import java.util.List;

/**
 * Created by MMM on 2017/9/1.
 * Context传进来的上下文最好是Activity，才会有阴影效果。
 */
public class BRPopWindow extends PopupWindow {

    private Context context;
    private List<String> items;
    private int selectPostion;

    private View conentView;
    private ListView listView;
    int[] location = new int[2];
    private PopWindowAdapter popAdapter;

//    private static BRPopWindow sXPopWindow;
//
//    public static BRPopWindow getInstance(Context context, List<String> items) {
//        if (sXPopWindow == null) {
//            synchronized (BRPopWindow.class) {
//                if (sXPopWindow == null) {
//                    sXPopWindow = new BRPopWindow(context.getApplicationContext(), items);
//                }
//            }
//        }
//        return sXPopWindow;
//    }

    public BRPopWindow(Context context, List<String> items, int postion) {
        this.context = context;
        this.items = items;
        this.selectPostion = postion;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conentView = inflater.inflate(R.layout.layout_pop_menu, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopWindowBotStyle);
        this.listView = (ListView) conentView.findViewById(R.id.list_view);

        //设置适配器
        popAdapter = new PopWindowAdapter();
        setListViewHeight(listView, items);
        this.listView.setAdapter(popAdapter);
        popAdapter.changeSelected(selectPostion);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowing()) {
                    dismiss();
                }
                onItemListener.OnItemListener(position, items.get(position));
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    public void setPopWidth(int popWidth) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.width = popWidth;
        listView.setLayoutParams(params);
    }

    public void showPopupWindow(View parent, int xoff, int yoff) {
        //获取控件位置坐标
        parent.getLocationOnScreen(location);
        //获取自身的长宽高
        conentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //使用背景模糊效果
//        backgroundAlpha(0.5f);
        if (location[1] > ScreenUtils.getScreenHeight(context) / 2) {
            this.setAnimationStyle(R.style.PopWindowTopStyle);
            int measuredHeight = conentView.getMeasuredHeight() * items.size();
            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - xoff,
                    location[1] - measuredHeight - yoff);
        } else {
            //动画方式一：
            this.setAnimationStyle(R.style.PopWindowBotStyle);
            this.showAsDropDown(parent, xoff, yoff);
            //动画方式二：
//            this.showAsDropDown(parent, xoff, yoff);
//            int measuredHeight = conentView.getMeasuredHeight() * items.size()
//                    + CommonUtils.dip2px(context, 20);
//            AnimHelper.doMoveVertical(conentView, -measuredHeight, 0, 500);
        }
    }

    private void backgroundAlpha(float bgAlpha) {
        try {
            ((Activity) context).getWindow().getDecorView().setAlpha(bgAlpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnItemListener onItemListener;

    public interface OnItemListener {
        void OnItemListener(int position, String item);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    // 列表适配器
    public class PopWindowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items == null ? 0 : items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BRViewHolder holder = BRViewHolder.create(convertView, parent, R.layout.layout_pop_item);
            holder.getTv(R.id.txt_title).setText(items.get(position));
//            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_pop_item, null);
//            textView.setText(items.get(position));
            if (mSelectPosition == position) {
                holder.getTv(R.id.txt_title).setBackgroundResource(R.color.color_bg);
                holder.getTv(R.id.txt_title).setTextColor(ContextCompat.getColor(context, R.color.color_blue));
            } else {
                holder.getTv(R.id.txt_title).setBackgroundResource(R.drawable.bg_common_press);
                holder.getTv(R.id.txt_title).setTextColor(ContextCompat.getColor(context, R.color.color_content));
            }
            return holder.convertView;
        }

        private int mSelectPosition = -1;

        public void changeSelected(int position) {
            if (mSelectPosition != position) {
                mSelectPosition = position;
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 设置ListView的最大高度
     */
    private void setListViewHeight(ListView listView, List<String> contacts) {
        if (contacts.size() >= 8) {
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = ScreenUtils.getScreenHeight(context) * 6 / 10;
            listView.setLayoutParams(layoutParams);
        }
    }
}