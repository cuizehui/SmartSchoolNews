package com.example.cuizehui.smartschool.viewPagerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.activitys.MainActivity;

/**
 * Created by cuizehui on 2016/8/9at ${time}.
 */
public class SetcenterPager_view extends  BasePager_view {
    public SetcenterPager_view(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("设置");
        View root =getRoot();
        LayoutInflater inflater =LayoutInflater.from(mainActivity);
        View view=inflater.inflate(R.layout.set_centerlayout,null);
        fl.addView(view);
    }
}
