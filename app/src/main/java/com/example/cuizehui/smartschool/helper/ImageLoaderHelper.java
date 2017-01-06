package com.example.cuizehui.smartschool.helper;

import android.content.Context;
import android.widget.ImageView;

import com.example.cuizehui.smartschool.MyApplication;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by cuizehui on 2017/1/4at ${time}.
 */
public class ImageLoaderHelper {

        /**
         * 标准配置
         */
        private DisplayImageOptions normalOptions;
        /**
         * 圆形配置
         */
        private DisplayImageOptions circleOptions;
        /**
         * 圆角图片配置
         */
        private DisplayImageOptions roundedOptions;


        private BitmapDisplayer simpleBitmapDisplayer;
        private BitmapDisplayer circleBitmapDisplayer;
        private BitmapDisplayer roundedBitmapDisplayer;


    public ImageLoaderHelper() {

        //初始化 全局默认图片

        simpleBitmapDisplayer = new SimpleBitmapDisplayer();
        normalOptions = getOption(simpleBitmapDisplayer);

        circleBitmapDisplayer = new CircleBitmapDisplayer();
        circleOptions = getOption(circleBitmapDisplayer);

        //圆角图片 圆角半径dp
        int cornerRadiusDp = 10;
        //圆角大小通过 dp2px转换 使得 不同分辨率设备上呈现一致显示效果
        roundedBitmapDisplayer = new RoundedBitmapDisplayer(dip2px(MyApplication.getContext(),cornerRadiusDp));
        roundedOptions = getOption( roundedBitmapDisplayer);

    }
    private int dip2px(Context context, float dpValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);

    }


    /***
     * 初始化一个正常的ImageLoader
     * @param context
     */
    public static void init (Context context){
        ImageLoaderConfiguration.Builder config =new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        //md5加密
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(80 * 1024 * 1024); // 80 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }


    /**
     * 重构 抽取出的通用生成Option方法

     * @param bitmapDisplayer normal 或圆形、圆角 bitmapDisplayer
     *
     * @return
     */
    private DisplayImageOptions getOption( BitmapDisplayer bitmapDisplayer) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(bitmapDisplayer)
                .build();
    }


    /**
     * 正常加载一个图片
     */
    public void loadImageView(ImageView iv, String url) {
        ImageLoader.getInstance().displayImage(url, iv, normalOptions);
    }

    /***
     * 圆角加载
     * @param iv
     * @param url
     */
    public void loadCircleImageView(ImageView iv, String url) {
        ImageLoader.getInstance().displayImage(url, iv, circleOptions);
    }

    public void loadRoundedImageView(ImageView iv, String url) {
        ImageLoader.getInstance().displayImage(url, iv, roundedOptions);
    }




}
