package com.example.cuizehui.smartschool.viewPagerViews;


import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.example.cuizehui.smartschool.Utils.Connect;
import com.example.cuizehui.smartschool.Utils.NewsCenterData;
import com.example.cuizehui.smartschool.Utils.SPtools;
import com.example.cuizehui.smartschool.activitys.MainActivity;
import com.example.cuizehui.smartschool.baseCenter.BaseCenterPaper;
import com.example.cuizehui.smartschool.baseCenter.InteceptBascenter;
import com.example.cuizehui.smartschool.baseCenter.NewsBasecenter;
import com.example.cuizehui.smartschool.baseCenter.PicsBascenter;
import com.example.cuizehui.smartschool.baseCenter.TopicBascenter;
import com.example.cuizehui.smartschool.fragments.Slidingleft_Fragment;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuizehui on 2016/8/8at ${time}.
 */
public class NewscenterPager_view extends BasePager_view{
    private List<NewsCenterData.DataBean> lists;
    private Slidingleft_Fragment leftFragment;
    public List<BaseCenterPaper> baseCenterPaperList;
    public NewscenterPager_view(Context context) {
        super(context);
    }
    @Override
    public void initView() {
        super.initView();
        tv_title.setText("新闻中心");
    }



    @Override
    public void initData() {
        super.initData();
        baseCenterPaperList=new ArrayList<>();
        String gsondata=SPtools.getsetsp(mainActivity,"gsondata");
        if(gsondata!=null){
            localParesData(gsondata);
        }
        else {
            final RequestParams params = new RequestParams(Connect.adress);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    parseData(result);
                    SPtools.savesetsp(mainActivity,"gsondata",result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }

    }
    public void localParesData(String gsondata){

               parseData(gsondata);
               Log.d("执行缓存方法了","");

    }
    public void parseData(String result) {
        //缓存数据

        Gson gson=new Gson();
        NewsCenterData   newsCenterData = gson.fromJson(result, NewsCenterData.class);
        leftFragment = mainActivity.getLeftFragment();
        leftFragment.setDataBeanLists(newsCenterData.getData());
        lists = newsCenterData.getData();


        for (NewsCenterData.DataBean list : lists) {
            int i=1;
            Log.d("执行次数",i+"");
            BaseCenterPaper newsPage=null;
            switch (list.getType()){
                case 1:// 新闻页面
                   newsPage = new NewsBasecenter(mainActivity,newsCenterData.getData().get(0).getChildren());
                    break;
                case 10:// 专题页面
                    newsPage = new TopicBascenter(mainActivity);
                    break;
                case 2:// 组图页面
                    newsPage = new PicsBascenter(mainActivity);
                    break;
                case 3:// 互动页面
                    newsPage = new InteceptBascenter(mainActivity);
                    break;
                default:
                    break;
            }
            baseCenterPaperList.add(newsPage);
        }
       //传入这个监听 让调用者调用这个监听器
        leftFragment.setOnSwichtpaerListener(new Slidingleft_Fragment.onSwitchpaperListener() {
            @Override
            public void switchpaper(int i) {
                NewscenterPager_view.this.switchpaper(i);

            }
        });
              switchpaper(0);
        }

    public  void switchpaper(int i){
        BaseCenterPaper currentPaper= baseCenterPaperList.get(i);
        currentPaper.initData();
        tv_title.setText(lists.get(i).getTitle());
        //先移除
        fl.removeAllViews();
        currentPaper.initData();
        fl.addView(currentPaper.getRoot());

    }



    //重写父类 提供要在这个界面显示的信息 和相应的page
    //2 使用接口的方式实现
    /*public void switchpaper(int i) {
        Log.d("执行","替换执行");
       BaseCenterPaper currentPaper= baseCenterPaperList.get(i);
        tv_title.setText(lists.get(i).getTitle());
        //先移除
        fl.removeAllViews();
        currentPaper.initData();
        fl.addView(currentPaper.getRoot());
    }*/
}
