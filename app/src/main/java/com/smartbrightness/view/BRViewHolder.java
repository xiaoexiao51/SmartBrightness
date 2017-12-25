package com.smartbrightness.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * 通用的ViewHoder，用集合<View>来代替多个成员变量
 * 然后用同一个方法传入不同的id，来拿到需要的小控件
 */
public class BRViewHolder {

    public final View convertView;
    private Map<Integer, View> views = new HashMap<>();

    private BRViewHolder(View convertView) {
        this.convertView = convertView;
    }

    public static BRViewHolder create(View convertView, ViewGroup parent, int layoutRes) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(layoutRes, parent, false);
            BRViewHolder viewHolder = new BRViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        BRViewHolder viewHolder = (BRViewHolder) convertView.getTag();
        return viewHolder;
    }

    public View getView(int id) {
        if (views.get(id) == null) {
            views.put(id, convertView.findViewById(id));
        }
        return views.get(id);
    }

    public TextView getTv(int id) {
        return ((TextView) getView(id));
    }

    public ImageView getIv(int id) {
        return ((ImageView) getView(id));
    }

    public CheckBox getCb(int id) {
        return ((CheckBox) getView(id));
    }

    public ProgressBar getPb(int id) {
        return ((ProgressBar) getView(id));
    }

    public LinearLayout getLl(int id) {
        return ((LinearLayout) getView(id));
    }
}
