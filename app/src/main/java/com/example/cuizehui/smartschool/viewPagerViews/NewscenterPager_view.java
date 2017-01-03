package com.example.cuizehui.smartschool.viewPagerViews;


import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
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
import com.example.cuizehui.smartschool.net.RetrofitFactory;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by cuizehui on 2016/8/8at ${time}.
 */
public class NewscenterPager_view extends BasePager_view{
    private static final String TAG ="Taggg" ;
    private List<NewsCenterData.DataBean> lists;
    private Slidingleft_Fragment leftFragment;
    public List<BaseCenterPaper> baseCenterPaperList;
    public NewscenterPager_view(Context context) {
        super(context);
    }
    public Gson gson;
    @Override
    public void initView() {
        super.initView();
        tv_title.setText("新闻中心");
    }



    @Override
    public void initData() {

        baseCenterPaperList=new ArrayList<>();

        String jsonCache = SPtools.getsetsp(mainActivity,Connect.BASE_SEVICE+"");
        if (!TextUtils.isEmpty(jsonCache)){
            //有本地数据
            //从本地取数据显示
            parseData(jsonCache);

        }

                    Log.d("执行新闻中心数据加载","执行");

          Call<String> newdatacall = RetrofitFactory.getStringService().getnewsCenterData();

                   newdatacall.enqueue(new retrofit2.Callback<String>() {
                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {

                           if (response.isSuccessful() && response.errorBody() == null) {
                               Log.d("成功","1");

                               SPtools.savesetsp(mainActivity,Connect.BASE_SEVICE+"", response.body().toString());

                               parseData(response.body().toString());
                           } else {
                               Log.d(TAG, "error code:" + response.code());
                               Log.d(TAG, "error message:" + response.message());
                           }




                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {
                           Log.d(TAG, "error:" + t.getMessage());
                       }
                   });

        super.initData();
    }

    public void parseData(String result) {
        //缓存数据
        if (gson == null)
            gson = new Gson();

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
