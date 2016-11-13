package com.example.cuizehui.smartschool;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by cuizehui on 2016/7/1at ${time}.
 */
public class TestAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        SDKInitializer.initialize(getApplicationContext());

      SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=57e3a163");
    }
}
