package com.example.cuizehui.smartschool.baseCenter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.Utils.NewsCenterData;
import com.example.cuizehui.smartschool.newcenter.Tip_newsCenter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuizehui on 2016/8/18at ${time}.
 */
public class NewsBasecenter extends BaseCenterPaper {
    private ViewPager viewpager;
    private List<NewsCenterData.DataBean.ChildrenBean> childrendatalists=new ArrayList<>();
    private VpAdapter vpAdapter;
    private TabPageIndicator indicator;

    public NewsBasecenter(Context context, List<NewsCenterData.DataBean.ChildrenBean> datalists) {
        super(context);
        this.childrendatalists=datalists;
        Log.d("测试执行123","执行");
    }

    @Override
     public View initView() {
        LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);
        View newCenterview=layoutInflater.inflate(R.layout.news_center,null);
        indicator = (TabPageIndicator) newCenterview.findViewById(R.id.indicator);

        viewpager = (ViewPager) newCenterview.findViewById(R.id.pager);
        return newCenterview;


    }

    @Override
    public void initEvent() {
        super.initEvent();
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                        if (position==0){
                            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        }
                        else {
                            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        vpAdapter=new VpAdapter();
        viewpager.setAdapter(vpAdapter);
        indicator.setViewPager(viewpager);
    }
    class  VpAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return childrendatalists.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //相同的布局不同的数据
            Tip_newsCenter tip_newsCenter=new Tip_newsCenter(mainActivity,childrendatalists.get(position));


            container.addView(tip_newsCenter.getRoot());
            return tip_newsCenter.getRoot();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return childrendatalists.get(position).getTitle();
        }
    }

}
