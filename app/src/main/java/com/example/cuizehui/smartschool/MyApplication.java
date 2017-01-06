package com.example.cuizehui.smartschool;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.example.cuizehui.smartschool.helper.ImageLoaderHelper;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.xutils.x;

/**
 * Created by cuizehui on 2016/7/1at ${time}.
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

       /* //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
*/
        ImageLoaderHelper.init(this);

        SDKInitializer.initialize(getApplicationContext());

      SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=57e3a163");
        context=getApplicationContext();
    }
    public static Context getContext(){
        return  context;
    }
}
