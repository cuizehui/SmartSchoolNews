package com.example.cuizehui.smartschool.baseCenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.example.cuizehui.smartschool.R;

/**
 * Created by cuizehui on 2016/8/18at ${time}.
 */
public class InteceptBascenter extends  BaseCenterPaper {


    public InteceptBascenter(Context context) {
        super(context);
    }
    @Override
    public   View initView() {
        TextView textView=new TextView(mainActivity);
        textView.setText("这里是交互");
        return textView;
    }
}
