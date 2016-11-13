package com.example.cuizehui.smartschool.baseCenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cuizehui on 2016/8/18at ${time}.
 */
public class TopicBascenter extends  BaseCenterPaper {
    public TopicBascenter(Context context) {
        super(context);
    }
    @Override
    public View initView() {
        TextView textView=new TextView(mainActivity);
        textView.setText("这里是话题");
        return textView;
    }
}
