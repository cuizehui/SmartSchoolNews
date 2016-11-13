package com.example.cuizehui.smartschool.fragments;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.viewPagerViews.BasePager_view;
import com.example.cuizehui.smartschool.viewPagerViews.MapPager_view;
import com.example.cuizehui.smartschool.viewPagerViews.HomePager_view;
import com.example.cuizehui.smartschool.viewPagerViews.NewscenterPager_view;
import com.example.cuizehui.smartschool.viewPagerViews.SetcenterPager_view;
import com.example.cuizehui.smartschool.viewPagerViews.SmartServicePager_view;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/*
  内容
 */
public class SlidingContext_Fragment extends BaseFragment  {
    ViewPager viewPager;
    List<BasePager_view> pagerlist;

    RadioGroup radioGroup;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;
    private int currentVP;
    @Override
    public View initview() {
        LayoutInflater inflater =LayoutInflater.from(super.mainActivity);
        View contextlayout=  inflater.inflate(R.layout.fragment_sliding_context_,null);
        viewPager= (ViewPager) contextlayout.findViewById(R.id.context_viewpager);
        radioGroup = (RadioGroup) contextlayout.findViewById(R.id.rg_content_radios);
        radioButton2= (RadioButton) contextlayout.findViewById(R.id.rb_main_content_newscenter);
        radioButton3= (RadioButton) contextlayout.findViewById(R.id.rb_main_content_smartservice);
        radioButton4= (RadioButton) contextlayout.findViewById(R.id.rb_main_content_govaffairs);
        radioButton5= (RadioButton) contextlayout.findViewById(R.id.rb_main_content_settingcenter);
        return contextlayout;
    }

    @Override
    public void initData() {
        super.initData();
        pagerlist=new ArrayList<BasePager_view>();
        pagerlist.add(new NewscenterPager_view(mainActivity));
        pagerlist.add(new SmartServicePager_view(mainActivity));
        pagerlist.add(new MapPager_view(mainActivity));
        pagerlist.add(new SetcenterPager_view(mainActivity));
        viewPager.setAdapter(new VpAdapter());
    }

    @Override
    public void intiEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_main_content_newscenter:
                        switchViewpager(0);
                        break;
                    case R.id.rb_main_content_smartservice:
                        switchViewpager(1);
                        break;
                    case R.id.rb_main_content_govaffairs:
                        switchViewpager(2);
                        break;

                    case R.id.rb_main_content_settingcenter:
                        switchViewpager(3);
                        break;
                }
            }
        });

        //给viewpager设置滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                     switch (position){
                       case 0:
                             radioButton2.setChecked(true);
                             radioButton3.setChecked(false);
                             radioButton4.setChecked(false);
                             radioButton5.setChecked(false);
                             mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

                             break;
                         case 1:
                             radioButton2.setChecked(false);
                             radioButton3.setChecked(true);
                             radioButton4.setChecked(false);
                             radioButton5.setChecked(false);
                             mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

                             break;
                         case 2:
                             radioButton2.setChecked(false);
                             radioButton3.setChecked(false);
                             radioButton4.setChecked(true);
                             radioButton5.setChecked(false);
                             mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

                             break;
                         case 3:
                             radioButton2.setChecked(false);
                             radioButton3.setChecked(false);
                             radioButton4.setChecked(false);
                             radioButton5.setChecked(true);
                             mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                             break;
                     }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //依次设置每一个选择界面！！！
    public void switchViewpager(int i) {
        //当前viewpager显示的界面
        currentVP=i;
        viewPager.setCurrentItem(i);
    }

   /* //通过List设置下级要显示的paper
    public  void swichPager(int i){
        BasePager_view basePager=pagerlist.get(currentVP);
        basePager.swichpage(i);
    }*/

    class VpAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagerlist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
          View view=pagerlist.get(position).getRoot();
            container.addView(view);
            return  view;
        }
    }

}
