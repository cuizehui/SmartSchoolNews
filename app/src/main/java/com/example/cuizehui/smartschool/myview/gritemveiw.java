package com.example.cuizehui.smartschool.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by cuizehui on 2016/4/24.
 * Gridview 里面的自定义Imageview 只把控件改成正方形了
 */
public class gritemveiw extends ImageView {
    public gritemveiw(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec));
    }


}
