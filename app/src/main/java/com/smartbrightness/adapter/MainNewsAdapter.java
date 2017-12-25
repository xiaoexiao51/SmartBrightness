package com.smartbrightness.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smartbrightness.R;
import com.smartbrightness.base.BaseRecyclerAdapter;
import com.smartbrightness.base.BaseViewHolder;
import com.smartbrightness.bean.NewsBean;
import com.smartbrightness.utils.CommonUtils;
import com.smartbrightness.utils.GlideUtils;
import com.smartbrightness.view.BRToastView;

import java.util.List;


/**
 * Created by MMM on 2017/9/1.
 */
public class MainNewsAdapter extends BaseRecyclerAdapter<NewsBean.T1348648517839Bean> {

    public MainNewsAdapter(List<NewsBean.T1348648517839Bean> items) {
        super(items);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.adapter_news_item;
    }

    @Override
    protected void onBindViewHolder(final BaseViewHolder holder, int position, final NewsBean.T1348648517839Bean item) {
        // 绑定图片
        ImageView iv_cover = holder.getImageView(R.id.iv_cover);
        GlideUtils.loadWithDefult(mContext, item.getImgsrc(), iv_cover);
        // 绑定标题
        holder.getTextView(R.id.tv_title).setText(item.getTitle());
        // 是否收藏
        if (item.isLoved) {
            holder.getImageView(R.id.iv_love).setImageResource(R.drawable.ic_love_selected);
        } else {
            holder.getImageView(R.id.iv_love).setImageResource(R.drawable.ic_love_normal);
        }
        // 收藏监听
        holder.setOnClickListener(R.id.iv_love, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isLoved) {
                    item.setLoved(false);
                    holder.getImageView(R.id.iv_love).setImageResource(R.drawable.ic_love_normal);
                    BRToastView.getInstance().showToast(mContext, "取消收藏");
                } else {
                    item.setLoved(true);
                    holder.getImageView(R.id.iv_love).setImageResource(R.drawable.ic_love_selected);
                    BRToastView.getInstance().showToast(mContext, "收藏成功");
                }
            }
        });

        // 重新计算每个条目之间的间距
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin_horizontal = CommonUtils.dip2px(mContext, 5);
        if (position == 0) {
            lp.setMargins(margin_horizontal, margin_horizontal, margin_horizontal / 2, margin_horizontal / 2);
        } else if (position == 1) {
            lp.setMargins(margin_horizontal / 2, margin_horizontal, margin_horizontal, margin_horizontal / 2);
        } else {
            if (position % 2 == 0) {
                lp.setMargins(margin_horizontal, margin_horizontal / 2, margin_horizontal / 2, margin_horizontal / 2);
            } else {
                lp.setMargins(margin_horizontal / 2, margin_horizontal / 2, margin_horizontal, margin_horizontal / 2);
            }
        }
        holder.itemView.setLayoutParams(lp);
    }
}
