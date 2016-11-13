package com.example.cuizehui.smartschool.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.Utils.Connect;
import com.example.cuizehui.smartschool.Utils.NewsCenterData;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 左侧菜单
 */
public class Slidingleft_Fragment extends BaseFragment {
    private List<NewsCenterData.DataBean> dataBeanList=new ArrayList<NewsCenterData.DataBean>();
    private ListView lv_leftData;
    public MyLVadapter mylvadapter = new MyLVadapter();
    private int selectPosition;
    private onSwitchpaperListener onSwitchpaperListener;

    //使用接口的方式左侧菜单和 主界面内容交互
    public interface onSwitchpaperListener{
       void  switchpaper(int i);
    }


     //对外提供一个设置监听的方法。
    public void setOnSwichtpaerListener(onSwitchpaperListener listener){
         this.onSwitchpaperListener=listener;
    }
    //传递网络数据/

    /**
     *
     * @param dataBeanLists
     */
    public void setDataBeanLists(List<NewsCenterData.DataBean> dataBeanLists) {
         this.dataBeanList  =  dataBeanLists;
         mylvadapter.notifyDataSetChanged();
    }

    @Override
    public void intiEvent() {
        super.intiEvent();
        lv_leftData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 dataBeanList.get(position);
                //更换界面
                selectPosition=position;
                mylvadapter.notifyDataSetChanged();
                //调用接口内方法
                if(onSwitchpaperListener!=null){

                     onSwitchpaperListener.switchpaper(position);
                 }else {
                }
                //通知所在的fragment更换界面
                //mainActivity.getContextFragment().swichPager(position);
                //开关！
                mainActivity.getSlidingMenu().toggle();
            }
        });
    }

    @Override
    public View initview() {
        lv_leftData=new ListView(mainActivity);
        //背景是黑色
        lv_leftData.setBackgroundColor(Color.BLACK);

        //选中拖动的背景色 设置成透明
        lv_leftData.setCacheColorHint(Color.TRANSPARENT);

        //设置选中时为透明背景
        lv_leftData.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //没有分割线
        lv_leftData.setDividerHeight(0);

        //距顶部为45px
        lv_leftData.setPadding(0, 45, 0, 0);
        return lv_leftData;
    }



    @Override
    public void initData() {

        lv_leftData.setAdapter(mylvadapter);
        //通知重绘还是不行！还需要重新调用这个方法
        super.initData();
    }

   class MyLVadapter extends BaseAdapter{
         @Override
         public int getCount() {
           return dataBeanList.size();
         }

         @Override
         public Object getItem(int position) {
            return 0;
         }

       @Override
       public long getItemId(int position) {
           return 0;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
          TextView tv;
           if(convertView==null){
                    tv= (TextView) View.inflate(mainActivity,R.layout.left_lv_tv,null);
           }
           else {
               //等于缓存
               tv= (TextView) convertView;
          }
               tv.setText(dataBeanList.get(position).getTitle());
           //通过这种方式设置只有一个被选中的装态
               tv.setEnabled(position == selectPosition);
           return  tv;
       }
   }

}
