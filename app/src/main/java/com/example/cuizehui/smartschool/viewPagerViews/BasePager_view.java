package com.example.cuizehui.smartschool.viewPagerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.activitys.MainActivity;
import com.example.cuizehui.smartschool.baseCenter.BaseCenterPaper;

/**
 * Created by cuizehui on 2016/8/6at ${time}.
   ..veiwPager的view项。。。。
 */
public class BasePager_view {
    private View root;
    protected TextView tv_title;
    protected MainActivity mainActivity;
    protected FrameLayout fl;

    public BasePager_view(Context context){
        this.mainActivity= (MainActivity) context;
        initView();
        initData();
        initEvent();
    }

    public void initEvent() {

    }

    public void initData() {
    }


    public void initView() {
        LayoutInflater inflate=LayoutInflater.from(mainActivity);
        root=inflate.inflate(R.layout.basepager_context,null);
        tv_title= (TextView) root.findViewById(R.id.tv_base_content_title);
        fl= (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
    }
    public View getRoot(){
        return root;
    }
   /*//设置下级要选择的paper
    public void swichpage(int i){

    }*/

}
