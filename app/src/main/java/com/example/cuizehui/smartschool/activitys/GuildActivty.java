package com.example.cuizehui.smartschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.Utils.DensityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_spash_activty)
public class GuildActivty extends AppCompatActivity {


    @ViewInject(R.id.vg_guild)
    private ViewPager viewPager;

    @ViewInject(R.id.ll_guild_points)
    private LinearLayout llPoints;

    @ViewInject(R.id.bt_guild_finishbt)
    private Button finishButton;

    private List<View> guildList;

    @ViewInject(R.id.v_red_point)
    private View redpoint;

    private int distens;//两点之间的距离
    private Handler handler;

    public  LunboTask lunboTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        lunboTask=new LunboTask();
        initView();//初始化界面

        initData();//初始化数据

        initEvent();//初始化事件

    }

    /**
     * 动态注入只能在类中。。
     * @param view
     */
    @Event(value = R.id.bt_guild_finishbt,type = View.OnClickListener.class)
    private void setFinishButtonOnclick(View view){
        Intent intent=new Intent(GuildActivty.this,MainActivity.class);
        startActivity(intent);
    }


    private void initEvent()
    {



        //lunboProgress();
        lunboTask.startlunbo();


        redpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //取消注册只监听一次
                redpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取距离
                distens= llPoints.getChildAt(1).getLeft()-llPoints.getChildAt(0).getLeft();
            }
        });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 页面滑动过程中执行
             * @param position 当前位置
             * @param positionOffset 偏移比例
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //计算距离滑动红点
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) redpoint.getLayoutParams();
                //设置红点在布局中的有边距
                rl.leftMargin= (int) (distens*(positionOffset+position));

                redpoint.setLayoutParams(rl);
            }

            /**
             * 当前页面执行方法
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                   if(position==guildList.size()-1){
                       finishButton.setVisibility(View.VISIBLE);
                   }else {
                       finishButton.setVisibility(View.GONE);
                   }
                 /*RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) redpoint.getLayoutParams();
                 //设置红点在布局中的有边距
                 rl.leftMargin= distens*position;
                 redpoint.setLayoutParams(rl);*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
          finishButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(GuildActivty.this,MainActivity.class);
                  startActivity(intent);
                  finish();
              }
          });

    }

    /**
     * 轮播事件
     */
    private void lunboProgress() {

        //定时轮播
        handler=new Handler();
        //利用handler发送延时消息这个线程无关，完成的是runnbale接口中的run方法
        //利用这种方式处理事件地方法就不是HandlerMessage中的方法了。（仍然是在主线程吗？）
        //延时后执行一次自己在方法中不断的执行自己

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //更换图片
                //ListView获取调用四个方法的方式通过控件获取Adapter调用方法
                //
                viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%viewPager.getAdapter().getCount());
                handler.postDelayed(this,1000);
                //重新执行一遍自己。
            }
        }, 1000);
    }

    /**
     * 封装了导航界面
     */
    class LunboTask extends Handler implements Runnable
    {

        public  void startlunbo(){
            this.postDelayed(this,1000);
        }
        @Override
        public  void run() {
            viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%viewPager.getAdapter().getCount());
            this.postDelayed(this,1000);
        }
        public void stoplunbo(){
            this.removeCallbacksAndMessages(null);
        }
    }

    private void initData()
    {
        int[] pic=new int[]{
                R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
        guildList=new ArrayList<>();

        for (int i = 0 ; i < pic.length; i++) {
            ImageView view=new ImageView(this);
            view.setImageResource(pic[i]);
            guildList.add(view);

            //给点的布局添加点
            View viewPoint =new View(this);
            viewPoint.setBackgroundResource(R.drawable.gray_point);
            //给点设置放置的位置
            LinearLayout.LayoutParams ll=  new LinearLayout.LayoutParams(40,40);
            //点之间设置间距
            if(i==0){

             }
            else{
                 ll.leftMargin= 10;
             }
            viewPoint.setLayoutParams(ll);

            llPoints.addView(viewPoint);
        }

      viewPager.setAdapter(new pageAdapter());

    }

    class pageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return guildList.size();
        }

        /**
         *
         * @param view
         * @param object
         * @return 返回是否执行替换方法
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
          return   view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        //加载
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=  guildList.get(position);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                   switch (event.getAction()){
                       case MotionEvent.ACTION_DOWN:
                            lunboTask.stoplunbo();
                       break;

                       case MotionEvent.ACTION_MOVE:

                           break;

                       case  MotionEvent.ACTION_UP :
                              lunboTask.startlunbo();
                           break;
                       case MotionEvent.ACTION_CANCEL:
                           lunboTask.startlunbo();
                           break;
                   }

                    return true;
                }
            });
            container.addView(view);
            return view;
        }
    }

    private void initView()
    {

        x.view().inject(this);


    }


}
