package com.example.cuizehui.smartschool.viewPagerViews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuizehui.smartschool.R;

import java.util.ArrayList;

/**
 * Created by cuizehui on 2016/8/6at ${time}.
 */
public class HomePager_view extends BasePager_view {

    private GridView gridview;
    private HomeGvAdapter homeGvAdapter;
    private ArrayList <bean> homelist;
    public  int[]  drawables;
    public  String[] dnames;
    public HomePager_view(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();

        tv_title.setText("主页");
        LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);
        View view = layoutInflater.inflate(R.layout.home_center, null);
        gridview= (GridView) view.findViewById(R.id.home_gv);
        fl.removeAllViews();
        fl.addView(view);

    }

    @Override
    public void initData() {
        homelist=new ArrayList();
        homeGvAdapter=new HomeGvAdapter();
        gridview.setAdapter(homeGvAdapter);
        //添加图片ID 和文字ID
        drawables=new int[]{
                R.drawable.myclass,
                R.drawable.myschool,
                R.drawable.notify};
        dnames=new String[]{
          "我的课程","我的学校","通知"
        };

        for(int i=0;i<drawables.length;i++){
             bean bean=new bean();
             bean.setDrawbles(drawables[i]);
             bean.setNames(dnames[i]);
            homelist.add(bean);

        }
        homeGvAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
  class   HomeGvAdapter extends BaseAdapter{
      @Override
      public int getCount() {
          return homelist.size();
      }

      @Override
      public Object getItem(int position) {
          return null;
      }

      @Override
      public long getItemId(int position) {
          return 0;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=LayoutInflater.from(mainActivity);
            View view = inflater.inflate(R.layout.home_gv_item, null);
            ImageView imageview = (ImageView) view.findViewById(R.id.item_iv);
          TextView textView= (TextView) view.findViewById(R.id.item_tv);
             imageview.setBackgroundResource(homelist.get(position).getDrawbles());
             textView.setText(homelist.get(position).getNames());
          //  Log.d("显示界面","显示界面调用");
          return view;
      }
  }
    class bean{
        public int getDrawbles() {
            return drawbles;
        }

        public void setDrawbles(int drawbles) {
            this.drawbles = drawbles;
        }

        int drawbles;

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        String names;
    }
}
