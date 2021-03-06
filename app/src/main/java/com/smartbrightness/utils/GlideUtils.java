package com.smartbrightness.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.smartbrightness.R;


/**
 * Created by MMM on 2017/9/1.
 * 1、图片占用内存回收及时，能减少因内存不足造成的崩溃，生命周期和Activity/Fragment一致。
 * 2、默认Bitmap格式是RGB_565，减少内存资源占用。
 * 3、glide比universal-image-loader占用的内存要小一些。
 * 4、图片显示效果为渐变，更加平滑。
 * 5、glide可以将任何的本地视频解码成一张静态图片。
 * 6、支持 Jpg、Png、Gif、WebP、缩略图
 */

public class GlideUtils {

    private GlideUtils() {
        throw new RuntimeException("GlideUtils cannot be initialized!");
    }

    //设置加载中以及加载失败图片
    public static void loadWithDefult(Context mContext, String path, ImageView mImageView) {
        boolean download_img = SPUtils.getBoolean(mContext, "download_img", false);
        if (CommonUtils.checkNetwork(mContext) && !CommonUtils.isWifi(mContext) && !download_img) {
            mImageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_press));
            return;
        }
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).
                placeholder(R.color.color_press).error(R.color.color_press).centerCrop().into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadWithBlack(Context mContext, String path, ImageView mImageView) {
        boolean download_img = SPUtils.getBoolean(mContext, "download_img", false);
        if (CommonUtils.checkNetwork(mContext) && !CommonUtils.isWifi(mContext) && !download_img) {
            mImageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_black));
            return;
        }
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).
                placeholder(R.color.color_black).error(R.color.color_black).fitCenter().into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).override(width, height).
                placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).animate(anim).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).centerCrop().into(mImageView);
    }

    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asGif().into(mImageView);
    }

    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asBitmap().into(mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<String, GlideDrawable> requstlistener) {
        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<GlideDrawable> simpleTarget) {
        Glide.with(mContext).load(path).centerCrop().into(simpleTarget);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}
