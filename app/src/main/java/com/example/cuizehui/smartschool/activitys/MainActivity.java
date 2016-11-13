package com.example.cuizehui.smartschool.activitys;



import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.fragments.SlidingContext_Fragment;
import com.example.cuizehui.smartschool.fragments.Slidingleft_Fragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity {

 //注意  tag常量类型否则会发生找不到 空调用的情况！
    public static final String CONTEXT_FragmentTag="CONTEXT_FragmentTag" ;
    public static final String Left_FragmentTag ="Left_FragmentTag" ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        //设置主内容布局
        setContentView(R.layout.activity_main);
        //设置左滑菜单布局
        setBehindContentView(R.layout.fragment_left);
        SlidingMenu menu=getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setBehindOffset(200);

        SlidingContext_Fragment context_fragment=new SlidingContext_Fragment();
        Slidingleft_Fragment  left_fragment=new Slidingleft_Fragment();
        //获取事务
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();
        ft.replace(R.id.main_content,context_fragment,CONTEXT_FragmentTag);
        ft.replace(R.id.main_left,left_fragment,Left_FragmentTag);
        ft.commit();
    }
    //获取侧边fragment方法。 用Tag的方式
    public Slidingleft_Fragment getLeftFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        Slidingleft_Fragment left_Fragment = (Slidingleft_Fragment) fragmentManager.findFragmentByTag(Left_FragmentTag);
        return left_Fragment;
    }

    public SlidingContext_Fragment getContextFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        SlidingContext_Fragment context_Fragment = (SlidingContext_Fragment) fragmentManager.findFragmentByTag(CONTEXT_FragmentTag);
        return context_Fragment;
    }

}
