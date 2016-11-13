package com.example.cuizehui.smartschool.baseCenter;

import android.content.Context;
import android.view.View;

import com.example.cuizehui.smartschool.activitys.MainActivity;

/**
 * Created by cuizehui on 2016/8/18at ${time}.
 */
public abstract class BaseCenterPaper {
    private View root ;
    MainActivity mainActivity;
    public BaseCenterPaper(Context context) {
        this.mainActivity= (MainActivity)context;
        this.root=initView();
        initEvent();
    }
    public abstract View initView();
    public void initData(){

    }
    public void initEvent(){

    }

    public View getRoot() {
        return root;
    }
}
